package Avataryug.Example.AvatarLoader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mykotlinandroidsdk.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AvataryugViewer : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avataryug_viewer)

        val backButton = findViewById<FloatingActionButton>(R.id.floatingActionButton3)
        backButton.setOnClickListener {
            val i = Intent(this@AvataryugViewer, Avataryug.Example.AvatarLoader.AvatarLoaderLogin::class.java)
            startActivity(i)
            Toast.makeText(baseContext, "Avatar Loader BACK", Toast.LENGTH_SHORT).show()
        }
    }
}