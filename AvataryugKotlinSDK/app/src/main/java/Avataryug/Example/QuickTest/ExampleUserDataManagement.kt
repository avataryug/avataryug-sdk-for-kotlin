package Avataryug.Example.QuickTest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.Avataryug.client.ConfigurationApi
import com.Avataryug.client.Handler.UserDataManagementHandler
import com.Avataryug.client.Models.AddUserAvatarResult
import com.Avataryug.client.Models.AddVirtualCurrencyToUserResult
import com.Avataryug.client.Models.ConfirmPurchaseResult
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
import com.Avataryug.client.Models.SubtractUserVirtualCurrencyResult
import com.Avataryug.client.Models.UnlockContainerInstanceResult
import com.Avataryug.client.Models.UpdateUserAvatarRequest
import com.Avataryug.client.Models.UpdateUserAvatarResult
import com.example.mykotlinandroidsdk.R

/**
 * This ExampleUserDataManagement Class demonstrates API calling methods with temporary data.
 * A simple [Fragment] subclass.
 * Use the [ExampleUserDataManagement.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExampleUserDataManagement : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example_user_data_management, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val getUserInventoryBtn: Button = view.findViewById<Button>(R.id.GetUserInventoryBtn)
        getUserInventoryBtn.setOnClickListener {
            GetuserInventory()
            Toast.makeText(activity, "Get User Inventory", Toast.LENGTH_SHORT).show()
        }

        val getUserAllAvatarBtn: Button = view.findViewById<Button>(R.id.GetUsersAllAvatarsBtn)
        getUserAllAvatarBtn.setOnClickListener {
            GetUsersAllAvatars()
            Toast.makeText(activity, "Get Users All Avatars", Toast.LENGTH_SHORT).show()
        }

        val grantInstanceToUserBtn: Button = view.findViewById<Button>(R.id.GrantInstanceToUserBtn)
        grantInstanceToUserBtn.setOnClickListener {
            GrantInstanceToUser()
            Toast.makeText(activity, "Grant Instance To User ", Toast.LENGTH_SHORT).show()
        }

        val updateUserAvatarBtn: Button = view.findViewById<Button>(R.id.UpdateUserAvatarBtn)
        updateUserAvatarBtn.setOnClickListener {
            UpdateUserAvatar()
            Toast.makeText(activity, "Update User Avatar", Toast.LENGTH_SHORT).show()
        }

        val consumeInstanceBtn: Button = view.findViewById<Button>(R.id.ConsumeInstanceBtn)
        consumeInstanceBtn.setOnClickListener {
            ConsumeInstance()
            Toast.makeText(activity, "Consume Instance  ", Toast.LENGTH_SHORT).show()
        }

        val purchaseInstanceBtn: Button = view.findViewById<Button>(R.id.PurchaseInstanceBtn)
        purchaseInstanceBtn.setOnClickListener {
            PurchaseInstance()
            Toast.makeText(activity, "Purchase Instance  ", Toast.LENGTH_SHORT).show()
        }

        val addVirtualCurrencyToUserBtn: Button =view.findViewById<Button>(R.id.AddVirtualCurrencyToUserBtn)
        addVirtualCurrencyToUserBtn.setOnClickListener {
            AddVirtualCurrencyToUser()
            Toast.makeText(activity, "Add VirtualCurrency To User", Toast.LENGTH_SHORT).show()
        }

        val subtractUserVirtualCurrencyBtn: Button = view.findViewById<Button>(R.id.SubtractUserVirtualCurrencyBtn)
        subtractUserVirtualCurrencyBtn.setOnClickListener {
            SubtractUserVirtualCurrency()
            Toast.makeText(activity, "Subtract VirtualCurrency To User", Toast.LENGTH_SHORT).show()
        }

        val addUserAvatarBtn: Button = view.findViewById<Button>(R.id.AddUserAvatarBtn)
        addUserAvatarBtn.setOnClickListener {
            AddUserAvatar()
            Toast.makeText(activity, "Add User Avatar", Toast.LENGTH_SHORT).show()
        }

        val DeleteUserAvatarBtn: Button = view.findViewById<Button>(R.id.DeleteUserAvatarBtn)
        DeleteUserAvatarBtn.setOnClickListener {
            DeleteUserAvatar()
            Toast.makeText(activity, "Delete User Avatar", Toast.LENGTH_SHORT).show()
        }

        val confirmPurchaseBtn: Button = view.findViewById<Button>(R.id.ConfirmPurchaseBtn)
        confirmPurchaseBtn.setOnClickListener {
            ConfirmPurchase()
            Toast.makeText(activity, "Confirm Purchase", Toast.LENGTH_SHORT).show()
        }

        val getPurchaseBtn: Button = view.findViewById<Button>(R.id.GetPurchaseBtn)
        getPurchaseBtn.setOnClickListener {
            GetsPurchase()
            Toast.makeText(activity, " Get Purchase", Toast.LENGTH_SHORT).show()
        }

        val payForPurchaseBtn: Button = view.findViewById<Button>(R.id.PayForPurchaseBtn)
        payForPurchaseBtn.setOnClickListener {
            PayForPurchase()
            Toast.makeText(activity, "Pay For Purchase", Toast.LENGTH_SHORT).show()
        }

        val unlockContainerInstanceBtn: Button = view.findViewById<Button>(R.id.UnlockContainerInstanceBtn)
        unlockContainerInstanceBtn.setOnClickListener {
            UnlockContainerInstance()
            Toast.makeText(activity, " Unlock Container Instance", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Retrieves the user's current inventory of virtual goods.
     */
    private fun GetuserInventory()
    {
        val  accessHolder = ConfigurationApi()
        val handler = UserDataManagementHandler(UserDataManagementHandler.GetUserInventory(accessHolder.getUserId()))
        handler.getUserInventory(object : UserDataManagementHandler.OnGetUserInventoryListener
        {
            override fun onGetUserInventoryResult(result: GetUserInventoryResult)
            {
                val responseText = "API Response: $result"
                Log.i("onGetUserInventory->>Result--", responseText)
            }

            override fun onGetUserInventoryError(error: Exception)
            {
                val errorText = "API Error: " + error.message
                Log.e(" onGetUserInventory--->>Error--", errorText)
            }
        })
    }

    /**
     * Lists all of the avatars that belong to a specific user.
     */
    private fun GetUsersAllAvatars()
    {
        val  accessHolder = ConfigurationApi()
        val handler = UserDataManagementHandler(UserDataManagementHandler.GetUsersAllAvatars(accessHolder.getUserId()))
        handler.getUsersAllAvatars(object : UserDataManagementHandler.OnGetUsersAllAvatarsListener
        {
            override fun onGetUsersAllAvatarsResult(result: GetUsersAllAvatarsResult)
            {
                val responseText = "API Response: $result"
                Log.i("onGetUsersAllAvatars->>Result--", responseText)
            }
            override fun onGetUsersAllAvatarsError(error: Exception)
            {
                val errorText = "API Error: " + error.message
                Log.e(" onGetUsersAllAvatars--->>Error--", errorText)
            }
        })
    }

    /**
     * Adds the specified items to the specified user's inventory.
     */
    private fun GrantInstanceToUser()
    {
        val grantInstanceToUserRequest = GrantInstanceToUserRequest()

        val handler = UserDataManagementHandler(UserDataManagementHandler.GrantInstanceToUser(grantInstanceToUserRequest))
        handler.grantInstanceToUser(object : UserDataManagementHandler.OnGrantInstanceToUserListener
        {
            override fun onGrantInstanceToUserResult(result: GrantInstanceToUserResult)
            {
                val responseText ="onGrantInstanceToUser Response: $result"
                Log.i("Result--", responseText)
            }
            override fun onGrantInstanceToUserError(error: Exception)
            {
                val errorText = "onGrantInstanceToUser Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Update Avatar for the user.
     */
    private fun UpdateUserAvatar()
    {
        val updateUserAvatarRequest = UpdateUserAvatarRequest(
            avatarData = "",
            avatarID = "sfaf",
            avatarUrl = "dasf",
            thumbUrl = ""
        )

        val handler = UserDataManagementHandler(UserDataManagementHandler.UpdateUserAvatar(updateUserAvatarRequest))
        handler.updateUserAvatar(object : UserDataManagementHandler.OnUpdateUserAvatarListener {
            override fun onUpdateUserAvatarResult(result: UpdateUserAvatarResult) {
                val responseText = "onUpdateUserAvatar Response: $result"
                Log.i("Result--", responseText)
            }
            override fun onUpdateUserAvatarError(error: Exception) {
                val errorText = "onUpdateUserAvatar Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Consume uses of a consumable item. When all uses are consumed, it will be removed from the user's inventory.
     */
    private fun ConsumeInstance()
    {
        val handler = UserDataManagementHandler(UserDataManagementHandler.ConsumeInstance("", 1))
        handler.consumeInstance(object : UserDataManagementHandler.OnConsumeInstanceListener
        {
            override fun onConsumeInstanceResult(result: ConsumeInstanceResult)
            {
                val responseText = "onConsumeInstance Response: $result"
                Log.i("Result--", responseText)
            }
            override fun onConsumeInstanceError(error: Exception)
            {
                val errorText = "onConsumeInstance Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Buys a single item with virtual currency.
     * You must specify both the virtual currency to use to purchase, as well as what the client believes the price to be.
     * This lets the server fail the purchase if the price has changed.
     */
    private fun PurchaseInstance()
    {
        val purchaseInstanceRequest = PurchaseInstanceRequest(
            instanceID = "",
            instanceType = "",
            storeID = "",
            price = 0,
            virtualCurrency = "CC",
            userID = ""
        )

        val handler = UserDataManagementHandler(UserDataManagementHandler.PurchaseInstance(purchaseInstanceRequest))
        handler.purchaseInstance(object : UserDataManagementHandler.OnPurchaseInstanceListener
        {
            override fun onPurchaseInstanceResult(result: String)
            {
                if (result != null)
                {
                    val responseText = "onPurchaseInstance Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onPurchaseInstanceError(error: java.lang.Exception)
            {
                val errorText = "onPurchaseInstance Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Increments the user's balance of the specified virtual currency by the stated amount.
     */
    private fun AddVirtualCurrencyToUser()
    {
        val handler = UserDataManagementHandler(UserDataManagementHandler.AddVirtualCurrencyToUser("CN", 10))
        handler.addVirtualCurrencyToUser(object :
            UserDataManagementHandler.OnAddVirtualCurrencyToUserListener {
            override fun onAddVirtualCurrencyToUserResult(result: AddVirtualCurrencyToUserResult)
            {
                if (result != null)
                {
                    val responseText = "onAddVirtualCurrencyToUser Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onAddVirtualCurrencyToUserError(error: Exception)
            {
                val errorText = "onAddVirtualCurrencyToUser Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Decrements the user's balance of the specified virtual currency by the stated amount.
     * It is possible to make a VC balance negative with this API.
     */
    private fun SubtractUserVirtualCurrency()
    {
        val handler = UserDataManagementHandler(UserDataManagementHandler.SubtractUserVirtualCurrency("CN", 5))
        handler.subtractUserVirtualCurrency(object :
            UserDataManagementHandler.OnSubtractUserVirtualCurrencyListener
        {
            override fun onSubtractUserVirtualCurrencyResult(result: SubtractUserVirtualCurrencyResult)
            {
                if (result != null)
                {
                    val responseText = "onAddVirtualCurrencyToUser Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onSubtractUserVirtualCurrencyError(error: Exception)
            {
                val errorText = "onAddVirtualCurrencyToUser Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Add Avatar to the user.
     */
    private fun AddUserAvatar()
    {
        val handler = UserDataManagementHandler(UserDataManagementHandler.AddUserAvatar("ffsfasf"))
        handler.addUserAvatar(object : UserDataManagementHandler.OnAddUserAvatarListener {
            override fun onAddUserAvatarResult(result: AddUserAvatarResult)
            {
                if (result != null)
                {
                    val responseText = "onAddUserAvatar Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onAddUserAvatarError(error: Exception)
            {
                val errorText = "onAddUserAvatar Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Delete specified Avatar for the use
     */
    private fun DeleteUserAvatar()
    {
        val handler = UserDataManagementHandler(UserDataManagementHandler.DeleteUserAvatar(""))
        handler.deleteUserAvatar(object : UserDataManagementHandler.OnDeleteUserAvatarListener
        {
            override fun onDeleteUserAvatarResult(result: DeleteUserAvatarResult)
            {
                if (result != null)
                {
                    val responseText = "onDeleteUserAvatar Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onDeleteUserAvatarError(error: Exception)
            {
                val errorText = "onDeleteUserAvatar Error: " + error.message
                Log.e(" Error--", errorText)
            }

        })
    }

    private fun ConfirmPurchase()
    {
        val handler = UserDataManagementHandler(UserDataManagementHandler.ConfirmPurchase("sfas[ff"))
        handler.confirmPurchase(object : UserDataManagementHandler.OnConfirmPurchaseListener {
            override fun onConfirmPurchaseResult(result: ConfirmPurchaseResult)
            {
                if (result != null)
                {
                    val responseText = "onconfirmPurchase Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onConfirmPurchaseError(error: Exception)
            {
                val errorText = "onconfirmPurchase Error: " + error.message
                Log.e(" Error--", errorText)
            }

        })
    }

    private fun GetsPurchase()
    {
        val handler = UserDataManagementHandler(UserDataManagementHandler.GetPurchase())
        handler.getPurchase(object : UserDataManagementHandler.OnGetPurchaseListener
        {
            override fun onGetPurchaseResult(result: GetPurchaseResult)
            {
                if (result != null) {
                    val responseText = "onGetPurchase Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onGetPurchaseError(error: Exception)
            {
                val errorText = "onGetPurchase Error: " + error.message
                Log.e(" Error--", errorText)
            }

        })
    }

    private fun PayForPurchase()
    {
        val acc = ConfigurationApi()
        val purchaseRequest = PayForPurchaseRequest(
            currencyCode = "CC",
            metadata = "",
            userID = acc.getUserId(),
            paymentProvider = "sfas",
            transactionID = "fsasfafqr"
        )

        val handler = UserDataManagementHandler(UserDataManagementHandler.PayForPurchase(purchaseRequest))
        handler.payForPurchase(object : UserDataManagementHandler.OnPayForPurchaseListener {
            override fun onPayForPurchaseResult(result: PayForPurchaseResult)
            {
                if (result != null)
                {
                    val responseText = "onPayForPurchase Response: $result"
                    Log.i("Result--", responseText)
                }
            }

            override fun onPayForPurchaseError(error: Exception)
            {
                val errorText = "onPayForPurchase Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Opens the specified container, with the specified key (when required), and returns the contents of the opened container.
     * If the container (and key when relevant) are consumable (RemainingUses > 0), their RemainingUses will be decremented, consistent with the operation of ConsumeItem.
     */
    private fun UnlockContainerInstance()
    {
        val handler = UserDataManagementHandler(UserDataManagementHandler.UnlockContainerInstance("sofhh", "sfihasiv"))
        handler.unlockContainerInstance(object :
            UserDataManagementHandler.OnUnlockContainerInstanceListener
        {
            override fun onUnlockContainerInstanceResult(result: UnlockContainerInstanceResult)
            {
                if (result != null)
                {
                    val responseText = "onUnlockContainer Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onUnlockContainerInstanceError(error: Exception)
            {
                val errorText = "onUnlockContainer Error: " + error.message
                Log.e(" Error--", errorText)
            }

        })
    }

}