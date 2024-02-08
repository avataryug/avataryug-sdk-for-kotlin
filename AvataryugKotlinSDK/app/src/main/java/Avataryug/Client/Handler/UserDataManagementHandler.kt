package com.Avataryug.client.Handler

import com.Avataryug.client.Apis.UserDataManagementApi
import com.Avataryug.client.Handler.Base.OnApiResultListener
import com.Avataryug.client.Infrastructure.ClientException
import com.Avataryug.client.Infrastructure.ServerException
import com.Avataryug.client.Models.AddUserAvatarRequest
import com.Avataryug.client.Models.AddUserAvatarResult
import com.Avataryug.client.Models.AddVirtualCurrencyToUserRequest
import com.Avataryug.client.Models.AddVirtualCurrencyToUserResult
import com.Avataryug.client.Models.ConfirmPurchaseRequest
import com.Avataryug.client.Models.ConfirmPurchaseResult
import com.Avataryug.client.Models.ConsumeInstanceRequest
import com.Avataryug.client.Models.ConsumeInstanceResult
import com.Avataryug.client.Models.DeleteUserAvatarResult
import com.Avataryug.client.Models.GetPurchaseResult
import com.Avataryug.client.Models.GetUserInventoryResult
import com.Avataryug.client.Models.GetUsersAllAvatarsResult
import com.Avataryug.client.Models.GrantInstanceToUserRequest
import com.Avataryug.client.Models.GrantInstanceToUserResult
import com.Avataryug.client.Models.PayForPurchaseRequest
import com.Avataryug.client.Models.PayForPurchaseResult
import com.Avataryug.client.Models.PurchaseInstanceRequest
import com.Avataryug.client.Models.PurchaseInstanceResult
import com.Avataryug.client.Models.SubtractUserVirtualCurrencyRequest
import com.Avataryug.client.Models.SubtractUserVirtualCurrencyResult
import com.Avataryug.client.Models.UnlockContainerInstanceRequest
import com.Avataryug.client.Models.UnlockContainerInstanceResult
import com.Avataryug.client.Models.UpdateUserAvatarRequest
import com.Avataryug.client.Models.UpdateUserAvatarResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDataManagementHandler (private val apiBase: Base)
{
    /**
     * Retrieves the user's current inventory of virtual goods.
     * @param listener
     */
    fun getUserInventory(listener: OnGetUserInventoryListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetUserInventoryResult) {
                    listener.onGetUserInventoryResult(response)
                } else {
                    listener.onGetUserInventoryError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGetUserInventoryError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetUserInventory method.
     */
    interface OnGetUserInventoryListener
    {
        fun onGetUserInventoryResult(result: GetUserInventoryResult)
        fun onGetUserInventoryError(error: Exception)
    }

    /**
     * Retrieves the user's current inventory of virtual goods.
     */
    class GetUserInventory(private val userID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = UserDataManagementApi()
                    val result: GetUserInventoryResult = apiInstance.getUserInventory(userID)
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
     * Lists all of the avatars that belong to a specific user.
     * @param listener
     */
    fun getUsersAllAvatars(listener: OnGetUsersAllAvatarsListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetUsersAllAvatarsResult) {
                    listener.onGetUsersAllAvatarsResult(response)
                } else {
                    listener.onGetUsersAllAvatarsError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGetUsersAllAvatarsError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetUsersAllAvatars method
     */
    interface OnGetUsersAllAvatarsListener
    {
        fun onGetUsersAllAvatarsResult(result: GetUsersAllAvatarsResult)
        fun onGetUsersAllAvatarsError(error: Exception)
    }

    /**
     * Lists all of the avatars that belong to a specific user.
     */
    class GetUsersAllAvatars(private val userID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = UserDataManagementApi()
                    val result: GetUsersAllAvatarsResult = apiInstance.getUsersAllAvatars(userID)
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
     * Adds the specified items to the specified user's inventory.
     * @param listener
     */
    fun grantInstanceToUser(listener: OnGrantInstanceToUserListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GrantInstanceToUserResult) {
                    listener.onGrantInstanceToUserResult(response)
                } else {
                    listener.onGrantInstanceToUserError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception) {
                listener.onGrantInstanceToUserError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GrantInstanceToUser method
     */
    interface OnGrantInstanceToUserListener
    {
        fun onGrantInstanceToUserResult(result: GrantInstanceToUserResult)
        fun onGrantInstanceToUserError(error: Exception)
    }

    /**
     * Adds the specified items to the specified user's inventory.
     */
    class GrantInstanceToUser(private val grantInstanceToUserRequest: GrantInstanceToUserRequest) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val instanceID :  String = grantInstanceToUserRequest.instanceIDs.toString()

                    val apiInstance = UserDataManagementApi()
                    val result: GetUsersAllAvatarsResult = apiInstance.getUsersAllAvatars(instanceID)
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
     * Update Avatar for the user.
     * @param listener
     */
    fun updateUserAvatar(listener: OnUpdateUserAvatarListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is UpdateUserAvatarResult) {
                    listener.onUpdateUserAvatarResult(response)
                } else {
                    listener.onUpdateUserAvatarError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception) {
                listener.onUpdateUserAvatarError(error)
            }
        })
    }

    /**
     * Define the listener interface for the UpdateUserAvatar method
     */
    interface OnUpdateUserAvatarListener
    {
        fun onUpdateUserAvatarResult(result: UpdateUserAvatarResult)
        fun onUpdateUserAvatarError(error: Exception)
    }

    /**
     * Update Avatar for the user.
     */
    class UpdateUserAvatar(userAvatarRequest: UpdateUserAvatarRequest) : Base
    {
        private val userAvatarRequest: UpdateUserAvatarRequest

        init {
            this.userAvatarRequest = userAvatarRequest
        }

        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = UserDataManagementApi()
                    val result: UpdateUserAvatarResult = apiInstance.updateUserAvatar(userAvatarRequest)
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
     * Consume uses of a consumable item. When all uses are consumed, it will be removed from the user's inventory.
     * @param listener
     */
    fun consumeInstance(listener: OnConsumeInstanceListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is ConsumeInstanceResult)
                {
                    listener.onConsumeInstanceResult(response)
                } else
                {
                    listener.onConsumeInstanceError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onConsumeInstanceError(error)
            }
        })
    }

    /**
     * Define the listener interface for the consumeInstance method
     */
    interface OnConsumeInstanceListener
    {
        fun onConsumeInstanceResult(result: ConsumeInstanceResult)
        fun onConsumeInstanceError(error: Exception)
    }

    /**
     * Consume uses of a consumable item. When all uses are consumed, it will be removed from the user's inventory.
     */
    class ConsumeInstance(private val ID: String, private val instanceCount: Int) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = UserDataManagementApi()
                    val request = ConsumeInstanceRequest(
                        userID = ID,
                        instanceCount = instanceCount
                    )
                    val result: ConsumeInstanceResult = apiInstance.consumeInstance(request)
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
     *Buys a single item with virtual currency. You must specify both the virtual currency to use to purchase,
     * as well as what the client believes the price to be. This lets the server fail the purchase if the price has changed.
     * @param listener
     */
    fun purchaseInstance(listener: OnPurchaseInstanceListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is String)
                {
                    listener.onPurchaseInstanceResult(response)
                } else
                {
                    listener.onPurchaseInstanceError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception)
            {
                listener.onPurchaseInstanceError(error)
            }
        })
    }

    /**
     * Define the listener interface for the PurchaseInstance method.
     */
    interface OnPurchaseInstanceListener
    {
        fun onPurchaseInstanceResult(result: String)
        fun onPurchaseInstanceError(error: Exception)
    }

    /**
     * Buys a single item with virtual currency.
     * You must specify both the virtual currency to use to purchase, as well as what the client believes the price to be.
     * This lets the server fail the purchase if the price has changed.
     */
    class PurchaseInstance(private val purchaseInstanceRequest: PurchaseInstanceRequest) : Base
    {
        private val purchaseReq: PurchaseInstanceRequest = purchaseInstanceRequest

        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = UserDataManagementApi()
                    val result: PurchaseInstanceResult = apiInstance.purchaseInstance(purchaseReq)
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
     * Increments the user's balance of the specified virtual currency by the stated amount.
     * @param listener
     */
    fun addVirtualCurrencyToUser(listener: OnAddVirtualCurrencyToUserListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is AddVirtualCurrencyToUserResult) {
                    listener.onAddVirtualCurrencyToUserResult(response)
                } else {
                    listener.onAddVirtualCurrencyToUserError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onAddVirtualCurrencyToUserError(error)
            }
        })
    }

    /**
     * Define the listener interface for the AddVirtualCurrencyToUser method
     */
    interface OnAddVirtualCurrencyToUserListener
    {
        fun onAddVirtualCurrencyToUserResult(result: AddVirtualCurrencyToUserResult)
        fun onAddVirtualCurrencyToUserError(error: Exception)
    }

    /**
     * Increments the user's balance of the specified virtual currency by the stated amount.
     */
    class AddVirtualCurrencyToUser(private val virtualCurrency: String, private val amount: Int) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val request = AddVirtualCurrencyToUserRequest(
                        virtualCurrency = virtualCurrency,
                        amount = amount
                    )
                    val apiInstance = UserDataManagementApi()
                    val result: AddVirtualCurrencyToUserResult = apiInstance.addVirtualCurrencyToUser(request)
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
     * Decrements the user's balance of the specified virtual currency by the stated amount. It is possible to make a VC balance negative with this API
     * @param listener
     */
    fun subtractUserVirtualCurrency(listener: OnSubtractUserVirtualCurrencyListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is SubtractUserVirtualCurrencyResult)
                {
                    listener.onSubtractUserVirtualCurrencyResult(response)
                } else
                {
                    listener.onSubtractUserVirtualCurrencyError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception)
            {
                listener.onSubtractUserVirtualCurrencyError(error)
            }
        })
    }

    /**
     * Define the listener interface for the SubtractUserVirtualCurrency method
     */
    interface OnSubtractUserVirtualCurrencyListener
    {
        fun onSubtractUserVirtualCurrencyResult(result: SubtractUserVirtualCurrencyResult)
        fun onSubtractUserVirtualCurrencyError(error: Exception)
    }

    /**
     * Decrements the user's balance of the specified virtual currency by the stated amount.
     * It is possible to make a VC balance negative with this API.
     */
    class SubtractUserVirtualCurrency(private val virtualCurrency: String, private val amount: Int) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val request = SubtractUserVirtualCurrencyRequest(
                        virtualCurrency = virtualCurrency,
                        amount = amount
                    )
                    val apiInstance = UserDataManagementApi()
                    val result: SubtractUserVirtualCurrencyResult = apiInstance.subtractUserVirtualCurrency(request)
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
     * Add Avatar to the user.
     * @param listener
     */
    fun addUserAvatar(listener: OnAddUserAvatarListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is AddUserAvatarResult) {
                    listener.onAddUserAvatarResult(response)
                } else {
                    listener.onAddUserAvatarError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onAddUserAvatarError(error)
            }
        })
    }

    /**
     * Define the listener interface for the AddUserAvatar method
     */
    interface OnAddUserAvatarListener
    {
        fun onAddUserAvatarResult(result: AddUserAvatarResult)
        fun onAddUserAvatarError(error: Exception)
    }

    class AddUserAvatar(private val avatarData: String) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val request = AddUserAvatarRequest( avatarID = "", avatarData = avatarData!!)
                    val apiInstance = UserDataManagementApi()
                    val result: AddUserAvatarResult = apiInstance.addUserAvatar(request)
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
     * Delete specified Avatar for the user.
     * @param listener
     */
    fun deleteUserAvatar(listener: OnDeleteUserAvatarListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is DeleteUserAvatarResult)
                {
                    listener.onDeleteUserAvatarResult(response)
                } else
                {
                    listener.onDeleteUserAvatarError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception)
            {
                listener.onDeleteUserAvatarError(error)
            }
        })
    }

    /**
     * Define the listener interface for the DeleteUserAvatar method
     */
    interface OnDeleteUserAvatarListener
    {
        fun onDeleteUserAvatarResult(result: DeleteUserAvatarResult)
        fun onDeleteUserAvatarError(error: Exception)
    }

    /**
     * Delete specified Avatar for the user
     */
    class DeleteUserAvatar(private val avatarID: String) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = UserDataManagementApi()
                    val result: DeleteUserAvatarResult = apiInstance.deleteUserAvatar(avatarID)
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
     * Confirms with the payment provider that the purchase was approved (if applicable) and adjusts inventory and virtual currency balances as appropriate.
     * @param listener
     */
    fun confirmPurchase(listener: OnConfirmPurchaseListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is ConfirmPurchaseResult)
                {
                    listener.onConfirmPurchaseResult(response)
                } else
                {
                    listener.onConfirmPurchaseError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception)
            {
                listener.onConfirmPurchaseError(error)
            }
        })
    }

    /**
     * Define the listener interface for the ConfirmPurchase method
     */
    interface OnConfirmPurchaseListener
    {
        fun onConfirmPurchaseResult(result: ConfirmPurchaseResult)
        fun onConfirmPurchaseError(error: Exception)
    }

    /**
     * Confirms with the payment provider that the purchase was approved (if applicable) and adjusts inventory and virtual currency balances as appropriate.
     */
    class ConfirmPurchase(private val transactionID: String) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val request = ConfirmPurchaseRequest(
                        transactionID = transactionID
                    )
                    val apiInstance = UserDataManagementApi()
                    val result: ConfirmPurchaseResult = apiInstance.confirmPurchase(request)
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
     * Get the purchase detials
     * @param listener
     */
    fun getPurchase(listener: OnGetPurchaseListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is GetPurchaseResult)
                {
                    listener.onGetPurchaseResult(response)
                } else
                {
                    listener.onGetPurchaseError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception)
            {
                listener.onGetPurchaseError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetPurchase method
     */
    interface OnGetPurchaseListener
    {
        fun onGetPurchaseResult(result: GetPurchaseResult)
        fun onGetPurchaseError(error: Exception)
    }

    /**
     * Retrieves a purchase along with its current Avataryug status.
     * Returns inventory items from the purchase that are still active.
     */
    class GetPurchase : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = UserDataManagementApi()
                    val result: GetPurchaseResult = apiInstance.getPurchase()
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
     * Buys a single item with virtual currency. You must specify both the virtual currency to use to purchase,
     * as well as what the client believes the price to be. This lets the server fail the purchase if the price has changed.
     * @param listener
     */
    fun payForPurchase(listener: OnPayForPurchaseListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is PayForPurchaseResult)
                {
                    listener.onPayForPurchaseResult(response)
                } else
                {
                    listener.onPayForPurchaseError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onPayForPurchaseError(error)
            }
        })
    }

    /**
     * Define the listener interface for the PayForPurchase method
     */
    interface OnPayForPurchaseListener
    {
        fun onPayForPurchaseResult(result: PayForPurchaseResult)
        fun onPayForPurchaseError(error: Exception)
    }

    /**
     * Pay For purchase items.
     */
    class PayForPurchase(private val purchaseRequest: PayForPurchaseRequest) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val Request: PayForPurchaseRequest? = null
                    val apiInstance = UserDataManagementApi()
                    val result: PayForPurchaseResult = apiInstance.payForPurchase(Request)
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
     * Opens the specified container, with the specified key (when required),and returns the contents of the opened container.
     * If the container (and key when relevant) are consumable (RemainingUses > 0),their RemainingUses will be decremented, consistent with the operation of ConsumeItem.
     * @param listener
     */
    fun unlockContainerInstance(listener: OnUnlockContainerInstanceListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is UnlockContainerInstanceResult)
                {
                    listener.onUnlockContainerInstanceResult(response)
                } else
                {
                    listener.onUnlockContainerInstanceError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception)
            {
                listener.onUnlockContainerInstanceError(error)
            }
        })
    }

    /**
     * Define the listener interface for the UnlockContainerInstance method
     */
    interface OnUnlockContainerInstanceListener
    {
        fun onUnlockContainerInstanceResult(result: UnlockContainerInstanceResult)
        fun onUnlockContainerInstanceError(error: Exception)
    }

    /**
     * Opens the specified container, with the specified key (when required), and returns the contents of the opened container.
     * If the container (and key when relevant) are consumable (RemainingUses > 0), their RemainingUses will be decremented, consistent with the operation of ConsumeItem.
     */
    class UnlockContainerInstance(private val containerInstanceID: String, private val keyInstanceID: String) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {

            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val request = UnlockContainerInstanceRequest(
                        containerInstanceID = containerInstanceID,
                        keyInstanceID = keyInstanceID
                    )
                    val apiInstance = UserDataManagementApi()
                    val result: UnlockContainerInstanceResult = apiInstance.unlockContainerInstance(request)
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

