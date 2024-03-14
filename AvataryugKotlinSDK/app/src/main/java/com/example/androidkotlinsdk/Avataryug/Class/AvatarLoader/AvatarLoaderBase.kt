package Avataryug.Class.AvatarLoader

import Avataryug.Class.AvataryugData
import Avataryug.Class.DefaultAvatarData
import Avataryug.Class.ModelDecryptionHandler
import Avataryug.Client.Models.ModelData
import SkinToneArtifact
import TattooArtifact
import ThreeDArtifact
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.AsyncTask
import com.Avataryug.client.Models.GetEconomyItemsResultDataInner
import com.google.android.filament.Colors
import com.google.android.filament.Texture
import com.google.android.filament.TextureSampler
import com.google.android.filament.android.TextureHelper
import com.google.android.filament.utils.Float3
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.Buffer
import java.nio.ByteBuffer

open class AvatarLoaderBase {

     val loadDefaultModelList: MutableList<ModelData> = mutableListOf()
    private val lastLoadedTattoos: MutableList<LoadedTattoo> = ArrayList()

      var  currentTopData : EconomyItem = EconomyItem()
      var  currentBottomData : EconomyItem = EconomyItem()
      var  currentOutfitData : EconomyItem = EconomyItem()
      var  currentFootwearData : EconomyItem = EconomyItem()
      var  currentHandwearData : EconomyItem = EconomyItem()

      var  currentSkinToneData : EconomyItem = EconomyItem()

      var  currentWristwearData : EconomyItem = EconomyItem()

      var  currentEyeballData : EconomyItem = EconomyItem()
      var  currentEyebrowData : EconomyItem = EconomyItem()
      var  currentLipsData : EconomyItem = EconomyItem()
      var  currentHeadTattooData : EconomyItem = EconomyItem()
      var  currentHairData : EconomyItem = EconomyItem()
      var  currentFacialhairData : EconomyItem = EconomyItem()
    var  currentEyewearData : EconomyItem = EconomyItem()
    var  currentHeadwearData : EconomyItem = EconomyItem()
    fun getGender():Gender{
        return  Gender.Male//UserDetailHolder.instance.userDetail.gender
    }







    private fun getByteData(url: String, result: (ByteBuffer) -> Unit, error: (Exception) -> Unit) {
        val request = Request.Builder().url(url).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                error(e)
            }
            override  fun onResponse(call: Call, response: Response) {
                try {
                    val bytearray = response.body?.bytes()
                    if (bytearray != null) {
                        val decdata = ModelDecryptionHandler.instance.getGlbByte(bytearray)
                        val inputs = ByteArrayInputStream(decdata)
                        val inputStream = BufferedInputStream(inputs)
                        GlobalScope.launch {
                            ByteArrayOutputStream().use { output ->
                                inputStream.copyTo(output)
                                val byteArr = output.toByteArray()
                                val byteBuffer = ByteBuffer.wrap(byteArr)
                                val rewound = byteBuffer.rewind()
                                withContext(Dispatchers.Main) {
                                    result(rewound as ByteBuffer)
                                }
                            }
                        } }
                } catch (e: Exception) {
                    error(e)
                }
            }
        })
    }

    fun  getbytedta(url: String,result: (ByteBuffer) -> Unit){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val byteArray = downloadByteArray(url)
                val decdata = ModelDecryptionHandler.instance.getGlbByte(byteArray)
                val inputs = ByteArrayInputStream(decdata)
                val inputStream = BufferedInputStream(inputs)
                ByteArrayOutputStream().use { output ->
                    inputStream.copyTo(output)
                    val byteArr = output.toByteArray()
                    val byteBuffer = ByteBuffer.wrap(byteArr)
                    val rewound = byteBuffer.rewind()
                    withContext(Dispatchers.Main) {
                        result(rewound as ByteBuffer)
                    }
                }
                // Handle the downloaded byte array here (e.g., save to a file, process it, etc.)
                println("Downloaded ${byteArray.size} bytes")
            } catch (e: Exception) {
                // Handle exceptions (e.g., network errors)
                println("Error downloading file: ${e.message}")
            }
        }
    }

    suspend fun downloadByteArray(url: String): ByteArray = withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection() as HttpURLConnection
        try {
            connection.connect()
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("Server returned HTTP ${connection.responseCode} ${connection.responseMessage}")
            }
            connection.inputStream.readBytes()
        } finally {
            connection.disconnect()
        }
    }


    private val loadNetworkModellist: MutableList<EconomyItem> = mutableListOf()

    open fun loadNetworkModel(modelData:EconomyItem)
    {
        loadNetworkModellist.add(modelData)
        onQueueNetworkModel()
    }

    open fun changePropColor(category:String,color:String){
//        if(category == "Eyebrow")
//        {
//            CurrentAvatarChanges.Instance.changedPropColors.EyebrowColor = color
//            val eyebrowColorRgb = hexToColor(CurrentAvatarChanges.Instance.changedPropColors.EyebrowColor)
//            CustomizeAvatarLoader.instance.faceMaterial.setParameter("eyebrowColor", Colors.RgbaType.SRGB,eyebrowColorRgb.r,eyebrowColorRgb.g,eyebrowColorRgb.b,1.0f)
//        }
//        if(category == "Hair")
//        {
//            CurrentAvatarChanges.Instance.changedPropColors.HairColor = color
//            val hairColorRgb = hexToColor(CurrentAvatarChanges.Instance.changedPropColors.HairColor)
//            CustomizeAvatarLoader.instance.faceMaterial.setParameter("hairColor", Colors.RgbaType.SRGB,hairColorRgb.r,hairColorRgb.g,hairColorRgb.b,1.0f)
//        }
//        if(category == "Lips")
//        {
//            CurrentAvatarChanges.Instance.changedPropColors.LipColor = color
//            val lipsColorRgb = hexToColor(CurrentAvatarChanges.Instance.changedPropColors.LipColor)
//            CustomizeAvatarLoader.instance.faceMaterial.setParameter("lipsColor", Colors.RgbaType.SRGB,lipsColorRgb.r,lipsColorRgb.g,lipsColorRgb.b,1.0f)
//        }
//        if(category == "Facialhair")
//        {
//            CurrentAvatarChanges.Instance.changedPropColors.FacialHairColor = color
//            val facialhairColorRgb = hexToColor(CurrentAvatarChanges.Instance.changedPropColors.FacialHairColor)
//            CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairColor",Colors.RgbaType.SRGB,facialhairColorRgb.r,facialhairColorRgb.g,facialhairColorRgb.b,1.0f)
//        }
    }

    private fun onQueueNetworkModel()
    {
        if(loadNetworkModellist.isEmpty())
        {
            //ApiEvents.onHideLoading?.invoke()
        }
        else
        {
            //ApiEvents.onShowLoading?.invoke()
            val modelData = loadNetworkModellist[0]
            loadNetworkModellist.removeAt(0)
            downloadNetworkModel(modelData)
        }
    }

    private fun downloadNetworkModel(modelData:EconomyItem)
    {
        if( modelData.ItemCategory == "SkinTone" )
        {
            loadSkinTone(modelData)
        }
        else if( modelData.ItemCategory == "Wristwear" )
        {
            loadBodyWears(modelData)
        }
        else if(AvataryugData.instance.isTattooCategory(modelData.ItemCategory))
        {
            loadTattoo(modelData)
        }
        else if(modelData.ItemCategory == "Top")
        {
            loadBodyModel(modelData)
        }
        else if(modelData.ItemCategory == "Bottom")
        {
            loadBodyModel(modelData)
        }
        else if(modelData.ItemCategory == "Outfit")
        {
            loadBodyModel(modelData)
        }
        else if(modelData.ItemCategory == "Footwear")
        {
            loadBodyModel(modelData)
        }
        else if( modelData.ItemCategory == "Handwear")
        {
            loadBodyModel(modelData)
        }
        else if( modelData.ItemCategory == "Eyeball" )
        {
            loadEyeball(modelData)
        }
        else if(modelData.ItemCategory == "Lips")
        {
            loadLips(modelData)
        }
        else if(modelData.ItemCategory == "Eyebrow" )
        {
            loadEyebrow(modelData)
        }
        else if(modelData.ItemCategory == "HeadTattoo" )
        {
            loadHeadTattoo(modelData)
        }
        else if(modelData.ItemCategory == "Hair" )
        {
            loadHairs(modelData)
        }
        else if(modelData.ItemCategory == "Facialhair" )
        {
            loadFacialhair(modelData)
        }
        else if(modelData.ItemCategory =="Eyewear")
        {
            loadEyewear(modelData)
        }
        else if(modelData.ItemCategory =="Headwear")
        {
            loadHeadwear(modelData)
        }
        else if(modelData.ItemCategory =="Mouthwear")
        {
            loadNeckwear(modelData)
        }
        else if(modelData.ItemCategory =="Earwear")
        {
            loadNeckwear(modelData)
        }
        else if(modelData.ItemCategory =="Eyebrowswear")
        {
            loadNeckwear(modelData)
        }
        else if(modelData.ItemCategory =="Facewear")
        {
            loadNeckwear(modelData)
        }
        else if(modelData.ItemCategory =="Neckwear")
        {
            loadNeckwear(modelData)
        }
        else if(modelData.ItemCategory == "Nosewear" )
        {
            loadNeckwear(modelData)
        }
        else
        {
            onQueueNetworkModel()
        }
    }
    private fun loadNeckwear(modelData:EconomyItem)
    {
        val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
        var artifactUrl = ""
        for (artifact in artifactList) {
            if(artifact.device == 0){
                artifact.url?.let {
                    artifactUrl = it
                }
            }
        }

//        if(DataHolder.Instance.isPresentInCurrentPart(modelData))
//        {
//            DataHolder.Instance.removePart(modelData)
//            CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(null,modelData,false)
//            onQueueNetworkModel()
//        }
//        else
//        {
//            val tempList : MutableList<String> = mutableListOf()
//            tempList.add(getBucketName(modelData.CoreBucket))
//            if(modelData.ConflictingBuckets.isNotEmpty()) {
//                val conflictNames: MutableList<ConflictName> = modelData.ConflictingBuckets
//                conflictNames.forEach {
//                    it.let {
//                        it.name.let {name->
//                            tempList.add(name)
//                        }
//                    }
//                }
//            }
//            println("=====        " +Gson().toJson(tempList))
//            tempList.forEach {
//                if(it.isNotEmpty()){
//                    println("=====        >> " + it)
//                    DataHolder.Instance.removePartWithBucketName(it)
//                }
//            }
//
//            DataHolder.Instance.addCurrentBodyPartWithId(modelData)
//            getByteData(artifactUrl, result = {
//                CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(it,modelData,true)
//                onQueueNetworkModel()
//            }, error = { })
//        }
    }

    private fun loadHeadwear(modelData:EconomyItem)
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val loadNew: Boolean
        if(currentHeadwearData.ID == modelData.ID)
        {
            loadNew = false
            currentHeadwearData = EconomyItem()
            //DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentHeadwearData = modelData
           // DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {
            val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
            var artifactUrl = ""
            for (artifact in artifactList) {
                if(artifact.device == 0){
                    artifact.url?.let {
                        artifactUrl = it
                    }
                }
            }
            getByteData(artifactUrl, result = {
                CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(it,modelData,true)
                onQueueNetworkModel()
            }, error = {

            })
        }
        else
        {
            CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(null,modelData,false)
            onQueueNetworkModel()
        }
    }

    private fun loadEyewear(modelData:EconomyItem)
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val loadNew: Boolean
        if(currentEyewearData.ID == modelData.ID)
        {
            loadNew = false
            currentEyewearData = EconomyItem()
            //DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentEyewearData = modelData
            //DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {
            val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
            var artifactUrl = ""
            for (artifact in artifactList) {
                if(artifact.device == 0){
                    artifact.url?.let {
                        artifactUrl = it
                    }
                }
            }
            getByteData(artifactUrl, result = {
                    CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(it,modelData,true)
                    onQueueNetworkModel()
            }, error = {

            })
        }
        else
        {
            CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(null,modelData,false)
            onQueueNetworkModel()
        }
    }
    private fun loadFacialhair(modelData:EconomyItem)
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val loadNew: Boolean
        if(currentFacialhairData.ID == modelData.ID)
        {
            loadNew = false
            currentFacialhairData = EconomyItem()
          //  DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentFacialhairData = modelData
           // DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {

            val conig: Config = modelData.Config
            if(conig.isTwoD == 1)
            {
                val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)

                var haittex = ""
                for (artifact in artifactList)
                {
                    if(artifact.device == 0)
                    {
                        artifact.url?.let { haittex = it }
                    }
                }

                ImageLoadTask(error = {
                    onQueueNetworkModel()
                }, results = {result->
                    CustomizeAvatarLoader.instance.facialHairTexture = bitmapToTexture(result)
                    val color : Float3 = hexToColor(AvataryugData.instance.defaultFacialHairColor)
                    CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairColor", Colors.RgbaType.SRGB,color.x,color.y,color.z,1.0f)
                    CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairTexture",bitmapToTexture(result),sampler)
                    CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(null,modelData,false)
                    onQueueNetworkModel()
                }).execute(haittex)
            }
            else
            {
                val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
                var artifactUrl = ""
                for (artifact in artifactList) {
                    if(artifact.device == 0){
                        artifact.url?.let {
                            artifactUrl = it
                        }
                    }
                }
                CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairTexture", CustomizeAvatarLoader.instance.emptyTexture, sampler)
                getByteData(artifactUrl, result = {
                    CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(it,modelData,true)
                    onQueueNetworkModel()
                }, error = {

                })
            }
        }
        else
        {
            CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairTexture", CustomizeAvatarLoader.instance.emptyTexture, sampler)
            CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(null,modelData,false)
            onQueueNetworkModel()
        }
    }
    private fun hexToColor(colorString:String):Float3{
        var color : Float3 = Float3(0.0f,0.0f,0.0f)
        val red = Integer.parseInt(colorString.substring(1, 3), 16)
        val green = Integer.parseInt(colorString.substring(3, 5), 16)
        val blue = Integer.parseInt(colorString.substring(5, 7), 16)
        color = Float3(red.toFloat()/255f,green.toFloat()/255f,blue.toFloat()/255f)
        return color
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun loadHairs(modelData:EconomyItem)
    {           val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        var loadNew = false
        if(currentHairData.ID == modelData.ID)
        {
            loadNew = false
            currentHairData = EconomyItem()
           // DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentHairData = modelData
          //  DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {

            val conig: Config = modelData.Config

            if(conig.isTwoD == 1)
            {
                val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)

                var haittex = ""
                for (artifact in artifactList)
                {
                    if(artifact.device == 0)
                    {
                        artifact.url?.let { haittex = it }
                    }
                }
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val bitmap = downloadBitmap(haittex)
                        launch(Dispatchers.Main) {
                            CustomizeAvatarLoader.instance.hairsTexture = bitmapToTexture(bitmap)
                            CustomizeAvatarLoader.instance.faceMaterial.setParameter(
                                "hairTexture",
                                bitmapToTexture(bitmap),
                                sampler
                            )
                            CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(
                                null,
                                modelData,
                                false
                            )
                            onQueueNetworkModel()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }
            else
            {
                val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
                var artifactUrl = ""
                for (artifact in artifactList) {
                    if(artifact.device == 0){
                        artifact.url?.let {
                            artifactUrl = it
                        }
                    }
                }
                CustomizeAvatarLoader.instance.faceMaterial.setParameter("hairTexture", CustomizeAvatarLoader.instance.emptyTexture, sampler)
                getByteData(artifactUrl, result = {
                    CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(it,modelData,true)
                    onQueueNetworkModel()
                }, error = {

                })
            }


        }
        else
        {
            CustomizeAvatarLoader.instance.faceMaterial.setParameter("hairTexture", CustomizeAvatarLoader.instance.emptyTexture, sampler)
            CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(null,modelData,false)
            onQueueNetworkModel()
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun loadHeadTattoo(modelData:EconomyItem)
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val loadNew: Boolean
        if(currentHeadTattooData.ID == modelData.ID)
        {
            loadNew = false
            currentHeadTattooData = EconomyItem()
            //DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentHeadTattooData = modelData
           // DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {
            val artifactList = Gson().fromJson(modelData.Artifacts, Array<TattooArtifact>::class.java)
            var tattooPath = ""
            for (artifact in artifactList)
            {
                if(artifact.device.toInt() == 0)
                {
                    artifact.url.let { tattooPath = it }
                }
            }
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val bitmap = downloadBitmap(tattooPath)
                    launch(Dispatchers.Main) {
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("tattooTexture", bitmapToTexture(bitmap), sampler)
                        onQueueNetworkModel()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        else
        {
            val emptyTexture = CustomizeAvatarLoader.instance.emptyTexture
            CustomizeAvatarLoader.instance.faceMaterial.setParameter("tattooTexture", emptyTexture, sampler)
            onQueueNetworkModel()
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun loadLips(modelData:EconomyItem)
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val loadNew: Boolean
        if(currentLipsData.ID == modelData.ID)
        {
            loadNew = false
            currentLipsData = EconomyItem()
            //DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentLipsData = modelData
            //DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {

            val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
            var eyebrowTex = ""
            for (artifact in artifactList)
            {
                if(artifact.device == 0)
                {
                    artifact.url?.let { eyebrowTex = it }
                }
            }

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val bitmap = downloadBitmap(eyebrowTex)
                    launch(Dispatchers.Main) {
                        CustomizeAvatarLoader.instance.lipsTexture = bitmapToTexture(bitmap)
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("lipsTexture",bitmapToTexture(bitmap),sampler)
                        onQueueNetworkModel()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
        else
        {
            var defaultLipTexture = CustomizeAvatarLoader.instance.defaultLipTexture
            CustomizeAvatarLoader.instance.lipsTexture = defaultLipTexture
            CustomizeAvatarLoader.instance.faceMaterial.setParameter("lipsTexture", defaultLipTexture,sampler)
            onQueueNetworkModel()
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun loadEyebrow(modelData:EconomyItem)
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val loadNew: Boolean
        if(currentEyebrowData.ID == modelData.ID)
        {
            loadNew = false
            currentEyebrowData = EconomyItem()
            //DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentEyebrowData = modelData
           // DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {
            val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
            var eyebrowTex = ""
            for (artifact in artifactList)
            {
                if(artifact.device == 0)
                {
                    artifact.url?.let { eyebrowTex = it }
                }
            }
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val bitmap = downloadBitmap(eyebrowTex)
                    launch(Dispatchers.Main) {
                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("eyebrowTexture",bitmapToTexture(bitmap),sampler)
                        onQueueNetworkModel()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
//        ImageLoadTask(error = {
//            onQueueNetworkModel()
//        },{
//            CustomizeAvatarLoader.instance.faceMaterial.setParameter("eyebrowTexture",bitmapToTexture(it),sampler)
//            onQueueNetworkModel()
//        }).execute(eyebrowTex)
        }
        else
        {

                        CustomizeAvatarLoader.instance.faceMaterial.setParameter("eyebrowTexture",CustomizeAvatarLoader.instance.defaultEyebrowTexture,sampler)
                        onQueueNetworkModel()

        }
    }
    private fun loadEyeball(modelData:EconomyItem)
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val loadNew: Boolean
        if(currentEyeballData.ID == modelData.ID)
        {
            loadNew = false
            currentEyeballData = EconomyItem()
            //DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentEyeballData = modelData
            //DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {
           val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
            var eyeballPath = ""
            for (artifact in artifactList)
            {
                if(artifact.device == 0)
                {
                    artifact.url?.let { eyeballPath = it }
                }
            }

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val bitmap = downloadBitmap(eyeballPath)
                    launch(Dispatchers.Main) {
                        CustomizeAvatarLoader.instance.eyeballTexture = bitmapToTexture(bitmap)
                        CustomizeAvatarLoader.instance.eyeballMaterial.setParameter("EyeballTex",bitmapToTexture(bitmap),sampler)
                        onQueueNetworkModel()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        else
        {
            var defaultEyeballTexture = CustomizeAvatarLoader.instance.defaultEyeballTexture
            CustomizeAvatarLoader.instance.eyeballTexture = defaultEyeballTexture
            CustomizeAvatarLoader.instance.eyeballMaterial.setParameter("EyeballTex", defaultEyeballTexture,sampler)
            onQueueNetworkModel()
        }
    }
    private fun loadBodyWears(modelData:EconomyItem)
    {
        var loadNew = false
        if( modelData.ItemCategory == "Wristwear")
        {
            if(currentWristwearData.ID == modelData.ID)
            {
                loadNew = false
                currentWristwearData = EconomyItem()
                //DataHolder.Instance.removePart(modelData)
            }
            else
            {
                loadNew = true
                currentWristwearData = modelData
                //DataHolder.Instance.addCurrentBodyPart(modelData)
            }
        }
        if(loadNew)
        {
            val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
            var artifactUrl = ""
            for (artifact in artifactList) {
                if(artifact.device == 0){
                    artifact.url?.let {
                        artifactUrl = it
                    }
                }
            }
            getByteData(artifactUrl, result = {
                CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(it,modelData,true)
                onQueueNetworkModel()
            }, error = {

            })
        }
        else
        {
            CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(null,modelData,false)
            onQueueNetworkModel()
        }
    }


    private  fun bitmapToTexture(bitmaps:Bitmap): Texture {
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
    private fun clearModelData():GetEconomyItemsResultDataInner{
       return GetEconomyItemsResultDataInner("",0,"","",""
       ,"","",0,"","",0,0,0,
          "","","","","","",0,"",
           "","","")
    }

    private fun loadBodyModel(modelData:EconomyItem)
    {
        var loadNew = false
        if( modelData.ItemCategory == "Top")
        {
            if(currentTopData.ID == modelData.ID)
            {
                loadNew = false
                currentTopData = EconomyItem()
                //DataHolder.Instance.removePart(modelData)
            }
            else
            {
                loadNew = true
                currentTopData = modelData
                //DataHolder.Instance.addCurrentBodyPart(modelData)
               // DataHolder.Instance.removePart("Outfit")
            }
        }
        if( modelData.ItemCategory == "Bottom")
        {
            if(currentBottomData.ID == modelData.ID)
            {
                loadNew = false
                currentBottomData = EconomyItem()
                //DataHolder.Instance.removePart(modelData)
            }
            else
            {
                loadNew = true
                currentBottomData = modelData
                //DataHolder.Instance.addCurrentBodyPart(modelData)
               // DataHolder.Instance.removePart("Outfit")
            }
        }
        if( modelData.ItemCategory == "Outfit")
        {
            if(currentOutfitData.ID == modelData.ID)
            {
                loadNew = false
                currentOutfitData = EconomyItem()
               // DataHolder.Instance.removePart(modelData)
            }
            else
            {
                loadNew = true
                currentOutfitData = modelData
               // DataHolder.Instance.addCurrentBodyPart(modelData)
               // DataHolder.Instance.removePart("Top")
               // DataHolder.Instance.removePart("Bottom")
//                if (modelData.ConflictingBuckets.first() Find(f => f.name == "lowerbody_foot_both") != null)
//                {
//                    AvatarHandler.Instance.RemovePart("Footwear");
//                }
            }
        }
        if( modelData.ItemCategory == "Footwear")
        {
            if(currentFootwearData.ID == modelData.ID)
            {
                loadNew = false
                currentFootwearData = EconomyItem()
                //DataHolder.Instance.removePart(modelData)
            }
            else
            {
                loadNew = true
                currentFootwearData = modelData
                //DataHolder.Instance.addCurrentBodyPart(modelData)
            }
        }
        if( modelData.ItemCategory == "Handwear")
        {
            if(currentHandwearData.ID == modelData.ID)
            {
                loadNew = false
                currentHandwearData = EconomyItem()
                //DataHolder.Instance.removePart(modelData)
            }
            else
            {
                loadNew = true
                currentHandwearData = modelData
                //DataHolder.Instance.addCurrentBodyPart(modelData)
            }
        }
        if(loadNew)
        {
            val artifactList = Gson().fromJson(modelData.Artifacts, Array<ThreeDArtifact>::class.java)
            var artifactUrl = ""
            for (artifact in artifactList) {
                if(artifact.device == 0){
                    artifact.url?.let {
                        artifactUrl = it
                    }
                }
            }
            //ApiEvents.onShowLoading?.invoke()

            DownloadCompressGlbBuffer{
                CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(it,modelData,true)
               // ApiEvents.onHideLoading?.invoke()
                onQueueNetworkModel()
            }.execute(artifactUrl)
//            getByteData(artifactUrl, result = {
//                CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(it,modelData,true)
//                ApiEvents.onHideLoading?.invoke()
//            }, error = {
//
//            })
        }
        else
        {
            CustomizeAvatarLoader.instance.modelViewer.loadNetworkModel(null,modelData,false)
            onQueueNetworkModel()
        }
    }


    private class ImageLoadTask(var error: (IOException) -> Unit,var results: (Bitmap) -> Unit) :
        AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageUrl = urls[0]
            var bitmap: Bitmap? = null

            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                error(e)
                e.printStackTrace()
            }

            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let { results(it) }
        }
    }


//    open fun downloadBitmap(url: String,result: (Bitmap) -> Unit)
//    {
//        val request = Request.Builder().url(url).build()
//        OkHttpClient().newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                error(e)
//            }
//            override  fun onResponse(call: Call, response: Response) {
//                try {
//                    val byteArray : InputStream? = response.body?.byteStream()
//                    val bitmap: Bitmap? = if (byteArray != null) {
//                        BitmapFactory.decodeStream(byteArray)
//                    } else {
//                        null
//                    }
//                    if (bitmap != null) {
//                        result(bitmap)
//                    }
//                } catch (e: Exception) {
//                    error(e)
//                }
//            }
//        })
//    }
private class DownloadGlbBuffer(var results: (Buffer) -> Unit) :
    AsyncTask<String, Void, InputStream?>() {

    override fun doInBackground(vararg urls: String): InputStream? {
        val imageUrl = urls[0]
        var bitmap: InputStream? = null

        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            bitmap = BufferedInputStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bitmap
    }

    override fun onPostExecute(result: InputStream?) {
        GlobalScope.launch {
            ByteArrayOutputStream().use { output ->
                result?.copyTo(output)
                val byteArr = output.toByteArray()
                val byteBuffer = ByteBuffer.wrap(byteArr)
                val rewound = byteBuffer.rewind()
                withContext(Dispatchers.Main) {
                    results(rewound)
                }
            }
        }
    }
}

    private fun loadSkinTone(modelData:EconomyItem)
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        val loadNew: Boolean
        if(currentSkinToneData.ID == modelData.ID)
        {
            loadNew = false
            currentSkinToneData = EconomyItem()
            //DataHolder.Instance.removePart(modelData)
        }
        else
        {
            loadNew = true
            currentSkinToneData = modelData
            //DataHolder.Instance.addCurrentBodyPart(modelData)
        }
        if(loadNew)
        {
            val artifactList = Gson().fromJson(modelData.Artifacts, Array<SkinToneArtifact>::class.java)
            var bodyPath = ""
            var facePath = ""
            for (artifact in artifactList)
            {
                if(artifact.device?.toInt() == 0)
                {
                    artifact.body_path?.let { bodyPath = it }
                    artifact.face_path?.let { facePath = it }
                }
            }
            //ApiEvents.onShowLoading?.invoke()
            ImageLoadTask(error = {
                onQueueNetworkModel()
            }, results =  {body->
                ImageLoadTask(error = {
                    onQueueNetworkModel()
                }, results =  { face->
                    //ApiEvents.onHideLoading?.invoke()
                    CustomizeAvatarLoader.instance.bodyTexture = bitmapToTexture(body)
                    CustomizeAvatarLoader.instance.faceTexture = bitmapToTexture(face)
                    CustomizeAvatarLoader.instance.bodyMaterial.setParameter("baseTexture",bitmapToTexture(body),sampler)
                    CustomizeAvatarLoader.instance.faceMaterial.setParameter("baseTexture",bitmapToTexture(face),sampler)
                    onQueueNetworkModel()
                }).execute(facePath)
            }).execute(bodyPath)
        }
        else
        {
            val faceTex = CustomizeAvatarLoader.instance.defaultFaceTexture
            val bodyTex = CustomizeAvatarLoader.instance.defaultBodyTexture
            CustomizeAvatarLoader.instance.bodyTexture = bodyTex
            CustomizeAvatarLoader.instance.faceTexture = faceTex
            CustomizeAvatarLoader.instance.faceMaterial.setParameter("baseTexture",faceTex,sampler)
            CustomizeAvatarLoader.instance.bodyMaterial.setParameter("baseTexture",bodyTex,sampler)
            onQueueNetworkModel()
        }
    }

    private fun loadTattoo(modelData:EconomyItem) {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        var isPresentSameCat = false
        var isPresentLatestId = false
        var tattooSameIDIndex = -1
        var tattooSameCatIndex = -1
        if (lastLoadedTattoos.size > 0) {
            lastLoadedTattoos.forEachIndexed { ind, loadedTattoo ->
                if(loadedTattoo.ItemCategory ==  modelData.ItemCategory){
                    isPresentSameCat = true
                    tattooSameCatIndex = ind
                    if (loadedTattoo.Tattooid == modelData.ID) {
                        isPresentLatestId = true
                        tattooSameIDIndex = ind
                    }
                }
            }
        }
        var loadNew = false
        if (isPresentSameCat)
        {
            if (isPresentLatestId)
            {
                //DataHolder.Instance.removePart(modelData)
                lastLoadedTattoos.removeAt (tattooSameIDIndex)
                onProcessModelTexture()
            }
            else
            {
                //DataHolder.Instance.removePart(modelData)
                lastLoadedTattoos.removeAt (tattooSameCatIndex)
                loadNew = true
            }
        }
        else
        {
            loadNew = true
        }
        if(loadNew)
        {
            //DataHolder.Instance.addCurrentBodyPart(modelData)
            val artifactList = Gson().fromJson(modelData.Artifacts, Array<TattooArtifact>::class.java)
            var tattooPath = ""
            for (artifact in artifactList)
            {
                if(artifact.device.toInt() == 0)
                {
                    artifact.url.let { tattooPath = it }
                }
            }
            ImageLoadTask( error = {
                onQueueNetworkModel()
            }, results = {   result ->
                lastLoadedTattoos.add(LoadedTattoo(modelData.ItemCategory,modelData.ID,result))
                onProcessModelTexture()
                onQueueNetworkModel()
            }).execute(tattooPath)
//            downloadBitmap(tattooPath, result = { result ->
//
//                lastLoadedTattoos.add(LoadedTattoo(modelData.ItemCategory,modelData.ID,result))
//                onProcessModelTexture()
//                onQueueNetworkModel()
//            })
        }else
        {
            onQueueNetworkModel()
        }
    }

    private fun onProcessModelTexture()
    {
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f
        if(lastLoadedTattoos.size >0)
        {
            val tempList : MutableList<Bitmap> = ArrayList()
            for (tattoo in lastLoadedTattoos)
            {
                tempList.add(tattoo.TattooTex)
            }
            val finalTex = bitmapOverlayToCenter(tempList[0],tempList)
            finalTex?.let {
                CustomizeAvatarLoader.instance.bodyMaterial.setParameter("tattooTexture",bitmapToTexture(it),sampler)
            }
        }
        else
        {
            CustomizeAvatarLoader.instance.bodyMaterial.setParameter("tattooTexture", CustomizeAvatarLoader.instance.emptyTexture,sampler)
        }
    }

    open fun bitmapOverlayToCenter(bitmap1: Bitmap, overlayBitmap: List<Bitmap?>): Bitmap? {
        val bitmap1Width = bitmap1.width
        val bitmap1Height = bitmap1.height
        val finalBitmap = Bitmap.createBitmap(bitmap1Width, bitmap1Height, bitmap1.config)
        val canvas = Canvas(finalBitmap)
        canvas.drawBitmap(bitmap1, 0f, 0f, null)
        if(overlayBitmap.isNotEmpty())
        {
            for (i in 1 until overlayBitmap.size)
            {
                canvas.drawBitmap(overlayBitmap[i]!!, 0f, 0f, null)
            }
        }
        return finalBitmap
    }

    open fun  resetToDefault(onComplete: () -> Unit){
       // ApiEvents.onShowLoading?.invoke()
        val sampler = TextureSampler()
        sampler.anisotropy = 8.0f


     //   CurrentAvatarChanges.Instance.changedProps.clear()
        //CurrentAvatarChanges.Instance.currentProps.clear()
//        CurrentAvatarChanges.Instance.changedBlendshapes.clear()
//        CurrentAvatarChanges.Instance.currentBlendshapes.clear()
//        CurrentAvatarChanges.Instance.changedPropColors =  PropColors("","","","","")
//        CurrentAvatarChanges.Instance.currentPropColors =  PropColors("","","","","")



        val haircolor = AvataryugData.instance.defaultHairColor
        val eyebrocolor = AvataryugData.instance.defaultEyebrowColor
        val facialHaircolor = AvataryugData.instance.defaultFacialHairColor
        val lipColor = AvataryugData.instance.defaultLipColor

//        CurrentAvatarChanges.Instance.changedPropColors.HairColor = haircolor
//        CurrentAvatarChanges.Instance.currentPropColors.HairColor = haircolor
//
//        CurrentAvatarChanges.Instance.changedPropColors.EyebrowColor = eyebrocolor
//        CurrentAvatarChanges.Instance.currentPropColors.EyebrowColor = eyebrocolor
//
//        CurrentAvatarChanges.Instance.changedPropColors.FacialHairColor = facialHaircolor
//        CurrentAvatarChanges.Instance.currentPropColors.FacialHairColor = facialHaircolor
//
//        CurrentAvatarChanges.Instance.changedPropColors.LipColor = lipColor
//        CurrentAvatarChanges.Instance.currentPropColors.LipColor = lipColor
//
//        CurrentAvatarChanges.Instance.changedPropColors.FaceColor = "#FFFFFF"
//        CurrentAvatarChanges.Instance.currentPropColors.FaceColor = "#FFFFFF"

        val defaultBodyTexture = CustomizeAvatarLoader.instance.defaultBodyTexture
        val defaultFaceTexture = CustomizeAvatarLoader.instance.defaultFaceTexture
        val defaultEyebrowTexture = CustomizeAvatarLoader.instance.defaultEyebrowTexture
        val defaultLipTexture = CustomizeAvatarLoader.instance.defaultLipTexture
        val defaultEyeballTexture = CustomizeAvatarLoader.instance.defaultEyeballTexture
        val emptyTexture = CustomizeAvatarLoader.instance.emptyTexture

        CustomizeAvatarLoader.instance.eyeballTexture = defaultEyeballTexture
        CustomizeAvatarLoader.instance.hairsTexture = emptyTexture
        CustomizeAvatarLoader.instance.facialHairTexture = emptyTexture
        CustomizeAvatarLoader.instance.bodyTexture = defaultBodyTexture
        CustomizeAvatarLoader.instance.faceTexture = defaultFaceTexture
        CustomizeAvatarLoader.instance.eyebrowTexture = defaultEyebrowTexture
        CustomizeAvatarLoader.instance.lipsTexture = defaultLipTexture

        CustomizeAvatarLoader.instance.bodyMaterial.setParameter("baseTexture",defaultBodyTexture,sampler)
        CustomizeAvatarLoader.instance.bodyMaterial.setParameter("tattooTexture",emptyTexture,sampler)

        CustomizeAvatarLoader.instance.eyeballMaterial.setParameter("EyeballTex",defaultEyeballTexture,sampler)

        CustomizeAvatarLoader.instance.faceMaterial.setParameter("baseTexture",defaultFaceTexture,sampler)
        val hairColorRgb = hexToColor(haircolor)
        val eyebrowColorRgb = hexToColor(eyebrocolor)
        val facialhairColorRgb = hexToColor(facialHaircolor)
        val lipsColorRgb = hexToColor(lipColor)

        CustomizeAvatarLoader.instance.faceMaterial.setParameter("hairColor", Colors.RgbaType.SRGB,hairColorRgb.r,hairColorRgb.g,hairColorRgb.b,1.0f)
        CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairColor",Colors.RgbaType.SRGB,facialhairColorRgb.r,facialhairColorRgb.g,facialhairColorRgb.b,1.0f)
        CustomizeAvatarLoader.instance.faceMaterial.setParameter("eyebrowColor", Colors.RgbaType.SRGB,eyebrowColorRgb.r,eyebrowColorRgb.g,eyebrowColorRgb.b,1.0f)
        CustomizeAvatarLoader.instance.faceMaterial.setParameter("lipsColor", Colors.RgbaType.SRGB,lipsColorRgb.r,lipsColorRgb.g,lipsColorRgb.b,1.0f)

        CustomizeAvatarLoader.instance.faceMaterial.setParameter("eyebrowTexture",defaultEyebrowTexture,sampler)
        CustomizeAvatarLoader.instance.faceMaterial.setParameter("lipsTexture",defaultLipTexture,sampler)
        CustomizeAvatarLoader.instance.faceMaterial.setParameter("tattooTexture",emptyTexture, sampler)
        CustomizeAvatarLoader.instance.faceMaterial.setParameter("hairTexture",emptyTexture,sampler)
        CustomizeAvatarLoader.instance.faceMaterial.setParameter("facialHairTexture",emptyTexture,sampler)

        CustomizeAvatarLoader.instance.currentTopData = EconomyItem()
        CustomizeAvatarLoader.instance.currentBottomData = EconomyItem()
        CustomizeAvatarLoader.instance.currentOutfitData = EconomyItem()
        CustomizeAvatarLoader.instance.currentFootwearData = EconomyItem()
        CustomizeAvatarLoader.instance.currentHandwearData = EconomyItem()
        CustomizeAvatarLoader.instance.currentSkinToneData = EconomyItem()
        CustomizeAvatarLoader.instance.currentWristwearData = EconomyItem()
        CustomizeAvatarLoader.instance.currentEyeballData = EconomyItem()
        CustomizeAvatarLoader.instance.currentEyebrowData = EconomyItem()
        CustomizeAvatarLoader.instance.currentLipsData = EconomyItem()
        CustomizeAvatarLoader.instance.currentHeadTattooData = EconomyItem()
        CustomizeAvatarLoader.instance.currentHairData = EconomyItem()
        CustomizeAvatarLoader.instance.currentFacialhairData = EconomyItem()
        CustomizeAvatarLoader.instance.currentEyewearData = EconomyItem()
        CustomizeAvatarLoader.instance.currentHeadwearData = EconomyItem()
        //DataHolder.Instance.currentSelectedBodyParts.clear()
        CustomizeAvatarLoader.instance.modelViewer.RemoveAllModel()
        loadDefaultModel(onComplete)
        //ApiEvents.onItemSelect?.invoke(null)
    }

    open fun resetToCurrent(){

//        resetToDefault{
//
//                ApiEvents.onItemSelect?.invoke(null)
//
//                val avatarData = UserDetailHolder.instance.userDetail.currentUserSelectedAvatar.AvatarData
//                val hairColor = avatarData.ColorMeta.HairColor
//                val facialHairColor = avatarData.ColorMeta.FacialHairColor
//                val eyebrowColor = avatarData.ColorMeta.EyebrowColor
//                val lipColor = avatarData.ColorMeta.LipColor
//                val faceColor = avatarData.ColorMeta.FaceColor
//                CurrentAvatarChanges.Instance.currentPropColors = PropColors(hairColor,eyebrowColor,facialHairColor,lipColor,faceColor)
//                CurrentAvatarChanges.Instance.changedPropColors = PropColors(hairColor,eyebrowColor,facialHairColor,lipColor,faceColor)
//                val bucketData: MutableList<Prop> = mutableListOf()
//
//
//                avatarData.BucketData.forEach {
//                    val tempItem = Prop()
//                    tempItem.ID = it.ID
//                    tempItem.CoreBucket = it.CoreBucket
//                    bucketData.add(tempItem)
//                }
//                CurrentAvatarChanges.Instance.changedProps = bucketData.toMutableList()
//                CurrentAvatarChanges.Instance.currentProps = bucketData.toMutableList()
//
//            CurrentAvatarChanges.Instance.changedProps.forEach {
//                if(EconomyItemHolder.instance.IsEconomyItemPresentWithID(it.ID)){
//                    val item = EconomyItemHolder.instance.GetEconomyItem(it.ID)
//                    loadNetworkModellist.add(item)
//                }
//            }
//                onQueueNetworkModel()
//            }

    }

    open fun loadDefaultModel(onComplete: () -> Unit )
    {
        if(getGender() == Gender.Male)
        {
            for( item in DefaultAvatarData.instance.modelMaleData)
            {
                loadDefaultModelList.add(item)
            }
        }
        if(getGender() == Gender.Female)
        {
            for( item in DefaultAvatarData.instance.modelFemaleData)
            {
                loadDefaultModelList.add(item)
            }
        }
        onLoadQueDefaultModel(onComplete)
    }

    open fun onLoadQueDefaultModel(onComplete: () -> Unit)
    {
        if(loadDefaultModelList.isEmpty())
        {
            //ApiEvents.onHideLoading?.invoke()
            onComplete()
        }
        else
        {
            //ApiEvents.onShowLoading?.invoke()
            val modelData = loadDefaultModelList[0]
            loadDefaultModelList.removeAt(0)
            DownloadGlbBuffer{
                CustomizeAvatarLoader.instance.modelViewer.LoadDefaultGlb(it,modelData.MainCatID,modelData.genderType)
              onLoadQueDefaultModel(onComplete)
            }.execute(modelData.GlbPath)
        }
    }
    private class DownloadCompressGlbBuffer(var results: (Buffer) -> Unit) :
        AsyncTask<String, Void, InputStream?>() {

        override fun doInBackground(vararg urls: String): InputStream? {
            val imageUrl = urls[0]
            var bitmap: InputStream? = null

            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                val decData = ModelDecryptionHandler.instance.getGlbByte(input.readBytes())
                val inputs = ByteArrayInputStream(decData)
                bitmap = BufferedInputStream(inputs)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return bitmap
        }

        override fun onPostExecute(result: InputStream?) {
            GlobalScope.launch {
                ByteArrayOutputStream().use { output ->
                    result?.copyTo(output)
                    val byteArr = output.toByteArray()
                    val byteBuffer = ByteBuffer.wrap(byteArr)
                    val rewound = byteBuffer.rewind()
                    withContext(Dispatchers.Main) {
                        results(rewound)
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun downloadBitmap(url: String): Bitmap {
        val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val input: InputStream = connection.inputStream
        return BitmapFactory.decodeStream(input)
    }
    private fun getBucketName(coreBucket: String): String {
        val buc = coreBucket.split("/")
        var bucketName = buc[0]
        if (buc.size > 1) {
            bucketName = buc[1]
        }
        return bucketName
    }
}