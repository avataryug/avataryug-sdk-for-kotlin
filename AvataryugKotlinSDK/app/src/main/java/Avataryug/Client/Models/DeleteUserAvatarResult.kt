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
 * @param code 
 * @param status 
 * @param message 
 */


data class DeleteUserAvatarResult (

    @Json(name = "Code")
    val code: kotlin.Int? = null,

    @Json(name = "Status")
    val status: kotlin.String? = null,

    @Json(name = "Message")
    val message: kotlin.String? = null

)

