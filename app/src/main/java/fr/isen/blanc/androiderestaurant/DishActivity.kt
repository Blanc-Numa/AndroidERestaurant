package fr.isen.blanc.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.blanc.androiderestaurant.databinding.ActivityDishBinding

class DishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainDishTitle.text = intent.getStringExtra("category_type")
    }
}