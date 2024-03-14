package com.Avataryug.client

/**
 * Represents a set of configuration settings
 */
class ConfigurationApi
{
    private var accessToken: String = ""

    private  var userId:String = ""

    /**
     * Gets the access token (Bearer/OAuth authentication).
     */
    fun getAccessToken(): String {
        return accessToken
    }

    /**
     * Sets the access token (Bearer/OAuth authentication).
     */
    fun  setAccessToken(value: String) {
        accessToken = value
    }

    /**
     * Get UserID
     */
    fun getUserId(): String
    {
        return userId
    }

    /**
     * Set UserID
     */
    fun setUserId(value: String)
    {
        userId = value
    }

}