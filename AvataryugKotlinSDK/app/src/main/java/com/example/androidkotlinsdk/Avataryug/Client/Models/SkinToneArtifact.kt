
import com.squareup.moshi.Json

data class SkinToneArtifact (

    @Json(name = "body_path")
    val body_path: String? = null,

    @Json(name = "device")
    val device: Long? = null,

    @Json(name = "face_path")
    val face_path: String? = null,

    @Json(name = "textureSize")
    val texture_size: Long? = null

)