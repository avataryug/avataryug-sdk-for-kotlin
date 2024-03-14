import com.squareup.moshi.Json

data class TattooArtifact (

    @Json(name = "device")
    val device: Long,

    @Json(name = "format")
    val format: Long,

    @Json(name = "lod")
    val lod: Long,

    @Json(name = "normals")
    val normals: Long,

    @Json(name = "texture_size")
    val texture_size: Long,

    @Json(name = "url")
    val url: String
)