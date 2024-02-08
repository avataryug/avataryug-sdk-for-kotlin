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
import com.Avataryug.client.Handler.UserAccountManagementHandler
import com.Avataryug.client.Models.AddGenericServiceIDResult
import com.Avataryug.client.Models.ChangePasswordResult
import com.Avataryug.client.Models.DeleteUserResult
import com.Avataryug.client.Models.GetUserAccountInfoResult
import com.Avataryug.client.Models.GetUserProfileResult
import com.Avataryug.client.Models.LinkAndroidDeviceIDResult
import com.Avataryug.client.Models.LinkAppleResult
import com.Avataryug.client.Models.LinkCustomIDResult
import com.Avataryug.client.Models.LinkFacebookAccountResult
import com.Avataryug.client.Models.LinkGoogleAccountResult
import com.Avataryug.client.Models.LinkIOSDeviceIDResult
import com.Avataryug.client.Models.RemoveGenericServiceIDResult
import com.Avataryug.client.Models.UnlinkAndroidDeviceIDResult
import com.Avataryug.client.Models.UnlinkAppleResult
import com.Avataryug.client.Models.UnlinkCustomIDResult
import com.Avataryug.client.Models.UnlinkFacebookAccountResult
import com.Avataryug.client.Models.UnlinkGoogleAccountResult
import com.Avataryug.client.Models.UnlinkIOSDeviceIDResult
import com.Avataryug.client.Models.UpdateDefaultAvatarIDResult
import com.Avataryug.client.Models.UpdateUserDemographicsResult
import com.Avataryug.client.Models.UpdateUserProjectDisplayNameResult
import com.example.mykotlinandroidsdk.R

/**
 * This ExampleUserAccountManagement Class demonstrates API calling methods with temporary data.
 * A simple [Fragment] subclass.
 * Use the [ExampleUserAccountManagement.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExampleUserAccountManagement : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example_user_account_management, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val linkCustomIDBtn = view.findViewById<Button>(R.id.LinkCustomIDBtn)
        linkCustomIDBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Link CustomID..", Toast.LENGTH_LONG).show()
            LinkCustomID()
        }

        val unlinkCustomBtn = view.findViewById<Button>(R.id.UnlinkCustomBtn)
        unlinkCustomBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Unlink CustomID..", Toast.LENGTH_LONG).show()
            UnlinkCustomID()
        }

        val linkAndroidDeviceBtn = view.findViewById<Button>(R.id.LinkAndroidDeviceBtn)
        linkAndroidDeviceBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Link Android Device..", Toast.LENGTH_LONG).show()
            LinkAndroidDevice()
        }

        val unLinkAndroidDeviceBtn = view.findViewById<Button>(R.id.UnLinkAndroidDeviceBtn)
        unLinkAndroidDeviceBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Unlink Android Device..", Toast.LENGTH_LONG).show()
            UnLinkAndroidDevice()
        }

        val linkIOSDeviceBtn = view.findViewById<Button>(R.id.LinkIOSDeviceBtn)
        linkIOSDeviceBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Link IOS Device..", Toast.LENGTH_LONG).show()
            LinkIosDevice()
        }

        val unLinkIOSDeviceBtn = view.findViewById<Button>(R.id.UnLinkIOSDeviceBtn)
        unLinkIOSDeviceBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Unlink IOS Device..", Toast.LENGTH_LONG).show()
            UnlinkIosDevice()
        }

        val linkAppleBtn = view.findViewById<Button>(R.id.LinkAppleBtn)
        linkAppleBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Link Apple ..", Toast.LENGTH_LONG).show()
            LinkApple()
        }

        val unLinkAppleBtn = view.findViewById<Button>(R.id.UnLinkAppleBtn)
        unLinkAppleBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Unlink Apple ..", Toast.LENGTH_LONG).show()
            UnLinkApple()
        }

        val linkGoogleBtn = view.findViewById<Button>(R.id.LinkGoogleBtn)
        linkGoogleBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Link Google Account ..", Toast.LENGTH_LONG).show()
            LinkGoogle()
        }

        val unLinkGoogleBtn = view.findViewById<Button>(R.id.UnLinkGoogleBtn)
        unLinkGoogleBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Unlink Google Account ..", Toast.LENGTH_LONG).show()
            UnlinkGoogle()
        }

        val linkFacebookBtn = view.findViewById<Button>(R.id.LinkFacebookBtn)
        linkFacebookBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Link Facebook Account ..", Toast.LENGTH_LONG).show()
            LinkFB()
        }

        val unLinkFacebookBtn = view.findViewById<Button>(R.id.UnLinkFacebookBtn)
        unLinkFacebookBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Unlink Facebook Account ..", Toast.LENGTH_LONG).show()
            UnlinkFB()
        }

        val addGenericServiceBtn = view.findViewById<Button>(R.id.AddGenericServiceBtn)
        addGenericServiceBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Add Generic Service Btn ..", Toast.LENGTH_LONG).show()
            AddGenericService()
        }

        val removeGenericServiceBtn = view.findViewById<Button>(R.id.RemoveGenericServiceBtn)
        removeGenericServiceBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Remove Generic Service Btn ..", Toast.LENGTH_LONG).show()
            RemoveGenericService()
        }

        val updateDisplayNameBtn = view.findViewById<Button>(R.id.UpdateDisplayNameBtn)
        updateDisplayNameBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Update Display Name  ..", Toast.LENGTH_LONG).show()
            updateProjectDisplayName();
        }

        val getUserAccountInfo = view.findViewById<Button>(R.id.getUserAccountInfo)
        getUserAccountInfo.setOnClickListener {
            Toast.makeText(requireContext(),"Get User Account Info  ..", Toast.LENGTH_LONG).show()
            GetUserAccountInfo();
        }

        val updateUserDemographicBtn = view.findViewById<Button>(R.id.UpdateUserDemographicBtn)
        updateUserDemographicBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Update User Demographic..", Toast.LENGTH_LONG).show()
            UpdateUserDemographic()
        }

        val getuserProfileBtn = view.findViewById<Button>(R.id.GetuserProfileBtn)
        getuserProfileBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Delete User Btn..", Toast.LENGTH_LONG).show()
            GetUserProfile()
        }

        val deleteUserBtn = view.findViewById<Button>(R.id.DeleteUserBtn)
        deleteUserBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Delete User Btn..", Toast.LENGTH_LONG).show()
            DeleteUsers()
        }

        val updateDefaultAvatarBtn = view.findViewById<Button>(R.id.UpdateDefaultAvatarBtn)
        updateDefaultAvatarBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Update Default Avatar..", Toast.LENGTH_LONG).show()
            UpdateDefaultAvatarID()
        }

        val changePasswordBtn = view.findViewById<Button>(R.id.ChangePasswordBtn)
        changePasswordBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Change Password..", Toast.LENGTH_LONG).show()
            Changepassword()
        }

    }

    /**
     * Links the custom identifier, generated by the Project, to the user's Avataryug account.
     */
    private fun LinkCustomID()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.LinkCustomID("",true))
        handler.linkCustomID(object : UserAccountManagementHandler.OnLinkCustomIDResultListener{
            override fun onLinkCustomIDResult(result: LinkCustomIDResult) {
                val responseText = "API Response: $result"
                Log.i("linkCustomID Response--", responseText)
            }
            override fun onError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("linkCustomID ERROR--", errorText)
            }
        })
    }

    /**
     * Unlinks the related custom identifier from the user's Avataryug account.
     */
    private fun UnlinkCustomID()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UnlinkCustomID())
        handler.unlinkCustomID(object : UserAccountManagementHandler.OnUnlinkCustomIDListener{
            override fun onUnlinkCustomIDResult(result: UnlinkCustomIDResult) {
                val responseText = "API Response: $result"
                Log.i("unlinkCustomID Response--", responseText)
            }
            override fun onUnlinkCustomIDError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("unlinkCustomID ERROR--", errorText)
            }
        })
    }

    /**
     * Links the Android device identifier to the user's Avataryug account.
     */
    private fun LinkAndroidDevice()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.LinkAndroidDeviceID("",true))
        handler.linkAndroidDeviceID(object : UserAccountManagementHandler.LinkAndroidDeviceIDListener{
            override fun onLinkAndroidDeviceIDResult(result: LinkAndroidDeviceIDResult) {
                val responseText = "API Response: $result"
                Log.i("linkAndroidDeviceID Response--", responseText)
            }
            override fun onLinkAndroidDeviceIDError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("linkAndroidDeviceID ERROR--", errorText)
            }
        })
    }

    /**
     * Unlinks the related Android device identifier from the user's Avataryug account.
     */
    private fun UnLinkAndroidDevice()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UnlinkAndroidDeviceID())
        handler.unlinkAndroidDeviceID(object : UserAccountManagementHandler.UnlinkAndroidDeviceIDListener{
            override fun onUnlinkAndroidDeviceIDResult(result: UnlinkAndroidDeviceIDResult) {
                val responseText = "API Response: $result"
                Log.i("unlinkAndroidDeviceID Response--", responseText)
            }
            override fun onUnlinkAndroidDeviceIDError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("unlinkAndroidDeviceID ERROR--", errorText)
            }
        })
    }

    /**
     * Links the vendor-specific iOS device identifier to the user's Avataryug account.
     */
    private fun LinkIosDevice()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.LinkIOSDeviceID("ID0101hfh",true))
        handler.linkIOSDeviceID(object : UserAccountManagementHandler.LinkIOSDeviceIDListener{
            override fun onLinkIOSDeviceIDResult(result: LinkIOSDeviceIDResult)
            {
                val responseText = "API Response: $result"
                Log.i("linkIOSDeviceID Response--", responseText)
            }
            override fun onLinkIOSDeviceIDError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("linkIOSDeviceID ERROR--", errorText)
            }
        })
    }

    /**
     * Unlinks the related iOS device identifier from the user's Avataryug account.
     */
    private fun UnlinkIosDevice()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UnlinkIOSDeviceID())
        handler.unlinkIOSDeviceID(object : UserAccountManagementHandler.UnlinkIOSDeviceIDListener{
            override fun onUnlinkIOSDeviceIDResult(result: UnlinkIOSDeviceIDResult)
            {
                val responseText = "API unlinkIOSDeviceID Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onUnlinkIOSDeviceIDError(error: Exception)
            {
                val errorText = "API unlinkIOSDeviceID Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     *   /// Links the Apple account associated with the token to the user's Avataryug account.
     */
    private fun LinkApple()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.LinkApple("",true,""))
        handler.linkApple(object : UserAccountManagementHandler.LinkAppleListener{
            override fun onLinkAppleResult(result: LinkAppleResult)
            {
                val responseText = "API Response: $result"
                Log.i("LinkApple Response--", responseText)
            }
            override fun onLinkAppleError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("LinkApple ERROR--", errorText)
            }
        })
    }

    /**
     * Unlinks the related Apple account from the user's Avataryug account.
     */
    private fun UnLinkApple()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UnlinkApple())
        handler.unlinkApple(object : UserAccountManagementHandler.UnlinkAppleResultListener{
            override fun onUnlinkAppleResult(result: UnlinkAppleResult)
            {
                val responseText = "API Response: $result"
                Log.i("unlinkApple Response--", responseText)
            }

            override fun onUnlinkAppleError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("unlinkApple ERROR--", errorText)
            }
        })
    }

    /**
     * Links the currently signed-in user account to their Google account, using their Google account credentials
     */
    private fun LinkGoogle()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.LinkGoogleAccount("Kotlin GoogleSh ID 1010",true,"AUTHLIN 090"))
        handler.linkGoogleAccount(object : UserAccountManagementHandler.LinkGoogleAccountListener{
            override fun onLinkGoogleAccountResult(result: LinkGoogleAccountResult) {
                val responseText = "API Response: $result"
                Log.i("linkGoogleAccount Response--", responseText)
            }
            override fun onLinkGoogleAccountError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("linkGoogleAccount ERROR--", errorText)
            }
        })
    }

    /**
     * Unlinks the related Google account from the user's Avataryug account
     */
    private fun UnlinkGoogle()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UnlinkGoogleAccount())
        handler.unlinkGoogleAccount(object : UserAccountManagementHandler.OnUnlinkGoogleAccountResultListener{
            override fun onUnlinkGoogleAccountResult(result: UnlinkGoogleAccountResult) {
                val responseText = "API unlinkGoogleAccount Response: $result"
                Log.i(" Response--", responseText)
            }

            override fun onUnlinkGoogleAccountError(error: Exception) {
                val errorText = "API unlinkGoogleAccount Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Links the Facebook account associated with the provided Facebook access token to the user's Avataryug account.
     */
    private fun LinkFB()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.LinkFacebookAccount("ACESSTOKENFB248",true,"FBID-2385908"))
        handler.linkFacebookAccount(object : UserAccountManagementHandler.OnLinkFacebookAccountListener{
            override fun onLinkFacebookAccountResult(result: LinkFacebookAccountResult) {
                val responseText = "API linkFacebookAccount Response: $result"
                Log.i(" Response--", responseText)
            }

            override fun onLinkFacebookAccountError(error: Exception) {
                val errorText = "API linkFacebookAccount Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Unlinks the related Facebook account from the user's Avataryug account.
     */
    private fun UnlinkFB()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UnlinkFacebookAccount())
        handler.unlinkFacebookAccount(object : UserAccountManagementHandler.OnUnlinkFacebookAccountListener{
            override fun onUnlinkFacebookAccountResult(result: UnlinkFacebookAccountResult) {
                val responseText = "API unlinkFacebookAccount Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onUnlinkFacebookAccountError(error: Exception) {
                val errorText = "API unlinkFacebookAccount Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Adds the specified generic service identifier to the user's Avataryug account.
     * This is designed to allow for a Avataryug ID lookup of any arbitrary service identifier a Project wants to add.
     * This identifier should never be used as authentication credentials, as the intent is that it is easily accessible by other users.
     */
    private fun AddGenericService()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.AddGenericServiceID("Generic1010101"))
        handler.addGenericServiceID(object : UserAccountManagementHandler.OnAddGenericServiceIDListener
        {
            override fun onAddGenericServiceIDResult(result: AddGenericServiceIDResult) {
                val responseText = "API addGenericServiceID Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onAddGenericServiceIDError(error: Exception) {
                val errorText = "API addGenericServiceID Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Removes the generic service identifier from the user's Avataryug account.
     */
    private fun RemoveGenericService()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.RemoveGenericServiceID())
        handler.removeGenericServiceID(object : UserAccountManagementHandler.OnRemoveGenericServiceIDListener
        {
            override fun onRemoveGenericServiceIDResult(result: RemoveGenericServiceIDResult) {
                val responseText = "API removeGenericServiceID Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onRemoveGenericServiceIDError(error: Exception) {
                val errorText = "API removeGenericServiceID Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Updates the display name for the user in the project.
     */
    private fun updateProjectDisplayName()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UpdateUserProjectDisplayName("KOTLINER TEST"))
        handler.updateUserProjectDisplayName(object : UserAccountManagementHandler.OnUpdateUserProjectDisplayNameListener{
            override fun onUpdateUserProjectDisplayNameResult(result: UpdateUserProjectDisplayNameResult) {
                val responseText = "API updateUserProjectDisplayName Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onUpdateUserProjectDisplayNameError(error: Exception) {
                val errorText = "API updateUserProjectDisplayName Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Retrieves information about the user's account.
     */
    private fun GetUserAccountInfo()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.GetUserAccountInfo())
        handler.getUserAccountInfo(object : UserAccountManagementHandler.OnGetUserAccountInfoListener{
            override fun onGetUserAccountInfoResult(result: GetUserAccountInfoResult) {
                val responseText = "API getUserAccountInfo Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onGetUserAccountInfoError(error: Exception) {
                val errorText = "API getUserAccountInfo Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Updates user demographic information.
     */
    private fun UpdateUserDemographic()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UpdateUserDemographics("1","20-24","Asian"))
        handler.updateUserDemographics(object : UserAccountManagementHandler.OnUpdateUserDemographicsListener{
            override fun onUpdateUserDemographicsResult(result: UpdateUserDemographicsResult) {
                val responseText = "API updateUserProjectDisplayName Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onUpdateUserDemographicsError(error: Exception) {
                val errorText = "API updateUserProjectDisplayName Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Retrieves the user's public profile information.
     */
    private fun GetUserProfile()
    {
        val  accessHolder = ConfigurationApi()
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.GetUserProfile(accessHolder.getUserId()))
        handler.getUserProfile(object : UserAccountManagementHandler.OnGetUserProfileListener
        {
            override fun onGetUserProfileResult(result: GetUserProfileResult)
            {
                val responseText = "API getUserProfile Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onGetUserProfileError(error: Exception) {
                val errorText = "API getUserProfile Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }
        })
    }

    /**
     * Delete User Account from Avataryug platform.
     */
    private fun DeleteUsers()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.DeleteUser())
        handler.deleteUser(object : UserAccountManagementHandler.OnDeleteUserListener
        {
            override fun onDeleteUserResult(result: DeleteUserResult) {
                val responseText = "API deleteUser Response: $result"
                Log.i(" Response--", responseText)
            }

            override fun onDeleteUserError(error: Exception) {
                val errorText = "API deleteUser Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }

        })
    }

    /**
     * Sets the default avatar ID to users account.
     */
    private fun UpdateDefaultAvatarID()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.UpdateDefaultAvatarID("KotlinAvatar028"))
        handler.updateDefaultAvatarID(object : UserAccountManagementHandler.OnUpdateDefaultAvatarIDListener
        {
            override fun onUpdateDefaultAvatarIDResult(result: UpdateDefaultAvatarIDResult) {
                val responseText = "API updateDefaultAvatarID Response: $result"
                Log.i(" Response--", responseText)
            }
            override fun onUpdateDefaultAvatarIDError(error: Exception) {
                val errorText = "API updateDefaultAvatarID Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }

        })
    }

    /**
     * Allows users to change their password.
     */
    private fun Changepassword()
    {
        val handler = UserAccountManagementHandler(UserAccountManagementHandler.ChangePassword("123","12"))
        handler.changePassword(object : UserAccountManagementHandler.OnChangePasswordListener
        {
            override fun onChangePasswordResult(result: ChangePasswordResult?) {
                val responseText = "API changePassword Response: $result"
                Log.i(" Response--", responseText)
            }

            override fun onChangePasswordError(error: Exception) {
                val errorText = "API changePassword Error: ${error.message}"
                Log.e(" ERROR--", errorText)
            }

        })
    }

}