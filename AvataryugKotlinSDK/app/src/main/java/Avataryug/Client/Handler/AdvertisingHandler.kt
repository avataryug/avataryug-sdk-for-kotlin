package com.Avataryug.client.Handler

import android.util.Log
import com.Avataryug.client.Apis.AdvertisingApi
import com.Avataryug.client.Infrastructure.ClientException
import com.Avataryug.client.Infrastructure.ServerException
import com.Avataryug.client.Models.GetAdPlacementByIDResult
import com.Avataryug.client.Models.GrantAdsRewardRequest
import com.Avataryug.client.Models.GrantAdsRewardResult
import com.Avataryug.client.Models.RecordAdsActivityRequest
import com.Avataryug.client.Models.RecordAdsActivityResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * The "AdvertisingHandler" class is responsible for handling advertising operations.
 * It uses the "Base" class for making API calls.
 * The class provides three methods:
 * "GetAdsPlacement" retrieves a list of ad placements by ID,
 * "RecordAdsActivity" stores ads activity data for reporting
 * And "GrantAdsReward" grants rewards for ads.
 * Each method takes callbacks for handling the response and error conditions.
 */
class AdvertisingHandler(private val apiBase: Base) {

    /**
     * Retrieves a list of ad placements by ID.
     */
    fun getAdPlacementByID(listener: OnGetAdPlacementByIDListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetAdPlacementByIDResult) {
                    listener.onGetAdPlacementByIDResult(response)
                } else {
                    listener.onGetAdPlacementByIDError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGetAdPlacementByIDError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetAdPlacementByID method
     */
    interface OnGetAdPlacementByIDListener
    {
        fun onGetAdPlacementByIDResult(result: GetAdPlacementByIDResult)
        fun onGetAdPlacementByIDError(error: Exception)
    }

    /**
     * Retrieves a list of ad placements by ID
     */
    class GetAdPlacementByID(private val placementID: String) : Base
    {

        override fun callApi(listener: Base.OnApiResultListener) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiInstance = AdvertisingApi()
                    val result: GetAdPlacementByIDResult =
                        apiInstance.getAdPlacementByID(placementID)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    Log.e("Get Ad Placement By ID CLIENT EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                } catch (e: ServerException) {
                    Log.e("Get Ad Placement By ID SERVER EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                } catch (e: Exception) {
                    Log.e("Get Ad Placement By ID EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                }
            }


        }
    }

    /**
     * Stores ads activity data for reporting after ad watch
     * @param listener
     */
    fun recordAdsActivity(listener: OnRecordAdsActivityResultListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is RecordAdsActivityResult) {
                    listener.onRecordAdsActivityResult(response)
                } else {
                    listener.onRecordAdsActivityError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onRecordAdsActivityError(error)
            }
        })
    }

    /**
     * Define the listener interface for the RecordAdsActivity method
     */
    interface OnRecordAdsActivityResultListener
    {
        fun onRecordAdsActivityResult(result: RecordAdsActivityResult)
        fun onRecordAdsActivityError(error: Exception)
    }

    /**
     * Stores ads activity data for reporting after ad watch.
     */
    class RecordAdsActivity(private val revenueCurrency: String, private val adRevenue: Int, private val placementID: String) : Base
    {
        // Implement the callApi function from the Base interface
        override fun callApi(listener: Base.OnApiResultListener)
        {
            // Perform the API call here using the provided data
            // For example, you can use the `apiBase` to make the API call as shown in your original code.
            // Since the actual API call is not provided, the following code is just a placeholder to demonstrate how the call might be done.
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiInstance = AdvertisingApi()

                    val request = RecordAdsActivityRequest(
                        placementID = placementID,
                        adRevenue = adRevenue,
                        revenueCurrency = revenueCurrency
                    )
                    val result: RecordAdsActivityResult = apiInstance.recordAdsActivity(request)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    Log.e("Record Ads Activity CLIENT EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                } catch (e: ServerException) {
                    Log.e("Record Ads Activity SERVER EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                } catch (e: Exception) {
                    Log.e("Record Ads Activity EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Grants rewards for ads.
     */
    fun grantAdsReward(listener: OnGrantAdsRewardResultListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GrantAdsRewardResult) {
                    listener.onGrantAdsRewardResult(response)
                } else {
                    listener.onGrantAdsRewardError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGrantAdsRewardError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GrantAdsReward method
     */
    interface OnGrantAdsRewardResultListener
    {
        fun onGrantAdsRewardResult(result: GrantAdsRewardResult)
        fun onGrantAdsRewardError(error: Exception)
    }

    /**
     * Grants rewards for ads.
     */
    class GrantAdsReward(private val placementID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiInstance = AdvertisingApi()
                    val grantAdsRewardRequest = GrantAdsRewardRequest(
                        placementID = placementID
                    )
                    val result: GrantAdsRewardResult =
                        apiInstance.grantAdsReward(grantAdsRewardRequest)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    Log.e("Grant Ads Reward CLIENT EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                } catch (e: ServerException) {
                    Log.e("Grant Ads Reward SERVER EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                } catch (e: Exception) {
                    Log.e("Grant Ads Reward EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                }
            }
        }

    }
}