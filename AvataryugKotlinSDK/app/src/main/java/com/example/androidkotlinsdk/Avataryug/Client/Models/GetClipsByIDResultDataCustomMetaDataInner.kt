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
 * @param key 
 * @param selected 
 * @param `value` 
 */


data class GetClipsByIDResultDataCustomMetaDataInner (

    @Json(name = "key")
    val key: kotlin.String,

    @Json(name = "selected")
    val selected: kotlin.Boolean,

    @Json(name = "value")
    val `value`: kotlin.String

)

