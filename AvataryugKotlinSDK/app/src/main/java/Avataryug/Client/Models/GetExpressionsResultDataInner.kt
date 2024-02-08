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
 * @param displayName 
 * @param description 
 * @param category 
 * @param customMetaData 
 * @param ThumbnailUrl
 * @param blendshapeKeys 
 * @param tags 
 * @param color 
 * @param metadata 
 * @param status 
 * @param ID 
 */


data class GetExpressionsResultDataInner (

    @Json(name = "DisplayName")
    val displayName: kotlin.String,

    @Json(name = "Description")
    val description: kotlin.String,

    @Json(name = "Category")
    val category: kotlin.String,

    @Json(name = "CustomMetaData")
    val customMetaData: kotlin.String,

    @Json(name = "ThumbnailUrl")
    val ThumbnailUrl: kotlin.String,

    @Json(name = "BlendshapeKeys")
    val blendshapeKeys: kotlin.String,

    @Json(name = "Tags")
    val tags: kotlin.String,

    @Json(name = "Color")
    val color: kotlin.String,

    @Json(name = "Metadata")
    val metadata: kotlin.String,

    @Json(name = "Status")
    val status: kotlin.Int,

    @Json(name = "ID")
    val ID: kotlin.String

)
