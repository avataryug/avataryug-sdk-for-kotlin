/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.Avataryug.client.Models


import com.squareup.moshi.Json

/**
 * 
 *
 * @param avatarID Unique identifier for the generated avatar
 * @param avatarData Data related to the AvatarID
 * @param avatarUrl Mesh URL of the Avatar
 * @param thumbUrl Rendered Image URL of the Avatar
 */


data class UpdateUserAvatarRequest (

    /* Unique identifier for the generated avatar */
    @Json(name = "AvatarID")
    val avatarID: kotlin.String,

    /* Data related to the AvatarID */
    @Json(name = "AvatarData")
    val avatarData: kotlin.String,

    /* Mesh URL of the Avatar */
    @Json(name = "AvatarUrl")
    val avatarUrl: kotlin.String,

    /* Rendered Image URL of the Avatar */
    @Json(name = "ThumbUrl")
    val thumbUrl: kotlin.String

)
