package fr.isen.blanc.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.blanc.androiderestaurant.databinding.ActivityDishBinding
import org.json.JSONObject

class DishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryType = intent.getStringExtra("category_type") ?:""
        binding.mainDishTitle.text = categoryType
        loadDishesFromCategory(categoryType)

    }

    private fun loadDishesFromCategory(Category: String){
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject, { response ->
                val dishResult= Gson().fromJson(response.toString(), DishResult::class.java )
                displayDishes(dishResult.data.firstOrNull{it.name_fr == Category}?.items ?: listOf())
            }, {
                Log.e("DishActivity", "erreur lors de la récupération de la liste des plats")
            })
        Volley.newRequestQueue(this).add(jsonRequest)
    }
    private fun displayDishes(dishes: List<DishModel>) {
        binding.maincourseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.maincourseRecyclerView.adapter = DishAdapter(dishes) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish", it)
            startActivity(intent)
        }
    }

}
