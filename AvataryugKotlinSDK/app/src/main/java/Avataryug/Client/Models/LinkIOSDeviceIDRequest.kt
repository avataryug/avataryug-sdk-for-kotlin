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
 * @param deviceID  iOS identifier for the user's device.
 * @param forceLink If another user is already linked to the device, unlink the other user and re-link.
 */


data class LinkIOSDeviceIDRequest (

    /*  iOS identifier for the user's device. */
    @Json(name = "DeviceID")
    val deviceID: kotlin.String,

    /* If another user is already linked to the device, unlink the other user and re-link. */
    @Json(name = "ForceLink")
    val forceLink: kotlin.Boolean? = null

)

