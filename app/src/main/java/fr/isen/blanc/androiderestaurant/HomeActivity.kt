package fr.isen.blanc.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import fr.isen.blanc.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val starterBtn =findViewById<Button>(R.id.starterBtn)
        val mainDishesBtn =findViewById<Button>(R.id.mainDishBtn)
        val desertBtn =findViewById<Button>(R.id.desertBtn)

        binding.starterBtn.setOnClickListener {

            changeActivity(getString(R.string.home_starters))
        }

        binding.mainDishBtn.setOnClickListener {
            changeActivity(getString(R.string.home_dishes))

        }




        binding.desertBtn.setOnClickListener {
            changeActivity(getString(R.string.home_desserts))
        }


    }
    private fun changeActivity( category: String) {
        val intent = Intent(this, DishActivity::class.java)
        intent.putExtra("category_type",category)
        Log.i("info","End of HomeActivity")
        startActivity(intent)
    }


}

