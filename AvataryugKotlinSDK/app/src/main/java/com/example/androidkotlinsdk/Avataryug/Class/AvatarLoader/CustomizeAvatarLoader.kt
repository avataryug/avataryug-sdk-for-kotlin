package Avataryug.Class.AvatarLoader

import android.view.SurfaceView
import com.google.android.filament.MaterialInstance
import com.google.android.filament.Texture

class CustomizeAvatarLoader : AvatarLoaderBase() {
    companion object {
        val instance: CustomizeAvatarLoader by lazy { CustomizeAvatarLoader() }
    }
    lateinit var modelViewer: CustomModelViewer
    lateinit var surfaceView: SurfaceView
    lateinit var faceMaterial: MaterialInstance
    lateinit var eyeballMaterial: MaterialInstance
    lateinit var bodyMaterial: MaterialInstance
    lateinit var emptyTexture : Texture
    lateinit var bodyTexture : Texture
    lateinit var eyeballTexture : Texture
    lateinit var faceTexture : Texture
    lateinit var eyebrowTexture : Texture
    lateinit var lipsTexture : Texture
    lateinit var hairsTexture : Texture
    lateinit var facialHairTexture : Texture

    lateinit var defaultFaceTexture : Texture
    lateinit var defaultBodyTexture : Texture
    lateinit var defaultEyebrowTexture : Texture
    lateinit var defaultLipTexture : Texture
    lateinit var defaultEyeballTexture : Texture


    lateinit var femaleTopTexture : Texture
    lateinit var femaleTopMaterial: MaterialInstance
    lateinit var femaleBottomMaterial: MaterialInstance
    lateinit var maleTopMaterial: MaterialInstance
    lateinit var maleBottomMaterial: MaterialInstance
}