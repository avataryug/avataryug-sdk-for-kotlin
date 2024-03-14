package Avataryug.Class.AvatarLoader


import Avataryug.Class.AvataryugData
import Avataryug.Class.DefaultAvatarData
import android.util.Log
import android.view.MotionEvent
import android.view.Surface
import android.view.SurfaceView
import android.view.TextureView
import com.google.android.filament.*
import com.google.android.filament.android.DisplayHelper
import com.google.android.filament.android.UiHelper
import com.google.android.filament.gltfio.*
import com.google.android.filament.utils.*
import kotlinx.coroutines.*
import java.nio.Buffer


private const val kNearPlane = 0.05     // 5 cm
private const val kFarPlane = 1000.0    // 1 km
private const val kAperture = 16f
private const val kShutterSpeed = 1f / 125f
private const val kSensitivity = 100f

class CustomModelViewer (val engine: Engine, private val uiHelper: UiHelper) : android.view.View.OnTouchListener
{
    private var headAsset : FilamentAsset? = null

    private var topAsset : FilamentAsset? = null
    private var bottomAsset : FilamentAsset? = null
    private var outfitAsset : FilamentAsset? = null
    private var handwearAsset : FilamentAsset? = null
    private var footwearAsset : FilamentAsset? = null

    private var wristwearAsset : FilamentAsset? = null

    private var normalizeSkinningWeights = true

    private val loadedFacewearList : MutableList<LoadedFacewearAssets>  = mutableListOf()

    private var cameraFocalLength = 28f
        set(value) {
            field = value
            updateCameraProjection()
        }

    val scene: Scene
    val view: View
    val camera: Camera
    val renderer: Renderer

    @Entity
    val light: Int

    @Entity
    var light1: Int


    private lateinit var displayHelper: DisplayHelper
    lateinit var cameraManipulator: Manipulator
    private lateinit var gestureDetector: GestureDetectors
    private var surfaceView: SurfaceView? = null
    private var textureView: TextureView? = null
    private var fetchResourcesJob: Job? = null

    private var swapChain: SwapChain? = null
    private var assetLoader: AssetLoader
    private var materialProvider: MaterialProvider
    private var resourceLoader: ResourceLoader
    private val readyRenderables = IntArray(128) // add up to 128 entities at a time
    private val weights = FloatArray(129) // add up to 128 entities at a time

    private val eyePos = DoubleArray(3)
    private val target = DoubleArray(3)
    private val upward = DoubleArray(3)

    init {
        renderer = engine.createRenderer()
        scene = engine.createScene()
        camera = engine.createCamera(engine.entityManager.create())
            .apply { setExposure(kAperture, kShutterSpeed, kSensitivity) }
        view = engine.createView()
        view.scene = scene
        view.camera = camera

        materialProvider = UbershaderProvider(engine)
        assetLoader = AssetLoader(engine, materialProvider, EntityManager.get())
        resourceLoader = ResourceLoader(engine, normalizeSkinningWeights)

        // Always add a direct light source since it is required for shadowing.
        // We highly recommend adding an indirect light as well.

        light = EntityManager.get().create()

        val (r, g, b) = Colors.cct(6_500.0f)
        LightManager.Builder(LightManager.Type.DIRECTIONAL)
            .color(r, g, b)
            .intensity(100_000.0f)
            .direction(0.0f, -1.0f, -1.0f)
            .castShadows(true)
            .build(engine, light)

        light1 = EntityManager.get().create()

        LightManager.Builder(LightManager.Type.DIRECTIONAL)
            .color(r, g, b)
            .intensity(100_000.0f)
            .direction(0.0f, -1.0f, 1.0f)
            .castShadows(true)
            .build(engine, light1)

        scene.addEntity(light)
        scene.addEntity(light1)
    }

    constructor(surfaceView: SurfaceView, engine: Engine = Engine.create(),uiHelper: UiHelper = UiHelper(UiHelper.ContextErrorPolicy.DONT_CHECK),manipulator: Manipulator? = null ) : this(engine, uiHelper) {
        cameraManipulator = manipulator ?: Manipulator.Builder().targetPosition(kDefaultObjectPosition.x, kDefaultObjectPosition.y, kDefaultObjectPosition.z)
            .viewport(surfaceView.width, surfaceView.height).build(Manipulator.Mode.ORBIT)

        this.surfaceView = surfaceView
        gestureDetector = GestureDetectors(surfaceView, cameraManipulator)
        displayHelper = DisplayHelper(surfaceView.context)
        uiHelper.renderCallback = SurfaceCallback()
        uiHelper.attachTo(surfaceView)
        addDetachListener(surfaceView)
    }
    private fun setMaterial(fAsset : FilamentAsset){
        for (entity in fAsset.entities)
        {
            val assetName = fAsset.getName(entity).toString()
            val renderable = engine.renderableManager.getInstance(entity)
            if (renderable == 0)  {
                continue
            }

            if (assetName == "body" || assetName == "AvatarMesh")
            {
                var bodyIndex = 0
                if (engine.renderableManager.getMaterialInstanceAt(renderable, 0).name == "body")
                {
                    bodyIndex = 0
                }
                if (engine.renderableManager.getMaterialInstanceAt(renderable, 1).name == "body")
                {
                    bodyIndex = 1
                }
                engine.renderableManager.setMaterialInstanceAt(renderable,bodyIndex,CustomizeAvatarLoader.instance.bodyMaterial)
            }
        }
    }
    private fun loadBodyPart(buffer: Buffer?, modelData:EconomyItem, addNew :Boolean)
    {
        val centerPoint : Float3 = kDefaultObjectPosition
        if(modelData.ItemCategory == "Top")
        {
            if(topAsset != null) {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                topAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.topAsset = null
                }
            }
            if(outfitAsset != null) {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                outfitAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.outfitAsset = null
                }
            }
                if(addNew) {
                    topAsset = buffer?.let { assetLoader.createAsset(it) }
                    topAsset?.let { asset ->
                        resourceLoader.loadResources(asset)
                        asset.releaseSourceData()
                    }
                    topAsset?.let { asset ->
                        transformToUnitCube(asset)
                        setMaterial(asset)
                        LoadMissingAfterModelLoad(modelData)
                    }
                }else{
                    LoadMissingAfterRemovingSameModle(modelData)
                }

        }
        if(modelData.ItemCategory == "Bottom")
        {
            if(bottomAsset != null)
            {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                bottomAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.bottomAsset = null
                }
            }
            if(outfitAsset != null) {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                outfitAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.outfitAsset = null
                }
            }
            if(addNew)
            {
                bottomAsset = buffer?.let { assetLoader.createAsset(it) }
                bottomAsset?.let { asset ->
                    resourceLoader.loadResources(asset)
                    asset.releaseSourceData()
                }
                bottomAsset?.let { asset ->
                    transformToUnitCube(asset)
//                    val tm = engine.transformManager
//                    var center = asset.boundingBox.center.let { v -> Float3(v[0], v[1], v[2]) }
//                    val scaleFactor = 1.0f
//                    center -= centerPoint
//                    val transform = scale(Float3(scaleFactor)) * translation(-Float3(x=0.0f, y=0.0f, z=0.0f))
//                    tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())
                    setMaterial(asset)
                    LoadMissingAfterModelLoad(modelData)
                }
            }else{
                LoadMissingAfterRemovingSameModle(modelData)
            }
        }
        if(modelData.ItemCategory == "Outfit")
        {
            if(outfitAsset != null) {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                outfitAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.outfitAsset = null
                }
            }
            if(topAsset != null) {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                topAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.topAsset = null
                }
            }
            if(bottomAsset != null) {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                bottomAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.bottomAsset = null
                }
            }

            if(addNew)
            {
                outfitAsset = buffer?.let { assetLoader.createAsset(it) }
                outfitAsset?.let { asset ->
                    resourceLoader.loadResources(asset)
                    asset.releaseSourceData()
                }
                outfitAsset?.let { asset ->
                    transformToUnitCube(asset)
//                    val tm = engine.transformManager
//                    var center = asset.boundingBox.center.let { v -> Float3(v[0], v[1], v[2]) }
//                    val scaleFactor = 1.0f
//                    center -= centerPoint
//                    val transform = scale(Float3(scaleFactor)) * translation(-Float3(x=0.0f, y=0.0f, z=0.0f))
//                    tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())
                    LoadMissingAfterModelLoad(modelData)
                    setMaterial(asset)
                }
            }else{
                LoadMissingAfterRemovingSameModle(modelData)
            }
        }
        if(modelData.ItemCategory == "Handwear")
        {
            if(handwearAsset != null)
            {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                handwearAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.handwearAsset = null
                }
            }
            if(addNew)
            {
                handwearAsset = buffer?.let { assetLoader.createAsset(it) }
                handwearAsset?.let { asset ->
                    resourceLoader.loadResources(asset)
                    asset.releaseSourceData()
                }
                handwearAsset?.let { asset ->
                    transformToUnitCube(asset)
//                    val tm = engine.transformManager
//                    var center = asset.boundingBox.center.let { v -> Float3(v[0], v[1], v[2]) }
//                    val scaleFactor = 1.0f
//                    center -= centerPoint
//                    val transform = scale(Float3(scaleFactor)) * translation(-Float3(x=0.0f, y=0.0f, z=0.0f))
//                    tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())
                    setMaterial(asset)
                    LoadMissingAfterModelLoad(modelData)
                }
            }else{
                LoadMissingAfterRemovingSameModle(modelData)
            }
        }
        if(modelData.ItemCategory == "Footwear")
        {
            if (footwearAsset != null)
            {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                footwearAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.footwearAsset = null
                }
            }
            if (addNew)
            {
                footwearAsset = buffer?.let { assetLoader.createAsset(it) }
                footwearAsset?.let { asset ->
                    resourceLoader.loadResources(asset)
                    asset.releaseSourceData()
                }
                footwearAsset?.let { asset ->
                    transformToUnitCube(asset)
//                    val tm = engine.transformManager
//                    var center = asset.boundingBox.center.let { v -> Float3(v[0], v[1], v[2]) }
//                    val scaleFactor = 1.0f
//                    center -= centerPoint
//                    val transform = scale(Float3(scaleFactor)) * translation(-Float3(x = 0.0f,y = 0.0f,z = 0.0f)                    )
//                    tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())
                    setMaterial(asset)
                    LoadMissingAfterModelLoad(modelData)
                }
            }else{
                LoadMissingAfterRemovingSameModle(modelData)
            }
        }
    }


    fun LoadHeadModel(buffer: Buffer){

        val centerPoint : Float3 = kDefaultObjectPosition
        if(headAsset == null) {
            headAsset = assetLoader.createAsset(buffer)
            headAsset?.let { asset ->
                resourceLoader.loadResources(asset)
                asset.releaseSourceData()
            }

            headAsset?.let { asset ->


                transformToUnitCube(asset)

                val rm = engine.renderableManager
                for (entity in asset.entities)
                {
                    val renderable = rm.getInstance(entity)

                    if (renderable == 0) {
                        continue
                    }

                    val assetName = asset.getName(entity).toString()
                   // asset.getMorphTargetNames(entity)
                    if(assetName == "eyes")
                    {

                        rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.eyeballMaterial)
                        val sampler = TextureSampler()
                        sampler.anisotropy = 8.0f
                        CustomizeAvatarLoader.instance.eyeballMaterial.setParameter("EyeballTex", CustomizeAvatarLoader.instance.eyeballTexture,sampler)


//                        val size = asset.getMorphTargetNames(entity).size
//                        for (index in asset.getMorphTargetNames(entity).indices) {
//                            weights[index] = 1.0f
//                        }
                        ///rm.setMorphWeights(entity,weights,0);

                    }
                    if(assetName == "head")
                    {
                        rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.faceMaterial)
                        val sampler = TextureSampler()
                        sampler.anisotropy = 8.0f
//                        for (item in asset.getMorphTargetNames(entity))
//                        {
//                            Log.e("head===",item.toString())
//                        }
//                        for (index in asset.getMorphTargetNames(entity).indices) {
//                            weights[index] = 1.0f
//                        }
                      //  rm.setMorphWeights(entity,weights,0);
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("hairColor", Colors.RgbaType.SRGB,1.0f,1.0f,1.0f,1.0f)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairColor", Colors.RgbaType.SRGB,1.0f,1.0f,1.0f,1.0f)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("eyebrowColor", Colors.RgbaType.SRGB,0.0f,0.0f,0.0f,1.0f)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("lipsColor", Colors.RgbaType.SRGB,1.0f,0.0f,0.0f,1.0f)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("baseTexture", CustomizeAvatarLoader.instance.defaultFaceTexture, sampler)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("tattooTexture", CustomizeAvatarLoader.instance.emptyTexture, sampler)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("hairTexture", CustomizeAvatarLoader.instance.emptyTexture, sampler)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairTexture", CustomizeAvatarLoader.instance.emptyTexture, sampler)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("eyebrowTexture", CustomizeAvatarLoader.instance.defaultEyebrowTexture, sampler)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("lipsTexture", CustomizeAvatarLoader.instance.defaultLipTexture, sampler)
                    }
            if(assetName =="inner_mouth"){
//                for (index in asset.getMorphTargetNames(entity).indices) {
//                    weights[index] = 100.0f
//                }
                //rm.setMorphWeights(entity,weights,0);
//                for (item in asset.getMorphTargetNames(entity))
//                {
//                    Log.e("inner_mouth===",item.toString())
//                }
            }

                }
            }
        } else {
            fetchResourcesJob?.cancel()
            resourceLoader.asyncCancelLoad()
            resourceLoader.evictResourceData()
            headAsset?.let { asset ->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
                this.headAsset = null
            }
        }
    }

     fun LoadDefaultGlb(buffer: Buffer,category:String,gender: Gender){
        val centerPoint : Float3 = kDefaultObjectPosition

        if(category == "Top")
        {
            //ApiEvents.onShowLoading?.invoke()
            if(topAsset == null)
            {
                topAsset = assetLoader.createAsset(buffer)
                topAsset?.let { asset ->
                    resourceLoader.loadResources(asset)
                    asset.releaseSourceData()
                }
                topAsset?.let { asset ->
                    transformToUnitCube(asset)
                    //ApiEvents.onHideLoading?.invoke()
                    val rm = engine.renderableManager
                    for (entity in asset.entities)
                    {
                        val renderable = rm.getInstance(entity)
                        if (renderable == 0) {
                            continue
                        }
                        val assetName = asset.getName(entity).toString()
                        asset.getMorphTargetNames(entity)

                        if(assetName == "body")
                        {
                            rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.bodyMaterial)
                            val sampler = TextureSampler()
                            sampler.anisotropy = 8.0f
                        }
                        if(gender == Gender.Male){
                            if(assetName == "upperbody_sleeveless")
                            {
                                rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.maleTopMaterial)
                            }
                        }else{
                            if(assetName == "upperbody_sleeveless")
                            {
                                rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.femaleTopMaterial)
                            }
                        }
                    }
                }
            } else {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                topAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.topAsset = null
                }
            }
        }
        if(category == "Bottom") {
            if(bottomAsset == null) {
                bottomAsset = assetLoader.createAsset(buffer)
                bottomAsset?.let { asset ->
                    resourceLoader.loadResources(asset)
                    asset.releaseSourceData()
                }
                bottomAsset?.let { asset ->
                    transformToUnitCube(asset)
//                    val tm = engine.transformManager
//                    var center = asset.boundingBox.center.let { v -> Float3(v[0], v[1], v[2]) }
//                    val scaleFactor = 1.0f
//                    center -= centerPoint
//                    val transform = scale(Float3(scaleFactor)) * translation(-Float3(x=0.0f, y=0.0f, z=0.0f))
//                    //val transform = scale(Float3(scaleFactor)) * translation(-Float3(-center))
//                    tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())
                    val rm = engine.renderableManager
                    for (entity in asset.entities)
                    {
                        val renderable = rm.getInstance(entity)
                        if (renderable == 0) {
                            continue
                        }
                        val assetName = asset.getName(entity).toString()
                        asset.getMorphTargetNames(entity)
                        Log.v("=====",assetName)
                        if(assetName == "body")
                        {
                            rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.bodyMaterial)
                            val sampler = TextureSampler()
                            sampler.anisotropy = 8.0f
                        }
                        if(gender == Gender.Male){
                            if(assetName == "lowerbody_till_knee")
                            {
                                rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.maleBottomMaterial)
                            }
                        }else{
                            if(assetName == "lowerbody_till_knee")
                            {
                                rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.femaleBottomMaterial)
                            }
                        }
                    }
                }
            } else {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                bottomAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.bottomAsset = null
                }
            }
        }
        if(category == "Handwear") {
            if(handwearAsset == null) {
                handwearAsset = assetLoader.createAsset(buffer)
                handwearAsset?.let { asset ->
                    resourceLoader.loadResources(asset)
                    asset.releaseSourceData()
                }
                handwearAsset?.let { asset ->
                    transformToUnitCube(asset)
//                    val tm = engine.transformManager
//                    var center = asset.boundingBox.center.let { v -> Float3(v[0], v[1], v[2]) }
//                    val scaleFactor = 1.0f
//                    center -= centerPoint
//                    val transform = scale(Float3(scaleFactor)) * translation(-Float3(x=0.0f, y=0.0f, z=0.0f))
//                    //val transform = scale(Float3(scaleFactor)) * translation(-Float3(-center))
//                    tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())
                    val rm = engine.renderableManager
                    for (entity in asset.entities)
                    {
                        val renderable = rm.getInstance(entity)
                        if (renderable == 0) {
                            continue
                        }
                        val assetName = asset.getName(entity).toString()
                        asset.getMorphTargetNames(entity)
                        if(assetName == "body")
                        {
                            rm.setMaterialInstanceAt(renderable,0,CustomizeAvatarLoader.instance.bodyMaterial)
                            val sampler = TextureSampler()
                            sampler.anisotropy = 8.0f
                        }
                    }
                }
            } else {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                handwearAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.handwearAsset = null
                }
            }
        }
        if(category == "Footwear") {
            if(footwearAsset == null) {
                footwearAsset = assetLoader.createAsset(buffer)
                footwearAsset?.let { asset ->
                    resourceLoader.loadResources(asset)
                    asset.releaseSourceData()
                }
                footwearAsset?.let { asset ->
                    transformToUnitCube(asset)
//                    val tm = engine.transformManager
//                    var center = asset.boundingBox.center.let { v -> Float3(v[0], v[1], v[2]) }
//                    val scaleFactor = 1.0f
//                    center -= centerPoint
//                    val transform = scale(Float3(scaleFactor)) * translation(-Float3(x=0.0f, y=0.0f, z=0.0f))
//                    //val transform = scale(Float3(scaleFactor)) * translation(-Float3(-center))
//                    tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())


                    val rm = engine.renderableManager
                    for (entity in asset.entities)
                    {
                        val renderer = rm.getInstance(entity)
                        if (renderer == 0) {
                            continue
                        }
                        val assetName = asset.getName(entity).toString()
                        asset.getMorphTargetNames(entity)
                        if(assetName == "body")
                        {
                            rm.setMaterialInstanceAt(renderer,0,CustomizeAvatarLoader.instance.bodyMaterial)
                            val sampler = TextureSampler()
                            sampler.anisotropy = 8.0f
                        }
                    }
                }
            } else {
                fetchResourcesJob?.cancel()
                resourceLoader.asyncCancelLoad()
                resourceLoader.evictResourceData()
                footwearAsset?.let { asset ->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    this.footwearAsset = null
                }
            }
        }
    }
    fun  LoadMissingAfterRemovingSameModle(modelData: EconomyItem)
    {
        var loadTop = false
        var loadBottom = false
        var loadFootwear = false
        var loadHandwear = false

        if (modelData.ItemCategory == "Top")
        {
            var isTopPresent = true
            var isbottomPresent = true
            var isFootwearPresent = true

            if(footwearAsset == null)
            {
                isFootwearPresent = false
            }
            if(topAsset == null)
            {
                isTopPresent = false
            }
            if(bottomAsset == null)
            {
                isbottomPresent = false
            }
            loadTop = !isTopPresent
            loadBottom = !isbottomPresent
            loadFootwear = !isFootwearPresent
        }

        if (modelData.ItemCategory == "Bottom")
        {
            var isBottomPresent = true
            var isTopPresent = true
            var isFootwearPresent = true

            if(footwearAsset == null)
            {
                isFootwearPresent = false
            }
            if(topAsset == null)
            {
                isTopPresent = false
            }
            if(bottomAsset == null)
            {
                isBottomPresent = false
            }
            loadBottom = !isBottomPresent
            loadTop = !isTopPresent
            loadFootwear = !isFootwearPresent
        }

        if (modelData.ItemCategory == "Outfit")
        {
            var isFootwearPresent = true
            var isTopPresent = true
            var isBottomPresent = true
            var isHandPresent = true

            if(footwearAsset == null)
            {
                isFootwearPresent = false
            }
            if(handwearAsset == null)
            {
                isHandPresent = false
            }
            if(topAsset == null)
            {
                isTopPresent = false
            }
            if(bottomAsset == null)
            {
                isBottomPresent = false
            }

            loadTop = !isTopPresent
            loadBottom = !isBottomPresent
            loadFootwear = !isFootwearPresent
            loadHandwear = !isHandPresent
        }
        if (modelData.ItemCategory == "Footwear")
        {
            var isFootwearPresent = true
            if(footwearAsset == null)
            {
                isFootwearPresent = false
            }
            loadFootwear = !isFootwearPresent
        }
        if (modelData.ItemCategory == "Handwear")
        {
            var isHandPResent = true
            if(handwearAsset == null)
            {
                isHandPResent = false
            }
            loadHandwear = !isHandPResent
        }

        if (loadTop)
        {
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Male)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelMaleData[0])
            }
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Female)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelFemaleData[0])
            }
            CustomizeAvatarLoader.instance.onLoadQueDefaultModel{

            }
        }
        if (loadBottom)
        {
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Male)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelMaleData[1])
            }
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Female)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelFemaleData[1])
            }
            CustomizeAvatarLoader.instance.onLoadQueDefaultModel{

            }
        }
        if (loadFootwear)
        {
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Male)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelMaleData[2])
            }
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Female)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelFemaleData[2])
            }
            CustomizeAvatarLoader.instance.onLoadQueDefaultModel{

            }
        }
        if (loadHandwear)
        {
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Male)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelMaleData[3])
            }
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Female)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelFemaleData[3])
            }
            CustomizeAvatarLoader.instance.onLoadQueDefaultModel{

            }
        }
    }
    fun  LoadMissingAfterModelLoad(modelData: EconomyItem)
    {
        var loadTop = false
        var loadBottom = false
        var loadFootwear = false
        var loadHandwear = false

        if (modelData.ItemCategory == "Top")
        {
            var isbottomPresent = true
            var isFootwearPresent = true

            if(bottomAsset == null)
            {
                isbottomPresent = false
            }
            if(footwearAsset == null)
            {
                isFootwearPresent = false
            }

            loadBottom = !isbottomPresent
            loadFootwear = !isFootwearPresent
        }
        if (modelData.ItemCategory == "Bottom")
        {
            var isTopPresent = true
            var isFootwearPresent = true

            if(topAsset == null)
            {
                isTopPresent = false
            }
            if(footwearAsset == null)
            {
                isFootwearPresent = false
            }
            loadTop = !isTopPresent
            loadFootwear = !isFootwearPresent
        }
        if (modelData.ItemCategory == "Outfit")
        {
            var isFootwearPresent = true
            var isHandPResent = true
            if(footwearAsset == null)
            {
                isFootwearPresent = false
            }
            if(handwearAsset == null)
            {
                isHandPResent = false
            }
            loadFootwear = !isFootwearPresent
            loadHandwear = !isHandPResent
        }
        if (modelData.ItemCategory == "Footwear")
        {
            var isTopPresent = true
            var isBottomPresent = true
            var isOutfitpresnet = true
            if(bottomAsset == null)
            {
                isBottomPresent = false
            }
            if(topAsset == null)
            {
                isTopPresent = false
            }
            if(outfitAsset == null)
            {
                isOutfitpresnet = false
            }

            if (!isOutfitpresnet)
            {
                loadTop = !isTopPresent
                loadBottom = !isBottomPresent
            }
        }

        if (loadTop)
        {
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Male)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelMaleData[0])
            }
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Female)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelFemaleData[0])
            }
            CustomizeAvatarLoader.instance.onLoadQueDefaultModel{

            }
        }
        if (loadBottom)
        {
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Male)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelMaleData[1])
            }
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Female)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelFemaleData[1])
            }
            CustomizeAvatarLoader.instance.onLoadQueDefaultModel{

            }
        }
        if (loadFootwear)
        {
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Male)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelMaleData[2])
            }
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Female)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelFemaleData[2])
            }
            CustomizeAvatarLoader.instance.onLoadQueDefaultModel{

            }
        }
        if (loadHandwear)
        {
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Male)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelMaleData[3])
            }
            if(CustomizeAvatarLoader.instance.getGender() == Gender.Female)
            {
                CustomizeAvatarLoader.instance.loadDefaultModelList.add( DefaultAvatarData.instance.modelFemaleData[3])
            }
            CustomizeAvatarLoader.instance.onLoadQueDefaultModel{

            }
        }
    }

    fun loadNetworkModel(buffer: Buffer?, modelData: EconomyItem, addNew:Boolean)
    {
        if(AvataryugData.instance.isBodyCategory(modelData.ItemCategory))
        {
            loadBodyPart(buffer,modelData,addNew)
        }
        if(modelData.ItemCategory == "Wristwear")
        {
            loadBodyWears(buffer,modelData.ItemCategory,addNew)
        }
        if(modelData.ItemCategory == "Hair")
        {
            loadHairs(buffer,modelData,addNew)
        }
        if(modelData.ItemCategory == "Facialhair")
        {
            loadFacialhair(buffer,modelData, addNew)
        }
        if(modelData.ItemCategory == "Eyewear")
        {
            loadEyewears(buffer,modelData,addNew)
        }
        if(modelData.ItemCategory == "Headwear")
        {
            loadHeadwear(buffer,modelData,addNew)
        }
        if(modelData.ItemCategory == "Neckwear")
        {
            loadNeckwear(buffer,modelData, addNew  )
        }
        if(modelData.ItemCategory == "Mouthwear" || modelData.ItemCategory == "Earwear"||
            modelData.ItemCategory == "Eyebrowswear" || modelData.ItemCategory == "Facewear" ||
            modelData.ItemCategory == "Nosewear"
              )
        {
            loadNeckwear(buffer,modelData, addNew)
        }
    }

    private fun loadNeckwear(buffer: Buffer?, modelData: EconomyItem , addNew:Boolean) {
            var buckentName = ""
            buckentName = if (modelData.CoreBucket.split("/").count() > 1) {
                modelData.CoreBucket.split("/")[1]
            } else {
                modelData.CoreBucket.split("/")[0]
            }
            var isModelPresent = false
            if (loadedFacewearList.isNotEmpty()) {
                var index = -1
                loadedFacewearList.forEachIndexed { ind, value ->
                    if (value.id == modelData.ID) {
                        index = ind
                    }
                }
                if (index > -1) {
                    isModelPresent = true
                    val neckwearAsset: FilamentAsset? = loadedFacewearList.get(index = index).asset
                    if (neckwearAsset != null) {
                        fetchResourcesJob?.cancel()
                        resourceLoader.asyncCancelLoad()
                        resourceLoader.evictResourceData()
                        neckwearAsset.let { asset ->
                            this.scene.removeEntities(asset.entities)
                            assetLoader.destroyAsset(asset)
                        }
                    }
                    loadedFacewearList.removeAt(index)
                }
            }
            if (isModelPresent) {

            } else {
                if(modelData.ConflictingBuckets.isNotEmpty()) {
                    val conflictNames: MutableList<ConflictName> = modelData.ConflictingBuckets
                    conflictNames.forEach {
                        removeconflictModel(it.name)
                    }
                }
                removeconflictModel(buckentName)

                val facewear = FacewearPoints.instance.facewearPointList.first { it.bucketName == buckentName }
                val asset = buffer?.let { assetLoader.createAsset(it) }
                asset?.let { hairaset ->
                    resourceLoader.loadResources(hairaset)
                    hairaset.releaseSourceData()
                }
                asset?.let { hairs ->
                    val tm = engine.transformManager
                    val transform = scale(Float3(0.35f)) * translation(
                        Float3(
                            x = facewear.position.x,
                            y = facewear.position.y - 1,
                            z = facewear.position.z
                        )
                    )
                    tm.setTransform(tm.getInstance(hairs.root), transpose(transform).toFloatArray())
                }
                val loadfacewear = LoadedFacewearAssets()
                loadfacewear.asset = asset
                loadfacewear.bucketname = buckentName
                loadedFacewearList.add(loadfacewear)
            }

    }

    private  fun  removeconflictModel(bucketname:String){
        if(loadedFacewearList.isNotEmpty())
        {
            var index = -1
            loadedFacewearList.forEachIndexed { ind, value ->
                if(value.bucketname == bucketname)
                {
                    index = ind
                }
            }
            if(index > -1)
            {
                val item = EconomyItem()
                item.ID = loadedFacewearList.get(index = index).id
                //DataHolder.Instance.removePart(item)
                val conflictAsset : FilamentAsset? = loadedFacewearList.get(index = index).asset
                if(conflictAsset != null)
                {
                    fetchResourcesJob?.cancel()
                    resourceLoader.asyncCancelLoad()
                    resourceLoader.evictResourceData()
                    conflictAsset?.let { asset ->
                        this.scene.removeEntities(asset.entities)
                        assetLoader.destroyAsset(asset)
                    }
                }
                loadedFacewearList.removeAt(index)
            }
        }
    }

    private fun getConflictID(bucketName:String):String{
        var id= ""
        var index = -1
        loadedFacewearList.forEachIndexed { ind, value ->
            if(value.bucketname == bucketName)
            {
                index = ind
            }
        }
        if(index > -1)
        {
            id = loadedFacewearList.get(index = index).id
        }
        return  id
    }

    private fun loadHeadwear(buffer: Buffer?, modelData: EconomyItem, addNew:Boolean ) {
        var buckentname = ""
        buckentname = if(modelData.CoreBucket.split("/").count() > 1){
            modelData.CoreBucket.split("/")[1]
        }else {
            modelData.CoreBucket.split("/")[0]
        }
        if(loadedFacewearList.isNotEmpty()) {
            var index = -1
            loadedFacewearList.forEachIndexed { ind, value ->
                if(value.bucketname == buckentname)
                {
                    index = ind
                }
            }
            if(index > -1)
            {
                val hairaset : FilamentAsset? = loadedFacewearList.get(index = index).asset
                if(hairaset != null)
                {
                    fetchResourcesJob?.cancel()
                    resourceLoader.asyncCancelLoad()
                    resourceLoader.evictResourceData()
                    hairaset?.let { asset ->
                        this.scene.removeEntities(asset.entities)
                        assetLoader.destroyAsset(asset)
                    }
                }
                loadedFacewearList.removeAt(index)
            }
        }

        if(addNew)
        {
            if(modelData.ConflictingBuckets.isNotEmpty()) {
                val conflictNames: MutableList<ConflictName> = modelData.ConflictingBuckets

                conflictNames.forEach {
                    removeconflictModel(it.name)
                }
            }
            removeconflictModel(buckentname)

            val facewear =  FacewearPoints.instance.facewearPointList.first{it.bucketName == buckentname}
            val asset = buffer?.let { assetLoader.createAsset(it) }
            asset?.let { hairaset->
                resourceLoader.loadResources(hairaset)
                hairaset.releaseSourceData()
            }
            asset?.let { hairs ->
                val tm = engine.transformManager
                val transform = scale(Float3(0.35f)) * translation(Float3(x=facewear.position.x, y=facewear.position.y -1, z=facewear.position.z))
                tm.setTransform(tm.getInstance(hairs.root),transpose(transform).toFloatArray())
            }
            var loadfacewear = LoadedFacewearAssets()
            loadfacewear.asset = asset
            loadfacewear.bucketname = buckentname
            loadedFacewearList.add(loadfacewear)
        }
    }
    private fun loadEyewears(buffer: Buffer?, modelData: EconomyItem, addNew:Boolean ) {
        var buckentname = ""
        buckentname = if(modelData.CoreBucket.split("/").count() > 1){
            modelData.CoreBucket.split("/")[1]
        }else {
            modelData.CoreBucket.split("/")[0]
        }
        if(loadedFacewearList.isNotEmpty()) {
            var index = -1
            loadedFacewearList.forEachIndexed { ind, value ->
                if(value.bucketname == buckentname)
                {
                    index = ind
                }
            }
            if(index > -1)
            {
                val hairaset : FilamentAsset? = loadedFacewearList.get(index = index).asset
                if(hairaset != null)
                {
                    fetchResourcesJob?.cancel()
                    resourceLoader.asyncCancelLoad()
                    resourceLoader.evictResourceData()
                    hairaset?.let { asset ->
                        this.scene.removeEntities(asset.entities)
                        assetLoader.destroyAsset(asset)
                    }
                }
                loadedFacewearList.removeAt(index)
            }
        }

        if(addNew)
        {
            if(modelData.ConflictingBuckets.isNotEmpty()) {
                val conflictNames: MutableList<ConflictName> = modelData.ConflictingBuckets

                conflictNames.forEach {
                    removeconflictModel(it.name)
                }
            }
            removeconflictModel(buckentname)

            val facewear =  FacewearPoints.instance.facewearPointList.first{it.bucketName == buckentname}
            val asset = buffer?.let { assetLoader.createAsset(it) }
            asset?.let { hairaset->
                resourceLoader.loadResources(hairaset)
                hairaset.releaseSourceData()
            }
            asset?.let { hairs ->
                val tm = engine.transformManager
                val transform = scale(Float3(0.35f)) * translation(Float3(x=facewear.position.x, y=facewear.position.y -1, z=facewear.position.z))
                tm.setTransform(tm.getInstance(hairs.root),transpose(transform).toFloatArray())
            }
            val loadfacewear = LoadedFacewearAssets()
            loadfacewear.asset = asset
            loadfacewear.bucketname = buckentname
            loadedFacewearList.add(loadfacewear)
        }
    }
    private fun loadHairs(buffer: Buffer?, modelData: EconomyItem, addNew:Boolean ) {
        var buckentname = ""
        buckentname = if(modelData.CoreBucket.split("/").count() > 1){
            modelData.CoreBucket.split("/")[1]
        }else {
            modelData.CoreBucket.split("/")[0]
        }
        if(loadedFacewearList.isNotEmpty()) {
            var index = -1
            loadedFacewearList.forEachIndexed { ind, value ->
                if(value.bucketname == buckentname)
                {
                    index = ind
                }
            }
            if(index > -1)
            {
                val hairaset : FilamentAsset? = loadedFacewearList.get(index = index).asset
                if(hairaset != null)
                {
                    fetchResourcesJob?.cancel()
                    resourceLoader.asyncCancelLoad()
                    resourceLoader.evictResourceData()
                    hairaset?.let { asset ->
                        this.scene.removeEntities(asset.entities)
                        assetLoader.destroyAsset(asset)
                    }
                }
                loadedFacewearList.removeAt(index)
            }
        }

        if(addNew)
        {
            if(modelData.ConflictingBuckets.isNotEmpty()) {
                val conflictNames: MutableList<ConflictName> = modelData.ConflictingBuckets

                conflictNames.forEach {
                    removeconflictModel(it.name)
                }
            }
            removeconflictModel(buckentname)



            val facewear =  FacewearPoints.instance.facewearPointList.first{it.bucketName == buckentname}
            val asset = buffer?.let { assetLoader.createAsset(it) }
            asset?.let { hairaset->
                resourceLoader.loadResources(hairaset)
                hairaset.releaseSourceData()
            }
            asset?.let { hairs ->

                for (entity in hairs.entities)
                {
                    val assetName = hairs.getName(entity).toString()
                    val renderable = engine.renderableManager.getInstance(entity)
                    if (renderable == 0)  {
                        continue
                    }
                    val matarial = engine.renderableManager.getMaterialInstanceAt(renderable,0)

                    matarial.material.parameters.forEachIndexed { ind, value ->
                    }
                    matarial.setParameter("baseColorFactor", Colors.RgbaType.SRGB,0.0f,0.0f,0.0f,1.0f)
                }
                val tm = engine.transformManager
                val transform = scale(Float3(0.35f)) * translation(Float3(x=facewear.position.x, y=facewear.position.y -1, z=facewear.position.z))
                tm.setTransform(tm.getInstance(hairs.root),transpose(transform).toFloatArray())
            }
            val loadfacewear = LoadedFacewearAssets()
            loadfacewear.asset = asset
            loadfacewear.bucketname = buckentname
            loadedFacewearList.add(loadfacewear)
        }
    }

    private fun loadFacialhair(buffer: Buffer?, modelData: EconomyItem, addNew:Boolean ) {
        var buckentname = ""
        buckentname = if(modelData.CoreBucket.split("/").count() > 1){
            modelData.CoreBucket.split("/")[1]
        }else {
            modelData.CoreBucket.split("/")[0]
        }

        if(loadedFacewearList.isNotEmpty()) {
            var index = -1
            loadedFacewearList.forEachIndexed { ind, value ->
                if(value.bucketname == buckentname)
                {
                    index = ind
                }
            }
            if(index > -1)
            {
                val hairaset : FilamentAsset? = loadedFacewearList.get(index = index).asset
                if(hairaset != null)
                {
                    fetchResourcesJob?.cancel()
                    resourceLoader.asyncCancelLoad()
                    resourceLoader.evictResourceData()
                    hairaset?.let { asset ->
                        this.scene.removeEntities(asset.entities)
                        assetLoader.destroyAsset(asset)
                    }
                }
                loadedFacewearList.removeAt(index)
            }
        }


        if(addNew)
        {
            if(modelData.ConflictingBuckets.isNotEmpty()) {
                val conflictNames: MutableList<ConflictName> = modelData.ConflictingBuckets

                conflictNames.forEach {
                    removeconflictModel(it.name)
                }
            }
            removeconflictModel(buckentname)


            val facewear =  FacewearPoints.instance.facewearPointList.first{it.bucketName == buckentname}
            val asset = buffer?.let { assetLoader.createAsset(it) }
            asset?.let { hairaset->
                resourceLoader.loadResources(hairaset)
                hairaset.releaseSourceData()
            }
            asset?.let { hairs ->

                for (entity in hairs.entities)
                {
                    val assetName = hairs.getName(entity).toString()
                    val renderable = engine.renderableManager.getInstance(entity)
                    if (renderable == 0)  {
                        continue
                    }
                    val matarial = engine.renderableManager.getMaterialInstanceAt(renderable,0)

                    matarial.material.parameters.forEachIndexed { ind, value ->
                    }
                    matarial.setParameter("baseColorFactor", Colors.RgbaType.SRGB,0.0f,0.0f,0.0f,1.0f)
                }
                val tm = engine.transformManager
                val transform = scale(Float3(0.35f)) * translation(Float3(x=facewear.position.x, y=facewear.position.y -1, z=facewear.position.z))
                tm.setTransform(tm.getInstance(hairs.root),transpose(transform).toFloatArray())
            }
            val loadfacewear = LoadedFacewearAssets()
            loadfacewear.asset = asset
            loadfacewear.bucketname = buckentname
            loadedFacewearList.add(loadfacewear)
        }
    }
    /**
     * Frees all entities associated with the most recently-loaded model.
     */
    fun destroyModel() {
        fetchResourcesJob?.cancel()
        resourceLoader.asyncCancelLoad()
        resourceLoader.evictResourceData()
//        for(item in assetListed)
//        {
//            this.scene.removeEntities(item.entities)
//            assetLoader.destroyAsset(item)
//            this.asset = null
//            this.animator = null
//        }
        headAsset?.let { asset->
            this.scene.removeEntities(asset.entities)
            assetLoader.destroyAsset(asset)
            this.headAsset = null
        }
        topAsset?.let { asset->
            this.scene.removeEntities(asset.entities)
            assetLoader.destroyAsset(asset)
            this.topAsset = null
        }
        bottomAsset?.let { asset->
            this.scene.removeEntities(asset.entities)
            assetLoader.destroyAsset(asset)
            this.bottomAsset = null
        }
        outfitAsset?.let { asset->
            this.scene.removeEntities(asset.entities)
            assetLoader.destroyAsset(asset)
            this.outfitAsset = null
        }
        for (entry in loadedFacewearList) {
            entry.asset?.let { asset->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
            }
        }
        handwearAsset?.let { asset->
            this.scene.removeEntities(asset.entities)
            assetLoader.destroyAsset(asset)
            this.handwearAsset = null
        }
        footwearAsset?.let { asset->
            this.scene.removeEntities(asset.entities)
            assetLoader.destroyAsset(asset)
            this.footwearAsset = null
        }
        wristwearAsset?.let { asset->
            this.scene.removeEntities(asset.entities)
            assetLoader.destroyAsset(asset)
            this.wristwearAsset = null
        }

    }

    /**
     * Renders the model and updates the Filament camera.
     *
     * @param frameTimeNanos time in nanoseconds when the frame started being rendered,
     *                       typically comes from {@link android.view.Choreographer.FrameCallback}
     */
    fun render(frameTimeNanos: Long) {
        if (!uiHelper.isReadyToRender) {
            return
        }
        // Allow the resource loader to finalize textures that have become ready.
        resourceLoader.asyncUpdateLoad()
        // Add renderable entities to the scene as they become ready.
        headAsset?.let { populateScene(it) }
        topAsset?.let { populateScene(it) }
        bottomAsset?.let { populateScene(it) }
        outfitAsset?.let { populateScene(it) }
        handwearAsset?.let { populateScene(it) }
        footwearAsset?.let { populateScene(it) }
        wristwearAsset?.let { populateScene(it) }
        for (entry in loadedFacewearList) {
            entry.asset?.let { it->
                populateScene(it)
            }
        }
        val eyePos1 = DoubleArray(3)
        val target = DoubleArray(3)
        val upward = DoubleArray(3)

        cameraManipulator.getLookAt(eyePos, target, upward)
        camera.lookAt(
            eyePos[0], eyePos[1], eyePos[2],
            target[0], target[1], target[2],
            upward[0], upward[1], upward[2])
//        camera.lookAt(
//            0.0, 2.0,3.0,
//            0.0, 1.0, 0.0,
//            0.0, 1.0, 0.0)
        // Render the scene, unless the renderer wants to skip the frame.
        if (renderer.beginFrame(swapChain!!, frameTimeNanos)) {
            renderer.render(view)
            renderer.endFrame()
        }
    }


    private fun populateScene(asset: FilamentAsset) {
        val rcm = engine.renderableManager
        var count = 0
        val popRenderables = { count = asset.popRenderables(readyRenderables); count != 0 }
        while (popRenderables()) {
            for (i in 0..count - 1) {
                val ri = rcm.getInstance(readyRenderables[i])
                rcm.setScreenSpaceContactShadows(ri, true)
            }
            scene.addEntities(readyRenderables.take(count).toIntArray())
        }
        scene.addEntities(asset.lightEntities)
    }

    private fun addDetachListener(view: android.view.View) {
        view.addOnAttachStateChangeListener(object : android.view.View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: android.view.View) {}
            override fun onViewDetachedFromWindow(v: android.view.View) {
                uiHelper.detach()

                destroyModel()
                assetLoader.destroy()
                materialProvider.destroyMaterials()
                materialProvider.destroy()
                resourceLoader.destroy()

                engine.destroyEntity(light)
                engine.destroyRenderer(renderer)
                engine.destroyView(this@CustomModelViewer.view)
                engine.destroyScene(scene)
                engine.destroyCameraComponent(camera.entity)
                EntityManager.get().destroy(camera.entity)

                EntityManager.get().destroy(light)

                engine.destroy()
            }
        })
    }

    /**
     * Handles a [MotionEvent] to enable one-finger orbit, two-finger pan, and pinch-to-zoom.
     */
    fun onTouchEvent(event: MotionEvent) {
        gestureDetector.onTouchEvent(event)
    }

    @SuppressWarnings("ClickableViewAccessibility")
    override fun onTouch(view: android.view.View, event: MotionEvent): Boolean {
        onTouchEvent(event)
        return true
    }

    private suspend fun fetchResources(asset: FilamentAsset, callback: (String) -> Buffer) {
        val items = HashMap<String, Buffer>()
        val resourceUris = asset.resourceUris
        for (resourceUri in resourceUris) {
            items[resourceUri] = callback(resourceUri)
        }

        withContext(Dispatchers.Main) {
            for ((uri, buffer) in items) {
                resourceLoader.addResourceData(uri, buffer)
            }
            resourceLoader.asyncBeginLoad(asset)
            asset.releaseSourceData()
        }
    }

    private fun updateCameraProjection() {
        val width = view.viewport.width
        val height = view.viewport.height
        val aspect = width.toDouble() / height.toDouble()
        camera.setLensProjection(cameraFocalLength.toDouble(), aspect, kNearPlane, kFarPlane)
    }

    inner class SurfaceCallback : UiHelper.RendererCallback {
        override fun onNativeWindowChanged(surface: Surface) {
            swapChain?.let { engine.destroySwapChain(it) }
            swapChain = engine.createSwapChain(surface)
            surfaceView?.let { displayHelper.attach(renderer, it.display) }
            textureView?.let { displayHelper.attach(renderer, it.display) }
        }

        override fun onDetachedFromSurface() {
            displayHelper.detach()
            swapChain?.let {
                engine.destroySwapChain(it)
                engine.flushAndWait()
                swapChain = null
            }
        }

        override fun onResized(width: Int, height: Int) {
            view.viewport = Viewport(0, 0, width, height)
            cameraManipulator.setViewport(width, height)
            updateCameraProjection()
        }
    }

    companion object {
        private val kDefaultObjectPosition = Float3(0.0f, 0.0f, 0.0f)
    }

    private fun loadBodyWears(buffer: Buffer?, category:String, addNew:Boolean ) {
        if (wristwearAsset != null) {
            fetchResourcesJob?.cancel()
            resourceLoader.asyncCancelLoad()
            resourceLoader.evictResourceData()
            wristwearAsset?.let { asset ->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
                this.wristwearAsset = null
            }
        }
        if(addNew)
        {
            wristwearAsset = buffer?.let { assetLoader.createAsset(it) }
            wristwearAsset?.let { wristwearAsset->
                resourceLoader.loadResources(wristwearAsset)
                wristwearAsset.releaseSourceData()
            }
            wristwearAsset?.let { wristwear ->
                if(headAsset != null) {
                    headAsset?.let { headAsset ->
                        for (entity in headAsset.entities) {
                            var assetName = headAsset.getName(entity).toString()
                            if(assetName.contains("LeftForeArm")){
                                val tm = engine.transformManager
                                val transformation = FloatArray(16)
                                tm.getTransform(tm.getInstance(entity),transformation)
                                val transform = scale(Float3(0.35f)) * translation(Float3(x=0.4919388f, y=1.221702f -1, z=0.05310341f))  * rotation(Float3(329.0f,158.0f,0.0f),RotationsOrder.XYZ)
                                tm.setTransform(tm.getInstance(wristwear.root),transpose(transform).toFloatArray())
                            }
                        }
                    }
                }
            }
        }
    }

    fun transformToUnitCube(assets: FilamentAsset, centerPoint: Float3 = kDefaultObjectPosition) {
        assets?.let { asset ->
            val tm = engine.transformManager
            val transform = scale(Float3(0.35f)) * translation(Float3(0.0f, -1.0f, -0.0f))
            tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())
        }
    }
    fun camCos()
    {
        val eyeX: Double = 0.0
        val eyeY: Double = 0.0
        val eyeZ: Double = 5.0

// Assuming you have Double variables for targetX, targetY, targetZ
        val targetX: Double = 0.0
        val targetY: Double = 0.0
        val targetZ: Double = 0.0

// Assuming you have Double variables for upX, upY, upZ
        val upX: Double = 0.0
        val upY: Double = 1.0  // Example: up vector in the positive y-axis (upward direction)
        val upZ: Double = 0.0

        camera.lookAt(
            eyeX, eyeY , eyeZ,  // Adjust eyeY to move the camera down
            targetX, targetY, targetZ,
            upX, upY, upZ
        )
//        val viewMatrix = camera.
//        viewMatrix[13] -= distance  // Adjust the translation value in the view matrix
//        camera.view.viewMatrix = viewMatrix

//        val eyePos1 = DoubleArray(3)
//        val target = DoubleArray(3)
//        val upward = DoubleArray(3)
//
//
//        camera.lookAt(
//            eyePos1[0], eyePos1[1], eyePos1[0],
//            target[0], target[1], target[2],
//            upward[0], upward[0], upward[2])
//        cameraManipulator.getLookAt(eyePos1, target, upward)
//        camera.lookAt(
//            0.0, 2.0,3.0,
//            0.0, 1.0, 0.0,
//            0.0, 1.0, 0.0)
        // Render the scene, unless the renderer wants to skip the frame.
//        if (renderer.beginFrame(swapChain!!, 10000)) {
            renderer.render(view)

//            renderer.endFrame()
//        }
    }
    fun RemoveAllModel(){

        if(topAsset != null) {
            topAsset?.let { asset ->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
                this.topAsset = null
            }
        }
        if(bottomAsset != null) {
            bottomAsset?.let { asset ->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
                this.bottomAsset = null
            }
        }

        if(outfitAsset != null) {
            outfitAsset?.let { asset ->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
                this.outfitAsset = null
            }
        }

        if(handwearAsset != null) {
            handwearAsset?.let { asset ->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
                this.handwearAsset = null
            }
        }

        if(footwearAsset != null) {
            footwearAsset?.let { asset ->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
                this.footwearAsset = null
            }
        }

        if(wristwearAsset != null) {
            wristwearAsset?.let { asset ->
                this.scene.removeEntities(asset.entities)
                assetLoader.destroyAsset(asset)
                this.wristwearAsset = null
            }
        }

            for (entry in loadedFacewearList) {
                entry.asset?.let { asset->
                    this.scene.removeEntities(asset.entities)
                    assetLoader.destroyAsset(asset)
                    entry.asset = null
                }
            }
        loadedFacewearList.clear()

    }
}