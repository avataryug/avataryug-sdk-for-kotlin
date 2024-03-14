package com.Avataryug.client.Handler

/**
 * This class defines an interface called Base, which serves as a foundation for making API calls.
 * It includes a nested interface called OnApiResultListener that is used to handle API call results and errors
 */
interface Base
{
    fun callApi(listener: OnApiResultListener)

    interface OnApiResultListener
    {
        fun onResult(response: Any)
        fun onError(error: Exception)
    }
}