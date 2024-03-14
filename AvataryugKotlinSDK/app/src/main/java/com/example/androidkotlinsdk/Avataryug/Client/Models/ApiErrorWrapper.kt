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
 * @param error 
 * @param errorCode 
 * @param errorMessage 
 */


data class ApiErrorWrapper (

    @Json(name = "Code")
    val code: kotlin.Int? = null,

    @Json(name = "Status")
    val status: kotlin.String? = null,

    @Json(name = "Error")
    val error: kotlin.String? = null,

    @Json(name = "ErrorCode")
    val errorCode: kotlin.Int? = null,

    @Json(name = "ErrorMessage")
    val errorMessage: kotlin.String? = null

)
