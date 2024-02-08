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
 * @param forceLink If another user is already linked to the account, unlink the other user and re-link. If the current user is already linked, link both accounts.
 * @param googleID Google ID provided by Google.
 * @param googleServerAuthCode Server authentication code obtained on the client by calling getServerAuthCode() (https://developers.google.com/identity/sign-in/android/offline-access) from Google Play for the user.
 */


data class LinkGoogleAccountRequest (

    /* If another user is already linked to the account, unlink the other user and re-link. If the current user is already linked, link both accounts. */
    @Json(name = "ForceLink")
    val forceLink: kotlin.Boolean? = null,

    /* Google ID provided by Google. */
    @Json(name = "GoogleID")
    val googleID: kotlin.String? = null,

    /* Server authentication code obtained on the client by calling getServerAuthCode() (https://developers.google.com/identity/sign-in/android/offline-access) from Google Play for the user. */
    @Json(name = "GoogleServerAuthCode")
    val googleServerAuthCode: kotlin.String? = null

)

