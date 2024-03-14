import com.squareup.moshi.Json

data class ThreeDArtifact (

    @Json(name = "device")
    val device:Int? = null,

    @Json(name = "format")
    val format:Int? = null,

    @Json(name = "lod")
    val lod:Int? = null,

    @Json(name = "normals")
    val normals:Int? = null,

    @Json(name = "texture_size")
    val texture_size:Int? = null ,

    @Json(name = "url")
    val url:String? = null

)
