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
 * @param googleID Identifier provided by Google for this users Google ID
 * @param googleServerAuthCode OAuth 2.0 server authentication code obtained on the client by calling the getServerAuthCode() (https://developers.google.com/identity/sign-in/android/offline-access) Google client API.
 * @param createAccount Automatically create a AvatarYug account if one is not currently linked to this ID.
 */


data class LoginWithGoogleRequest (

    /* Identifier provided by Google for this users Google ID */
    @Json(name = "GoogleID")
    val googleID: kotlin.String,

    /* OAuth 2.0 server authentication code obtained on the client by calling the getServerAuthCode() (https://developers.google.com/identity/sign-in/android/offline-access) Google client API. */
    @Json(name = "GoogleServerAuthCode")
    val googleServerAuthCode: kotlin.String,

    /* Automatically create a AvatarYug account if one is not currently linked to this ID. */
    @Json(name = "CreateAccount")
    val createAccount: kotlin.Boolean? = null

)
