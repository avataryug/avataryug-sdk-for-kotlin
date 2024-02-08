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
 * @param owner 
 * @param revenueCurrency 
 * @param adRevenue 
 * @param placementID 
 * @param updatedAt 
 * @param ID 
 */


data class RecordAdsActivityResultData (

    @Json(name = "Owner")
    val owner: kotlin.String? = null,

    @Json(name = "RevenueCurrency")
    val revenueCurrency: kotlin.String? = null,

    @Json(name = "AdRevenue")
    val adRevenue: kotlin.Int? = null,

    @Json(name = "PlacementID")
    val placementID: kotlin.String? = null,

    @Json(name = "UpdatedAt")
    val updatedAt: kotlin.String? = null,

    @Json(name = "ID")
    val ID: kotlin.String? = null

)

