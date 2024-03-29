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
 * @param emailID User email address attached to their account
 * @param password Password for the AvatarYug account (6-100 characters)
 */


data class AddEmailPasswordRequest (

    /* User email address attached to their account */
    @Json(name = "EmailID")
    val emailID: kotlin.String,

    /* Password for the AvatarYug account (6-100 characters) */
    @Json(name = "Password")
    val password: kotlin.String

)

