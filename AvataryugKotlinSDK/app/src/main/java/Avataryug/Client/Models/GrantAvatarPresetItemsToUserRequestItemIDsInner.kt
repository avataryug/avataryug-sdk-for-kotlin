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
 * @param itemID Unique idenifier of the item being granted
 */


data class GrantAvatarPresetItemsToUserRequestItemIDsInner (

    /* Unique idenifier of the item being granted */
    @Json(name = "ItemID")
    val itemID: kotlin.String

)

