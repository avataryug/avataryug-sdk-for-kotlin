package Avataryug.Example.QuickTest

import Avataryug.Example.AvatarLoader.AvatarLoaderLogin
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidkotlinsdk.R

class StartScreen : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        val quickTestBtn = findViewById<Button>(R.id.QuickTestBtn)
        quickTestBtn.setOnClickListener {
            Toast.makeText(baseContext, "Quick Test", Toast.LENGTH_SHORT).show()
            val i = Intent(this@StartScreen, MainActivity::class.java)
            startActivity(i)
        }

        val avatarLoaderBtn = findViewById<Button>(R.id.AvataLoaderBtn)
        avatarLoaderBtn.setOnClickListener {
            val i = Intent(this@StartScreen, AvatarLoaderLogin::class.java)
            startActivity(i)
            Toast.makeText(baseContext, "Avatar Loader", Toast.LENGTH_SHORT).show()
        }
    }
}