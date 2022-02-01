package fr.isen.blanc.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        startActivity(intent)
    }

    override fun onStop(){
        super.onStop()
        Log.d("HomeActivity", "vous stoppez la page home")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d("HomeActivity", "vous quittez la page home")
    }

}

