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
 * @param userID 
 * @param instanceID 
 * @param instanceType 
 * @param displayName 
 * @param expires 
 * @param count 
 * @param status 
 * @param content 
 * @param ID 
 */


data class GetUserInventoryResultDataInner (

    @Json(name = "UserID")
    val userID: kotlin.String,

    @Json(name = "InstanceID")
    val instanceID: kotlin.String,

    @Json(name = "InstanceType")
    val instanceType: kotlin.String,

    @Json(name = "DisplayName")
    val displayName: kotlin.String,

    @Json(name = "Expires")
    val expires: kotlin.String,

    @Json(name = "Count")
    val count: kotlin.Int,

    @Json(name = "Status")
    val status: kotlin.Int,

    @Json(name = "Content")
    val content: kotlin.String,

    @Json(name = "ID")
    val ID: kotlin.String

)

