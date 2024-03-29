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
 * @param customID Custom unique identifier for the user sent from the client.
 * @param createAccount Automatically create a AvatarYug account if one is not currently linked to this ID.
 */


data class LoginWithCustomIDRequest (

    /* Custom unique identifier for the user sent from the client. */
    @Json(name = "CustomID")
    var customID: kotlin.String,

    /* Automatically create a AvatarYug account if one is not currently linked to this ID. */
    @Json(name = "CreateAccount")
    val createAccount: kotlin.Boolean? = null

)

