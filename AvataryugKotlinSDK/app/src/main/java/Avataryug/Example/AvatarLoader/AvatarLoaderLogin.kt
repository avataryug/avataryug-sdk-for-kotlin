package Avataryug.Example.AvatarLoader

import Avataryug.Example.QuickTest.StartScreen
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinandroidsdk.R
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
            val i = Intent(this@AvatarLoaderLogin, Avataryug.Example.AvatarLoader.AvataryugViewer::class.java)
            startActivity(i)
            Toast.makeText(baseContext, "Avatar Loader Login", Toast.LENGTH_SHORT).show()
        }
    }
}