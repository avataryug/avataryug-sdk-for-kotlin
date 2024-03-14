package Avataryug.Client.Models

import com.squareup.moshi.Json

data class GetUserAvatarAllDataResponseDataInner(

    @Json(name = "TemplateID")
    val TemplateID: kotlin.String = "",

    @Json(name = "ItemCategory")
    val ItemCategory: kotlin.String = "",

    @Json(name = "Artifacts")
    val Artifacts: kotlin.String = "",

    @Json(name = "BlendshapeKeys")
    val BlendshapeKeys: kotlin.String = "",

    @Json(name = "ItemSkin")
    val ItemSkin: kotlin.String = "",

    @Json(name = "ID")
    val ID: kotlin.String = "",

    @Json(name = "Config")
    val Config: kotlin.String = "",

    @Json(name = "CoreBucket")
    val CoreBucket: kotlin.String = "",

    @Json(name = "ConflictingBuckets")
    val ConflictingBuckets: kotlin.String = ""
)