package fr.isen.blanc.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val text = "Bienvenue"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()


        val entreeButton =findViewById<Button>(R.id.starterBtn)
        entreeButton.setOnClickListener {

            val text = "Menu des entrées"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()

            val Intent = Intent(this,starter::class.java)
            startActivity(Intent)
        }
    }
}

