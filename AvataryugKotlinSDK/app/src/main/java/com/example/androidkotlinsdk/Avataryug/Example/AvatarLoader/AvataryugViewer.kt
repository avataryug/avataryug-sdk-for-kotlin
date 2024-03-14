package Avataryug.Example.AvatarLoader

import Avataryug.Class.AvatarLoader.CommonFunction
import Avataryug.Class.AvatarLoader.EconomyItemHolder
import Avataryug.Class.AvatarLoader.AvatarViewerClass
import Avataryug.Class.AvatarLoader.CustomModelViewer
import Avataryug.Class.AvatarLoader.CustomizeAvatarLoader
import Avataryug.Class.AvatarLoader.Gender
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Choreographer
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.Avataryug.client.Handler.EconomyHandler
import com.Avataryug.client.Models.GetEconomyItemsResult
import com.example.androidkotlinsdk.R
import com.google.android.filament.Fence
import com.google.android.filament.Material
import com.google.android.filament.Skybox
import com.google.android.filament.Texture
import com.google.android.filament.TextureSampler
import com.google.android.filament.android.TextureHelper
import com.google.android.filament.utils.KTX1Loader
import com.google.android.filament.utils.Utils
import kotlinx.coroutines.runBlocking
import java.nio.ByteBuffer

class AvataryugViewer : AppCompatActivity(){

    companion object {
        init {
            Utils.init()
        }
    }


    private val frameScheduler = FrameCallback()
    private val doubleTapListener = DoubleTapListener()
    private lateinit var doubleTapDetector: GestureDetector
    private var loadStartFence: Fence? = null
    private  var loadingPanel : ConstraintLayout? = null
    private var topButton:Button? =null
    private var bottomButton:Button? =null
    private var moreButton:Button? =null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize_full_avatar_panel)
        topButton = findViewById(R.id.topButton)
        bottomButton = findViewById(R.id.bottomButton)
        moreButton = findViewById(R.id.moreButton)

        topButton?.setOnClickListener {
            LoadModel("Top")
        }
        bottomButton?.setOnClickListener {
            LoadModel("Bottom")
        }
        moreButton?.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setMessage("For additional categories, please refer to the demo project or consult the documentation for more information")
                .setPositiveButton("OK") { dialog, id ->
                    // START THE GAME!
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        getReferenceToViews()

        setInitialValues()

        CustomizeAvatarLoader.instance.surfaceView = findViewById(R.id.customizeSurfaceView)
        AvatarViewerClass.instance.choreographer = Choreographer.getInstance()
        CustomizeAvatarLoader.instance.modelViewer = CustomModelViewer( CustomizeAvatarLoader.instance.surfaceView)
        AvatarViewerClass.instance.viewerContent.view =  CustomizeAvatarLoader.instance.modelViewer.view
        AvatarViewerClass.instance.viewerContent.sunlight =  CustomizeAvatarLoader.instance.modelViewer.light
        AvatarViewerClass.instance.viewerContent.lightManager =  CustomizeAvatarLoader.instance.modelViewer.engine.lightManager
        AvatarViewerClass.instance.viewerContent.scene =  CustomizeAvatarLoader.instance.modelViewer.scene
        AvatarViewerClass.instance.viewerContent.renderer =  CustomizeAvatarLoader.instance.modelViewer.renderer

        doubleTapDetector = GestureDetector(applicationContext, doubleTapListener)
        CustomizeAvatarLoader.instance.surfaceView.setOnTouchListener (CustomizeAvatarLoader.instance.modelViewer)

        val view =  CustomizeAvatarLoader.instance.modelViewer.view
        view.renderQuality = view.renderQuality.apply {
            hdrColorBuffer = com.google.android.filament.View.QualityLevel.ULTRA
        }

        // dynamic resolution often helps a lot
        view.dynamicResolutionOptions = view.dynamicResolutionOptions.apply {
            enabled = true
            quality = com.google.android.filament.View.QualityLevel.ULTRA
        }

        createIndirectLight("venetian_crossroads_2k")
        CustomizeAvatarLoader.instance.modelViewer.scene.skybox = Skybox.Builder().build(CustomizeAvatarLoader.instance.modelViewer.engine)
        CustomizeAvatarLoader.instance.modelViewer.scene.skybox?.setColor(1.0f, 1.0f, 1.0f, 1.0f) //White color
        loadDefaultTexture()

    }


    private fun getReferenceToViews()
    {
        loadingPanel = findViewById(R.id.loadingPanel)
    }

    private fun setInitialValues()
    {
        loadingPanel?.isVisible = false
    }

    private fun LoadModel(category: String){
            var gender = 0
            if(category == "Top" ||category == "Bottom" || category == "Outfit")
            {
                if(CustomizeAvatarLoader.instance.getGender() == Gender.Male){
                    gender = 1
                }
                else
                {
                    gender = 2
                }
            }
            else
            {
                gender = 3
            }

            loadingPanel?.isVisible =true


            val handler = EconomyHandler(EconomyHandler.GetEconomyItems(category, 1, gender,0,500))
            handler.getEconomyItems(object : EconomyHandler.OnGetEconomyItemsListener
            {
                override fun onGetEconomyItemsResult(result: GetEconomyItemsResult)
                {
                    runOnUiThread {
                        loadingPanel?.isVisible =false
                        EconomyItemHolder.instance.economyItems.clear()
                        EconomyItemHolder.instance.AddEconomItem(result){
                            CustomizeAvatarLoader.instance.loadNetworkModel(EconomyItemHolder.instance.economyItems[0])
                       }
                }}
                override fun onGetEconomyItemsError(error: Exception)
                        {
                            val errorText = "API Error: ${error.message}"
                            Log.e("GetEconomyItems ERROR--", errorText)
                        }
                    })

    }

    inner class DoubleTapListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            return super.onDoubleTap(e)
        }
    }
    private fun createIndirectLight(ibl:String) {

        val buffer = readAsset("envs/$ibl/${ibl}_ibl.ktx")
        KTX1Loader.createIndirectLight( CustomizeAvatarLoader.instance.modelViewer.engine, buffer).apply {
            intensity = 20_000f
            CustomizeAvatarLoader.instance.modelViewer.scene.indirectLight = this
        }
    }
    private fun readAsset(assetName: String): ByteBuffer {
        val input = assets.open(assetName)
        val bytes = ByteArray(input.available())
        input.read(bytes)
        return ByteBuffer.wrap(bytes)
    }
    override fun onResume() {
        super.onResume()
       AvatarViewerClass.instance.choreographer.postFrameCallback(frameScheduler)
    }

    override fun onPause() {
        super.onPause()
        AvatarViewerClass.instance.choreographer.removeFrameCallback(frameScheduler)
    }

    override fun onDestroy() {
        super.onDestroy()
        AvatarViewerClass.instance.choreographer.removeFrameCallback(frameScheduler)
    }

    inner class FrameCallback : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            AvatarViewerClass.instance.choreographer.postFrameCallback(this)

            loadStartFence?.let {
                if (it.wait(Fence.Mode.FLUSH, 0) == Fence.FenceStatus.CONDITION_SATISFIED) {
                    val end = System.nanoTime()
                    CustomizeAvatarLoader.instance.modelViewer.engine.destroyFence(it)
                    loadStartFence = null
                }
            }
            CustomizeAvatarLoader.instance.modelViewer.render(frameTimeNanos)
        }
    }
    fun loadDefaultTexture() = runBlocking {

        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val headMat = readAsset("Shaders/FaceMaterial1.filamat")
        CustomizeAvatarLoader.instance.faceMaterial = Material.Builder().payload(headMat,headMat.remaining()).build(CustomizeAvatarLoader.instance.modelViewer.engine).createInstance()
        val eyeballMat = readAsset("Shaders/EyeballMaterial.filamat")
        CustomizeAvatarLoader.instance.eyeballMaterial = Material.Builder().payload(eyeballMat,eyeballMat.remaining()).build(CustomizeAvatarLoader.instance.modelViewer.engine).createInstance()
        val bodyMat =readAsset("Shaders/BodyMaterial1.filamat")

        CustomizeAvatarLoader.instance.bodyMaterial = Material.Builder().payload(bodyMat,bodyMat.remaining()).build(CustomizeAvatarLoader.instance.modelViewer.engine).createInstance()


        val clothMaterial = readAsset("Shaders/ClothMaterial.filamat")
        CustomizeAvatarLoader.instance.femaleTopMaterial = Material.Builder().payload(clothMaterial,clothMaterial.remaining()).build(CustomizeAvatarLoader.instance.modelViewer.engine).createInstance()
        CustomizeAvatarLoader.instance.femaleBottomMaterial = Material.Builder().payload(clothMaterial,clothMaterial.remaining()).build(CustomizeAvatarLoader.instance.modelViewer.engine).createInstance()
        CustomizeAvatarLoader.instance.maleTopMaterial = Material.Builder().payload(clothMaterial,clothMaterial.remaining()).build(CustomizeAvatarLoader.instance.modelViewer.engine).createInstance()
        CustomizeAvatarLoader.instance.maleBottomMaterial = Material.Builder().payload(clothMaterial,clothMaterial.remaining()).build(CustomizeAvatarLoader.instance.modelViewer.engine).createInstance()

        val femaleTopMaterial  = loadTextur("Texture/Female/Top/diffuse.jpg")
        CustomizeAvatarLoader.instance.femaleTopMaterial.setParameter("baseTexture",femaleTopMaterial,sampler)

        val femaleBottomMaterial  = loadTextur("Texture/Female/Bottom/diffuse.jpg")
        CustomizeAvatarLoader.instance.femaleBottomMaterial.setParameter("baseTexture",femaleBottomMaterial,sampler)

        val maleTopMaterial  = loadTextur("Texture/Male/Top/diffuse.jpg")
        CustomizeAvatarLoader.instance.maleTopMaterial.setParameter("baseTexture",maleTopMaterial,sampler)

        val maleBottomMaterial  = loadTextur("Texture/Male/Bottom/diffuse.jpg")
        CustomizeAvatarLoader.instance.maleBottomMaterial.setParameter("baseTexture",maleBottomMaterial,sampler)

        val emptyTex  = loadTextur("Texture/empty.png")
        CustomizeAvatarLoader.instance.emptyTexture = emptyTex

        val defaultFaceTex = loadTextur("Texture/face_diffuse.jpg")
        val DefaultBodyTex = loadTextur("Texture/body_diffuse.jpg")
        CustomizeAvatarLoader.instance.defaultFaceTexture = defaultFaceTex
        CustomizeAvatarLoader.instance.defaultBodyTexture  = DefaultBodyTex

        CustomizeAvatarLoader.instance.faceTexture = defaultFaceTex
        CustomizeAvatarLoader.instance.bodyTexture  = DefaultBodyTex

        val defaultEyebrowTex  = loadTextur("Texture/eyebrow.png")
        CustomizeAvatarLoader.instance.defaultEyebrowTexture = defaultEyebrowTex
        CustomizeAvatarLoader.instance.eyebrowTexture = defaultEyebrowTex

        val defaultLipTex = loadTextur("Texture/lips.png")
        CustomizeAvatarLoader.instance.defaultLipTexture = defaultLipTex
        CustomizeAvatarLoader.instance.lipsTexture = defaultLipTex

        val defaultEyeballTex = loadTextur("Texture/eyeball.png")
        CustomizeAvatarLoader.instance.defaultEyeballTexture = defaultEyeballTex
        CustomizeAvatarLoader.instance.eyeballTexture = defaultEyeballTex

        CustomizeAvatarLoader.instance.hairsTexture = emptyTex
        CustomizeAvatarLoader.instance.facialHairTexture = emptyTex

        val normalFaceMap =   loadTextur("Texture/face_normal.jpg")
        val roughnessFaceMap =   loadTextur("Texture/face_roughness.jpg")

        CustomizeAvatarLoader.instance.faceMaterial.setParameter("normalMap",normalFaceMap,sampler)
        CustomizeAvatarLoader.instance.faceMaterial.setParameter("roughness",roughnessFaceMap,sampler)

        val normalBodyMap =   loadTextur("Texture/body_normal.jpg")
        val roughnessBodyMap =   loadTextur("Texture/body_roughness.jpg")

        CustomizeAvatarLoader.instance.bodyMaterial.setParameter("normalMap",normalBodyMap,sampler)
        CustomizeAvatarLoader.instance.bodyMaterial.setParameter("roughness",roughnessBodyMap,sampler)

    runOnUiThread {

            CommonFunction.instance.downloadGlb("https://aystorage.b-cdn.net/standard/head_generic_rig_unity_v6.glb") {
                CustomizeAvatarLoader.instance.modelViewer.LoadHeadModel(it)

                CustomizeAvatarLoader.instance.resetToDefault {

                }
            }
    }
    }
    private  fun loadTextur(path:String):Texture{
        val drophs = assets.open(path)
        val bitmaps = BitmapFactory.decodeStream(drophs)
        val texture = Texture.Builder()
            .width(bitmaps.width)
            .height(bitmaps.height)
            .sampler(Texture.Sampler.SAMPLER_2D)
            .format(Texture.InternalFormat.SRGB8_A8)
            // This tells Filament to figure out the number of mip levels
            .levels(0xff)
            .build(CustomizeAvatarLoader.instance.modelViewer.engine)
        TextureHelper.setBitmap(CustomizeAvatarLoader.instance.modelViewer.engine, texture, 0, bitmaps)
        texture.generateMipmaps(CustomizeAvatarLoader.instance.modelViewer.engine)
        return  texture
    }
}