package Avataryug.Client.Models

import com.squareup.moshi.Json

data class GetUserAvatarAllDataResult (

    @Json(name = "Code")
    val code: kotlin.Int? = null,

    @Json(name = "Status")
    val status: kotlin.String? = null,

    @Json(name = "Data")
    val `data`: kotlin.collections.List<GetUserAvatarAllDataResponseDataInner>? = null

)
