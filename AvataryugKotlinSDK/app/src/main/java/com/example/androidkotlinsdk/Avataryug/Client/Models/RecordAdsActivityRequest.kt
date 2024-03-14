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
 * @param placementID Unique identifier of the placement.
 * @param revenueCurrency ISO 4217 code of the currency.
 * @param adRevenue Revenue generated from Ad Activity in centesimal units (Eg.100).
 */


data class RecordAdsActivityRequest (

    /* Unique identifier of the placement. */
    @Json(name = "PlacementID")
    val placementID: kotlin.String,

    /* ISO 4217 code of the currency. */
    @Json(name = "RevenueCurrency")
    val revenueCurrency: kotlin.String,

    /* Revenue generated from Ad Activity in centesimal units (Eg.100). */
    @Json(name = "AdRevenue")
    val adRevenue: kotlin.Int

)
