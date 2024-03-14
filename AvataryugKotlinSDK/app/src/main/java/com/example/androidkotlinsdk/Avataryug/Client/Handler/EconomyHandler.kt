package com.Avataryug.client.Handler

import com.Avataryug.client.Apis.EconomyApi
import com.Avataryug.client.Infrastructure.ClientException
import com.Avataryug.client.Infrastructure.ServerException
import com.Avataryug.client.Models.GetEconomyBundleByIDResult
import com.Avataryug.client.Models.GetEconomyBundlesResult
import com.Avataryug.client.Models.GetEconomyContainerByIDResult
import com.Avataryug.client.Models.GetEconomyContainersResult
import com.Avataryug.client.Models.GetEconomyItemsByIDResult
import com.Avataryug.client.Models.GetEconomyItemsResult
import com.Avataryug.client.Models.GetEconomyStoresResult
import com.Avataryug.client.Models.GetStoreItemsByIDResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * The "EconomyHandler" class handles bundle containers, currencies, and items for economy-related operations.
 * It utilizes the "Base" class for making API calls and provides methods for managing
 * bundles, currencies, store, and items within the economy system.
 */
class EconomyHandler (private val apiBase: Base)
{
    /**
     * Get Economy Items
     * @param listener
     */
    fun getEconomyItems(listener: OnGetEconomyItemsListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetEconomyItemsResult) {
                    listener.onGetEconomyItemsResult(response)
                } else {
                    listener.onGetEconomyItemsError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGetEconomyItemsError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetEconomyItems method
     */
    interface OnGetEconomyItemsListener
    {
        fun onGetEconomyItemsResult(result: GetEconomyItemsResult)
        fun onGetEconomyItemsError(error: Exception)
    }

    /**
     *  Get Economy Items
     */
    class GetEconomyItems(private val category: String, private val status: Int, private val gender: Int,private val offset: Int,private val limit: Int) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = EconomyApi()
                    val result:GetEconomyItemsResult = apiInstance.getEconomyItems(category,status,gender,offset,limit)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }
    /**
     *  Get Economy Item by ID
     * @param listener
     */
    fun getEconomyItemsByID(listener: OnGetEconomyItemsByIDListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is GetEconomyItemsByIDResult)
                {
                    listener.onGetEconomyItemsByIDResult(response)
                } else {
                    listener.onGetEconomyItemsByIDError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception)
            {
                listener.onGetEconomyItemsByIDError(error)
            }
        })
    }

    // Define the listener interface for GetEconomyItemsByID method
    interface OnGetEconomyItemsByIDListener
    {
        fun onGetEconomyItemsByIDResult(result: GetEconomyItemsByIDResult)
        fun onGetEconomyItemsByIDError(error: Exception)
    }

    /**
     * Get Economy Item by ID
     */
    class GetEconomyItemsByID(private val itemID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = EconomyApi()
                    val result:GetEconomyItemsByIDResult = apiInstance.getEconomyItemsByID(itemID)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Get Economy Bundle
     * @param listener
     */
    fun getEconomyBundles(listener: OnGetEconomyBundlesListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is GetEconomyBundlesResult)
                {
                    listener.onGetEconomyBundlesResult(response)
                }
                else
                {
                    listener.onGetEconomyBundlesError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception)
            {
                listener.onGetEconomyBundlesError(error)
            }
        })
    }

    /**
     *Define the listener interface for the GetEconomyBundles method
     */
    interface OnGetEconomyBundlesListener
    {
        fun onGetEconomyBundlesResult(result: GetEconomyBundlesResult)
        fun onGetEconomyBundlesError(error: Exception)
    }

    /**
     * Get Economy Bundle
     * @param listener
     */
    class GetEconomyBundles(private val status: Int) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = EconomyApi()
                    val result:GetEconomyBundlesResult = apiInstance.getEconomyBundles(status)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Get Economy Bundles by ID
     * @param listener
     */
    fun getEconomyBundleByID(listener: OnGetEconomyBundleByIDListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is GetEconomyBundleByIDResult)
                {
                    listener.GetEconomyBundleByIDResult(response)
                } else
                {
                    listener.onGetEconomyBundleByIDError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception)
            {
                listener.onGetEconomyBundleByIDError(error)
            }
        })
    }

    /**
     *Define the listener interface for the GetEconomyBundleByID methodm
     */
    interface OnGetEconomyBundleByIDListener
    {
        fun GetEconomyBundleByIDResult(result: GetEconomyBundleByIDResult)
        fun onGetEconomyBundleByIDError(error: Exception)
    }

    /**
     * Get Economy Bundles by ID
     */
    class GetEconomyBundleByID(private val bundleID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = EconomyApi()
                    val result: GetEconomyBundleByIDResult = apiInstance.getEconomyBundleByID(bundleID)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     *Get Economy Containers
     */
    fun getEconomyContainers(listener: OnGetEconomyContainersListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is GetEconomyBundleByIDResult)
                {
                    listener.onGetEconomyContainersResult(response)
                } else
                {
                    listener.onGetEconomyBundleByIDError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception)
            {
                listener.onGetEconomyBundleByIDError(error)
            }
        })
    }

    /**
     *  Define the listener interface for the GetEconomyContainers method
     */
    interface OnGetEconomyContainersListener
    {
        fun onGetEconomyContainersResult(result: GetEconomyBundleByIDResult)
        fun onGetEconomyBundleByIDError(error: Exception)
    }

    /**
     *Get Economy Containers
     */
    class GetEconomyContainers(private val containersStatus: Int) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = EconomyApi()
                    val result:GetEconomyContainersResult = apiInstance.getEconomyContainers(containersStatus)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     *  Get Economy Container by ID
     * @param listener
     */
    fun getEconomyContainerByID(listener: OnGetEconomyContainerByIDListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetEconomyContainerByIDResult) {
                    listener.onGetEconomyContainerByIDResult(response)
                } else {
                    listener.onGetEconomyContainerByIDError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGetEconomyContainerByIDError(error)
            }
        })
    }

    /**
     *  Define the listener interface for the GetEconomyContainerByID method
     */
    interface OnGetEconomyContainerByIDListener
    {
        fun onGetEconomyContainerByIDResult(result: GetEconomyContainerByIDResult)
        fun onGetEconomyContainerByIDError(error: Exception)
    }

    /**
     * Get Economy Container by ID
     */
    class GetEconomyContainerByID(private val containerID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = EconomyApi()
                    val result:GetEconomyContainerByIDResult = apiInstance.getEconomyContainerByID(containerID)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Get Economy Stores
     * @param listener
     */
    fun getEconomyStores(listener: OnGetEconomyStoresListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetEconomyStoresResult) {
                    listener.onGetEconomyStoresResult(response)
                } else {
                    listener.onGetEconomyStoresError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGetEconomyStoresError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetEconomyStores method
     */
    interface OnGetEconomyStoresListener
    {
        fun onGetEconomyStoresResult(result: GetEconomyStoresResult)
        fun onGetEconomyStoresError(error: Exception)
    }

    /**
     * Get Economy Stores
     */
    class GetEconomyStores(private val storesStatus: Int) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = EconomyApi()
                    val result:GetEconomyStoresResult = apiInstance.getEconomyStores(storesStatus)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Retrieves the set of items defined for the specified store, including all prices defined
     * @param listener
     */
    fun getStoreItemsByID(listener: OnGetStoreItemsByIDListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetStoreItemsByIDResult) {
                    listener.onGetStoreItemsByIDResult(response)
                } else {
                    listener.onGetStoreItemsByIDError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGetStoreItemsByIDError(error)
            }
        })
    }

    /**
     * Define the listener interface for the getStoreItemsByID method
     */
    interface OnGetStoreItemsByIDListener
    {
        fun onGetStoreItemsByIDResult(result: GetStoreItemsByIDResult)
        fun onGetStoreItemsByIDError(error: Exception)
    }

    /**
     *Retrieves the set of items defined for the specified store, including all prices defined
     */
    class GetStoreItemsByID(private val storeID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = EconomyApi()
                    val result:GetStoreItemsByIDResult = apiInstance.getStoreItemsByID(storeID)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

}