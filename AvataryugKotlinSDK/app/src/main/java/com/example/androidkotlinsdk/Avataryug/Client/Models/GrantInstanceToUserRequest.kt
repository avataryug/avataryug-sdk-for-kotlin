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
 * @param instanceIDs 
 */


data class GrantInstanceToUserRequest (

    @Json(name = "InstanceIDs")
    val instanceIDs: MutableList<GrantInstanceToUserRequestInstanceIDsInner> = mutableListOf()

)
