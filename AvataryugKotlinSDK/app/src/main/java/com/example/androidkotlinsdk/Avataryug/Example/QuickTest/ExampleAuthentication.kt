package Avataryug.Example.QuickTest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.Avataryug.client.ConfigurationApi
import com.Avataryug.client.Handler.AuthenticationHandler
import com.Avataryug.client.Infrastructure.ApiClient
import com.Avataryug.client.Models.LoginWithAndroidDeviceIDResult
import com.Avataryug.client.Models.LoginWithAppleResult
import com.Avataryug.client.Models.LoginWithCustomIDResult
import com.Avataryug.client.Models.LoginWithEmailAddressResult
import com.Avataryug.client.Models.LoginWithFacebookResult
import com.Avataryug.client.Models.LoginWithGoogleResult
import com.Avataryug.client.Models.LoginWithIOSDeviceIDResult
import com.Avataryug.client.Models.RegisterUserResult
import com.example.androidkotlinsdk.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * This ExampleAuthentication Class demonstrates API calling methods with temporary data.
 * A simple [Fragment] subclass.
 * Use the [ExampleAuthentication.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExampleAuthentication : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example_authentication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val backButton: FloatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        backButton.setOnClickListener {
            val i = Intent(view.getContext(), StartScreen::class.java)
            startActivity(i)
            Toast.makeText(view.getContext(), "BACK", Toast.LENGTH_SHORT).show()
        }

        val CustomIDBtn = view.findViewById<Button>(R.id.CustomIDBtn)
        CustomIDBtn.setOnClickListener {
            // Handle button click here
            LoginwithCustomID()
            Toast.makeText(requireContext(), "Custom Login  Clicked!", Toast.LENGTH_SHORT).show()
        }

        val androidDeviceIDButton = view.findViewById<Button>(R.id.AndroidIDBtn)
        androidDeviceIDButton.setOnClickListener {
            Toast.makeText(requireContext(),"Android Device ID Login..", Toast.LENGTH_LONG).show()
            LoginWithandroidIDs();
        }

        val newAccountBtn = view.findViewById<Button>(R.id.newAccountBtn)
        newAccountBtn.setOnClickListener {

            Toast.makeText(requireContext(),"Create new Account Login..", Toast.LENGTH_LONG).show()
            LoginwithCreateAccounts()
        }

        val loginEmailBtn = view.findViewById<Button>(R.id.loginEmailBtn)
        loginEmailBtn.setOnClickListener {

            Toast.makeText(requireContext(),"Email Login..", Toast.LENGTH_LONG).show()
            LoginwithEmail()

        }

        val loginWithAppleBtn = view.findViewById<Button>(R.id.LoginwithAppleBtn)
        loginWithAppleBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Apple Login..", Toast.LENGTH_LONG).show()
            LoginWithApple()
        }


        val iOSDeviceIDBtn = view.findViewById<Button>(R.id.IOSDeviceIDBtn)
        iOSDeviceIDBtn.setOnClickListener {
            Toast.makeText(requireContext(),"IOS Device ID Login..", Toast.LENGTH_LONG).show()
            LoginwithIOSDeviceID()
        }

        val LoginwithGoogleBtn = view.findViewById<Button>(R.id.LoginwithGoogleBtn)
        LoginwithGoogleBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Google SignIn Login..", Toast.LENGTH_LONG).show()
        }

        val loginFacebookBtn = view.findViewById<Button>(R.id.LoginwithFacebookBtnn)
        loginFacebookBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Facebook Login..", Toast.LENGTH_LONG).show()
        }

    }

    /**
     * Signs the user in using a custom unique identifier, which can be a combination of strings, integers, numbers, and symbols,
     * creating a session identifier for subsequent API calls that require an authenticated use
     */
    fun LoginwithCustomID()
    {
        val handler = AuthenticationHandler(AuthenticationHandler.LoginWithCustomID("KotlinCustomID",true))
        handler.loginWithCustomID(object : AuthenticationHandler.LoginWithCustomIDListener{
            override fun onLoginWithCustomIDResult(result: LoginWithCustomIDResult) {
                val responseText = "API Response: $result"
                Log.i("LoginwithCustomID Response--", responseText)
                val  accessHolder = ConfigurationApi()
                accessHolder.setAccessToken(result.data!!.accessToken.toString())

                val client = ApiClient
                client.accessToken = result.data.accessToken
                accessHolder.setUserId(result.data.user!!.userID.toString())
            }
            override fun onLoginWithCustomIDError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("LoginwithCustomID ERROR--", errorText)
            }
        })
    }

    /**
     * Signs the user in using the Android device identifier,
     * returning a session identifier that can subsequently be used for API calls which require an authenticated user.
     */
    private fun LoginWithandroidIDs()
    {
        val handler = AuthenticationHandler(AuthenticationHandler.LoginWithAndroidDeviceID("KOTLINANDROID03030",true))
        handler.loginWithAndroidDeviceID(object : AuthenticationHandler.LoginWithAndroidDeviceIDListener{
            override fun onLoginWithAndroidDeviceIDResult(result: LoginWithAndroidDeviceIDResult) {
                val responseText = "API Response: $result"
                Log.i("loginWithAndroidDeviceID Response--", responseText)
                val  accessHolder = ConfigurationApi()
                accessHolder.setAccessToken(result.data!!.accessToken.toString())

                val client = ApiClient
                client.accessToken = result.data.accessToken
                accessHolder.setUserId(result.data.user!!.userID.toString())
            }

            override fun onLoginWithAndroidDeviceIDError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("loginWithAndroidDeviceID ERROR--", errorText)
            }
        })
    }

    /**
     * Register user with email id,
     * creating a session identifier for subsequent API calls that require an authenticated user.
     */
    private fun LoginwithCreateAccounts()
    {
        val handler = AuthenticationHandler(AuthenticationHandler.RegisterUser("KOTLINSDKTEST","selej10756@quipas.com","12"))
        handler.registerUser(object : AuthenticationHandler.RegisterUserListener{
            override fun onRegisterUserResult(result: RegisterUserResult) {
                val responseText = "API Response: $result"
                Log.i("LoginwithCreateAccounts Response--", responseText)
                val  accessHolder = ConfigurationApi()
                accessHolder.setAccessToken(result.data!!.accessToken.toString())

                val client = ApiClient
                client.accessToken = result.data.accessToken
                accessHolder.setUserId(result.data.user!!.userID.toString())
            }

            override fun onRegisterUserError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("LoginwithCreateAccounts ERROR--", errorText)
            }

        })
    }

    /**
     * Signs the user into the Avataryug account, returning a session identifier that can subsequently be used for API calls which require an authenticated user.
     * Unlike most other login API calls, LoginWithEmailAddress does not permit the creation of new accounts via the CreateAccountFlag.
     */
    private fun LoginwithEmail()
    {
        val handler = AuthenticationHandler(AuthenticationHandler.LoginWithEmailAddress("selej10756@quipas.com","12"))
        handler.loginWithEmailAddress(object : AuthenticationHandler.OnLoginWithEmailAddressListener{
            override fun onLoginWithEmailAddressResult(result: LoginWithEmailAddressResult) {
                val responseText = "API Response: $result"
                Log.i("loginWithEmailAddress Response--", responseText)
                val  accessHolder = ConfigurationApi()
                accessHolder.setAccessToken(result.data!!.accessToken.toString())

                val client = ApiClient
                client.accessToken = result.data.accessToken
                accessHolder.setUserId(result.data.user!!.userID.toString())
            }

            override fun onLoginWithEmailAddressError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("loginWithEmailAddress ERROR--", errorText)
            }

        })
    }

    /**
     * Signs in the user using an identity token obtained from Sign in with Apple,
     * returning a session identifier that can subsequently be used for API calls which require an authenticated user.
     */
    private fun LoginWithApple()
    {
        val handler = AuthenticationHandler(AuthenticationHandler.LoginWithApple("AppleKotlinToken123",true,"KoltinAppleID123"))
        handler.loginWithApple(object : AuthenticationHandler.LoginWithAppleListener{
            override fun onLoginWithAppleResult(result: LoginWithAppleResult) {
                val responseText = "API Response: $result"
                Log.i("loginWithApple Response--", responseText)
                val  accessHolder = ConfigurationApi()
                accessHolder.setAccessToken(result.data!!.accessToken.toString())

                val client = ApiClient
                client.accessToken = result.data.accessToken
                accessHolder.setUserId(result.data.user!!.userID.toString())
            }

            override fun onLoginWithAppleError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("loginWithApple ERROR--", errorText)
            }

        })
    }

    /**
     * Signs the user in using the vendor-specific iOS device identifier,
     * returning a session identifier that can subsequently be used for API calls which require an authenticated user
     */
    private fun LoginwithIOSDeviceID()
    {
        val handler = AuthenticationHandler(AuthenticationHandler.LoginWithIOSDeviceID("IOS KOTLIN SOI1019",true))
        handler.loginWithIOSDeviceID(object : AuthenticationHandler.LoginWithIOSDeviceIDListener{
            override fun onLoginWithIOSDeviceIDResult(result: LoginWithIOSDeviceIDResult) {
                val responseText = "API Response: $result"
                Log.i("loginWithIOSDeviceID Response--", responseText)
                val  accessHolder = ConfigurationApi()
                accessHolder.setAccessToken(result.data!!.accessToken.toString())

                val client = ApiClient
                client.accessToken = result.data.accessToken
                accessHolder.setUserId(result.data.user!!.userID.toString())
            }
            override fun onLoginWithIOSDeviceIDError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("loginWithIOSDeviceID ERROR--", errorText)
            }

        })
    }

    /**
     * Signs the user in using their Google account credentials,
     * returning a session identifier that can subsequently be used for API calls which require an authenticated user.
     */
}