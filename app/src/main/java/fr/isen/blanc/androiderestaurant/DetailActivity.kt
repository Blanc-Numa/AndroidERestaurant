package fr.isen.blanc.androiderestaurant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        findViewById<TextView>(R.id.detailTitle).text = (intent.getSerializableExtra("dish") as Dish).name
    }
}