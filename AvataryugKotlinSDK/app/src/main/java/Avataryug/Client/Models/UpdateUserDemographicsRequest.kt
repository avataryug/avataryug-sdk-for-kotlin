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
 * @param gender Gender of the user.
 * @param ageRange Age Range of the user.
 * @param race Race of the user.
 */


data class UpdateUserDemographicsRequest (

    /* Gender of the user. */
    @Json(name = "Gender")
    val gender: kotlin.String? = null,

    /* Age Range of the user. */
    @Json(name = "AgeRange")
    val ageRange: kotlin.String? = null,

    /* Race of the user. */
    @Json(name = "Race")
    val race: kotlin.String? = null

)

