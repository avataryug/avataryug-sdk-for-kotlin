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
 * @param artifacts 
 * @param tags 
 * @param color 
 * @param metadata 
 * @param status 
 * @param clipType 
 * @param ID
 * @param ClipTemplateID
 */


data class GetClipsByIDResultData (

    @Json(name = "DisplayName")
    val displayName: kotlin.String? = null,

    @Json(name = "Description")
    val description: kotlin.String? = null,

    @Json(name = "Category")
    val category: kotlin.String? = null,

    @Json(name = "CustomMetaData")
    val customMetaData: kotlin.String? = null,

    @Json(name = "ThumbnailUrl")
    val ThumbnailUrl: kotlin.String? = null,

    @Json(name = "Artifacts")
    val artifacts: kotlin.String? = null,

    @Json(name = "Tags")
    val tags: kotlin.String? = null,

    @Json(name = "Color")
    val color: kotlin.String? = null,

    @Json(name = "Metadata")
    val metadata: kotlin.String? = null,

    @Json(name = "Status")
    val status: kotlin.Int? = null,

    @Json(name = "ClipType")
    val clipType: kotlin.Int? = null,

    @Json(name = "ID")
    val ID: kotlin.String? = null,

    @Json(name = "ClipTemplateID")
    val ClipTemplateID: kotlin.String? = null
)

