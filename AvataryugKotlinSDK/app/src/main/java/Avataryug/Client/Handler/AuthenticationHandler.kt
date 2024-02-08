package com.Avataryug.client.Handler

import android.util.Log
import com.Avataryug.client.Apis.AuthenticationApi
import com.Avataryug.client.Infrastructure.ClientException
import com.Avataryug.client.Infrastructure.ServerException
import com.Avataryug.client.Models.LoginWithAndroidDeviceIDRequest
import com.Avataryug.client.Models.LoginWithAndroidDeviceIDResult
import com.Avataryug.client.Models.LoginWithAppleRequest
import com.Avataryug.client.Models.LoginWithAppleResult
import com.Avataryug.client.Models.LoginWithCustomIDRequest
import com.Avataryug.client.Models.LoginWithCustomIDResult
import com.Avataryug.client.Models.LoginWithEmailAddressRequest
import com.Avataryug.client.Models.LoginWithEmailAddressResult
import com.Avataryug.client.Models.LoginWithFacebookRequest
import com.Avataryug.client.Models.LoginWithFacebookResult
import com.Avataryug.client.Models.LoginWithGoogleRequest
import com.Avataryug.client.Models.LoginWithGoogleResult
import com.Avataryug.client.Models.LoginWithIOSDeviceIDRequest
import com.Avataryug.client.Models.LoginWithIOSDeviceIDResult
import com.Avataryug.client.Models.RegisterUserRequest
import com.Avataryug.client.Models.RegisterUserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *  The "AuthenticateHandler" class facilitates authentication operations by utilizing the "Base" class for API calls.
 *  It offers multiple methods for various authentication scenarios,
 *  These methods accept callback parameters to handle response and error conditions.
 *  By leveraging the "baseApiCall" instance, the class ensures appropriate API calls are made and callbacks are invoked accordingly.
 */
class AuthenticationHandler(private val apiBase: Base)
{
    /**
     * Signs the user in using a custom unique identifier, which can be a combination of strings, integers, numbers, and symbols.
     * @param listener
     */
    fun loginWithCustomID(listener: LoginWithCustomIDListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is LoginWithCustomIDResult) {
                    listener.onLoginWithCustomIDResult(response)
                } else {
                    listener.onLoginWithCustomIDError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onLoginWithCustomIDError(error)
            }
        })
    }

    /**
     * Define the listener interface for the LoginWithCustomID method
     */
    interface LoginWithCustomIDListener
    {
        fun onLoginWithCustomIDResult(result: LoginWithCustomIDResult)
        fun onLoginWithCustomIDError(error: Exception)
    }

    /**
     * Signs the user in using a custom unique identifier, which can be a combination of strings, integers, numbers, and symbols,
     * creating a session identifier for subsequent API calls that require an authenticated user.
     */
    class LoginWithCustomID(private val customID: String, private val createAccount: Boolean) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener) {

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiInstance = AuthenticationApi()
                    val loginWithCustomIDRequest = LoginWithCustomIDRequest(customID, createAccount)
                    val result: LoginWithCustomIDResult =
                        apiInstance.loginWithCustomID(loginWithCustomIDRequest)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    Log.e("Login With Custom ID CLIENT EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                } catch (e: ServerException) {
                    Log.e("Login With Custom ID SERVER EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                } catch (e: Exception) {
                    Log.e("Login With Custom ID EXCEPTION", e.message ?: "", e)
                    listener.onError(e)
                }
            }
        }
    }

    /**
     *Signs the user in using the Android device identifier.
     * @param listener
     */
    fun loginWithAndroidDeviceID(listener: LoginWithAndroidDeviceIDListener) {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is LoginWithAndroidDeviceIDResult) {
                    listener.onLoginWithAndroidDeviceIDResult(response)
                } else {
                    listener.onLoginWithAndroidDeviceIDError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onLoginWithAndroidDeviceIDError(error)
            }
        })
    }

    /**
     * Define the listener interface for the loginWithAndroidDeviceID method
     */
    interface LoginWithAndroidDeviceIDListener
    {
        fun onLoginWithAndroidDeviceIDResult(result: LoginWithAndroidDeviceIDResult)
        fun onLoginWithAndroidDeviceIDError(error: Exception)
    }

    /**
     * Signs the user in using the Android device identifier,
     *  returning a session identifier that can subsequently be used for API calls which require an authenticated user.
     */
    class LoginWithAndroidDeviceID(private val androidDeviceID: String, private val createAccount: Boolean) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try {
                    val api = AuthenticationApi()
                    val loginWithAndroidDeviceIDRequest = LoginWithAndroidDeviceIDRequest(
                        androidDeviceID = androidDeviceID,
                        createAccount = createAccount
                    )
                    val result: LoginWithAndroidDeviceIDResult = api.loginWithAndroidDeviceID(loginWithAndroidDeviceIDRequest)
                    listener.onResult(result)
                } catch (e: ClientException)
                {
                    println("Exception when calling AuthenticationApi#LoginWithandroidIDs")
                    e.printStackTrace()
                } catch (e: ServerException)
                {
                    println("5xx response calling AuthenticationApi #loginWithAndroidDeviceID")
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     *  Register user with email id.
     * @param listener
     */
    fun registerUser(listener: RegisterUserListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is RegisterUserResult) {
                    listener.onRegisterUserResult(response)
                } else {
                    listener.onRegisterUserError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onRegisterUserError(error)
            }
        })
    }

    /**
     * Define the listener interface for the RegisterUser method.
     */
    interface RegisterUserListener
    {
        fun onRegisterUserResult(result: RegisterUserResult)
        fun onRegisterUserError(error: Exception)
    }

    /**
     * Register user with email id,
     * creating a session identifier for subsequent API calls that require an authenticated user.
     */
    class RegisterUser(private val addDisplayName: String,private val emailID: String,private val password: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val registerUserRequest = RegisterUserRequest(
                        emailID = emailID,
                        password = password,
                        displayName = addDisplayName
                    )
                    val api = AuthenticationApi()
                    val result: RegisterUserResult = api.registerUser(registerUserRequest)
                    listener.onResult(result)
                } catch (e: ClientException)
                {
                    println("Exception when calling AuthenticationApi #RegisterUser")
                    e.printStackTrace()
                } catch (e: ServerException)
                {
                    println("5xx response calling AuthenticationApi #RegisterUser")
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * Signs the user into the Avataryug account.
     * @param listener
     */
    fun loginWithEmailAddress(listener: OnLoginWithEmailAddressListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is LoginWithEmailAddressResult) {
                    listener.onLoginWithEmailAddressResult(response)
                } else {
                    listener.onLoginWithEmailAddressError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onLoginWithEmailAddressError(error)
            }
        })
    }

    /**
     * Define the listener interface for the LoginWithEmailAddress method.
     */
    interface OnLoginWithEmailAddressListener
    {
        fun onLoginWithEmailAddressResult(result: LoginWithEmailAddressResult)
        fun onLoginWithEmailAddressError(error: Exception)
    }

    /**
     * Signs the user into the Avataryug account, returning a session identifier that can subsequently be used for API calls which require an authenticated user.
     * Unlike most other login API calls, LoginWithEmailAddress does not permit the creation of new accounts via the CreateAccountFlag.
     */
    class LoginWithEmailAddress(private val emailID: String, private val password: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener) {
            // Use Retrofit to make the API call
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val request = LoginWithEmailAddressRequest(
                        emailID = emailID,
                        password = password,
                    )
                    val api = AuthenticationApi()
                    val result: LoginWithEmailAddressResult = api.loginWithEmailAddress(request)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    println("Exception when calling AuthenticationApi #LoginWithEmailAddress")
                    e.printStackTrace()
                } catch (e: ServerException) {
                    println("5xx response calling AuthenticationApi #LoginWithEmailAddress")
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * Signs in the user using an identity token obtained from Sign in with Apple.
     * @param listener
     */
    fun loginWithApple(listener: LoginWithAppleListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is LoginWithAppleResult) {
                    listener.onLoginWithAppleResult(response)
                } else {
                    listener.onLoginWithAppleError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onLoginWithAppleError(error)
            }
        })
    }

    /**
     * Define the listener interface for the LoginWithApple method.
     */
    interface LoginWithAppleListener
    {
        fun onLoginWithAppleResult(result: LoginWithAppleResult)
        fun onLoginWithAppleError(error: Exception)
    }

    /**
     * Signs in the user using an identity token obtained from Sign in with Apple,
     * returning a session identifier that can subsequently be used for API calls which require an authenticated user
     */
    class LoginWithApple(private val appleIdentityToken: String, private val createAccount: Boolean, private val appleID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener) {

            GlobalScope.launch(Dispatchers.IO)
            {
                try {
                    val loginWithAppleRequest = LoginWithAppleRequest(
                        appleID = appleID,
                        appleIdentityToken = appleIdentityToken,
                        createAccount = createAccount
                    )
                    val api = AuthenticationApi()
                    val result: LoginWithAppleResult = api.loginWithApple(loginWithAppleRequest)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    println("Exception when calling AuthenticationApi #LoginWithAppleResult")
                    e.printStackTrace()
                } catch (e: ServerException) {
                    println("5xx response calling AuthenticationApi #LoginWithAppleResult")
                    e.printStackTrace()
                }
            }

        }
    }

    /**
     * Signs the user in using the vendor-specific iOS device identifier.
     * @param listener
     */
    fun loginWithIOSDeviceID(listener: LoginWithIOSDeviceIDListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is LoginWithIOSDeviceIDResult) {
                    listener.onLoginWithIOSDeviceIDResult(response)
                } else {
                    listener.onLoginWithIOSDeviceIDError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception) {
                listener.onLoginWithIOSDeviceIDError(error)
            }
        })
    }

    /**
     * Define the listener interface for the loginWithIOSDeviceID method.
     */
    interface LoginWithIOSDeviceIDListener
    {
        fun onLoginWithIOSDeviceIDResult(result: LoginWithIOSDeviceIDResult)
        fun onLoginWithIOSDeviceIDError(error: Exception)
    }

    /**
     * Signs the user in using the vendor specific iOS device identifier,
     * returning a session identifier that can subsequently be used for API calls which require an authenticated user.
     */
    class LoginWithIOSDeviceID(private val iOSDeviceID: String, private val createAccount: Boolean) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener) {
            GlobalScope.launch(Dispatchers.IO)
            {
                try {
                    val req = LoginWithIOSDeviceIDRequest(
                        ioSDeviceID = iOSDeviceID,
                        createAccount = createAccount
                    )
                    val api = AuthenticationApi()
                    val result: LoginWithIOSDeviceIDResult = api.loginWithIOSDeviceID(req)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    println("Exception when calling AuthenticationApi #LoginWithAppleResult")
                    e.printStackTrace()
                } catch (e: ServerException) {
                    println("5xx response calling AuthenticationApi #LoginWithAppleResult")
                    e.printStackTrace()
                }
            }

        }
    }

    /**
     * Signs the user in using their Google account credentials.
     * @param listener
     */
    fun loginWithGoogle(listener: LoginWithGoogleListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is LoginWithGoogleResult) {
                    listener.onLoginWithGoogleResult(response)
                } else {
                    listener.onLoginWithGoogleError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception)
            {
                listener.onLoginWithGoogleError(error)
            }
        })
    }

    /**
     * Define the listener interface for the LoginWithGoogle method.
     */
    interface LoginWithGoogleListener
    {
        fun onLoginWithGoogleResult(result: LoginWithGoogleResult?)
        fun onLoginWithGoogleError(error: Exception)
    }

    /**
     *  Signs the user in using their Google account credentials,
     *  returning a session identifier that can subsequently be used for API calls which require an authenticated user
     */
    class LoginWithGoogle(private val googleServerAuthCode: String, private val createAccount: Boolean, private val googleID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener) {

            GlobalScope.launch(Dispatchers.IO)
            {
                try {
                    val loginWithGoogleRequest = LoginWithGoogleRequest(
                       googleID =  googleID,
                        googleServerAuthCode =  googleServerAuthCode,
                        createAccount =  createAccount)

                    val api = AuthenticationApi()
                    val result: LoginWithGoogleResult = api.loginWithGoogle(loginWithGoogleRequest)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    println("Exception when calling AuthenticationApi #LoginWithGoogleResult")
                    e.printStackTrace()
                } catch (e: ServerException) {
                    println("5xx response calling AuthenticationApi #LoginWithGoogleResult")
                    e.printStackTrace()
                }
            }


        }
    }

    /**
     * Signs the user in using a Facebook access token.
     * @param listener
     */
    fun loginWithFacebook(listener: LoginWithFacebookListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is LoginWithFacebookResult) {
                    listener.onLoginWithFacebookResult(response)
                } else {
                    listener.onLoginWithFacebookError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception)
            {
                listener.onLoginWithFacebookError(error)
            }
        })
    }

    /**
     * Define the listener interface for the LoginWithFacebook method.
     */
    interface LoginWithFacebookListener
    {
        fun onLoginWithFacebookResult(result: LoginWithFacebookResult)
        fun onLoginWithFacebookError(error: Exception)
    }

    /**
     *  Signs the user in using a Facebook access token,
     *  returning a session identifier that can subsequently be used for API calls which require an authenticated user
     */
    class LoginWithFacebook(private val fbAccessToken: String, private val createAccount: Boolean, private val facebookID: String) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try {
                    val loginWithFacebookRequest = LoginWithFacebookRequest(
                        facebookID = facebookID,
                        createAccount = createAccount,
                        fbAccessToken = fbAccessToken
                    )
                    val api = AuthenticationApi()
                    val result: LoginWithFacebookResult = api.loginWithFacebook(loginWithFacebookRequest)
                    listener.onResult(result)
                } catch (e: ClientException) {
                    println("Exception when calling AuthenticationApi #LoginWithFacebookResult")
                    e.printStackTrace()
                } catch (e: ServerException) {
                    println("5xx response calling AuthenticationApi #LoginWithFacebookResult")
                    e.printStackTrace()
                }
            }
        }
    }
}