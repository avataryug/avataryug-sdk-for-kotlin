package Avataryug.Example.QuickTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import Avataryug.Example.AvatarLoader.AvatarLoaderLogin
import com.example.mykotlinandroidsdk.R

class StartScreen : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        val QuickTestBtn = findViewById<Button>(R.id.QuickTestBtn)
        QuickTestBtn.setOnClickListener {
            Toast.makeText(baseContext, "Quick Test", Toast.LENGTH_SHORT).show()
            val i = Intent(this@StartScreen, MainActivity::class.java)
            startActivity(i)
        }

        val AvataLoaderBtn = findViewById<Button>(R.id.AvataLoaderBtn)
        AvataLoaderBtn.setOnClickListener {
            val i = Intent(this@StartScreen, Avataryug.Example.AvatarLoader.AvatarLoaderLogin::class.java)
            startActivity(i)
            Toast.makeText(baseContext, "Avatar Loader", Toast.LENGTH_SHORT).show()
        }
    }
}