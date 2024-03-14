package Avataryug.Example.AvatarLoader

import Avataryug.Class.AvataryugData
import Avataryug.Example.QuickTest.StartScreen
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Avataryug.client.ConfigurationApi
import com.Avataryug.client.Handler.AuthenticationHandler
import com.Avataryug.client.Infrastructure.ApiClient
import com.Avataryug.client.Models.LoginWithCustomIDResult
import com.example.androidkotlinsdk.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
class AvatarLoaderLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar_loader_login)

        val backButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        backButton.setOnClickListener {
            val i = Intent(this@AvatarLoaderLogin, StartScreen::class.java)
            startActivity(i)
            Toast.makeText(baseContext, "Avatar Loader BACK", Toast.LENGTH_SHORT).show()
        }

        val LoginBtn = findViewById<Button>(R.id.loginBtn)
        LoginBtn.setOnClickListener {
            Toast.makeText(baseContext, "Avatar Loader Login", Toast.LENGTH_SHORT).show()
            LoginwithCustomID()
        }
    }

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

                //DataHolder.Instance.isCreateCustom = true
                //DataHolder.Instance.currentSelectedCustomizePanel = "Head"
                val i = Intent(this@AvatarLoaderLogin, AvataryugViewer::class.java)
                startActivity(i)
            }
            override fun onLoginWithCustomIDError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("LoginwithCustomID ERROR--", errorText)
            }
        })
    }
}