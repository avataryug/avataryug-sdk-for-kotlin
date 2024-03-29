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
 * @param appleID Apple ID provided by Apple.
 * @param forceLink If another user is already linked to a specific Apple account, unlink the other user and re-link.
 * @param identityToken The JSON Web token (JWT) returned by Apple after login. Represented as the identityToken field in the authorization credential payload. Used to validate the request and find the user ID (Apple subject) to link with.
 */


data class LinkAppleRequest (

    /* Apple ID provided by Apple. */
    @Json(name = "AppleID")
    val appleID: kotlin.String,

    /* If another user is already linked to a specific Apple account, unlink the other user and re-link. */
    @Json(name = "ForceLink")
    val forceLink: kotlin.Boolean? = null,

    /* The JSON Web token (JWT) returned by Apple after login. Represented as the identityToken field in the authorization credential payload. Used to validate the request and find the user ID (Apple subject) to link with. */
    @Json(name = "IdentityToken")
    val identityToken: kotlin.String? = null

)

