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
 * @param bucketName 
 * @param mainCatID 
 * @param platform 
 * @param vertexArray 
 * @param meta 
 * @param ID 
 */


data class GetAllBucketVerticesResultDataInner (

    @Json(name = "BucketName")
    val bucketName: kotlin.String,

    @Json(name = "MainCatID")
    val mainCatID: kotlin.String,

    @Json(name = "Platform")
    val platform: kotlin.String,

    @Json(name = "VertexArray")
    val vertexArray: kotlin.String,

    @Json(name = "Meta")
    val meta: kotlin.String,

    @Json(name = "ID")
    val ID: kotlin.String

)
