package fr.isen.blanc.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import fr.isen.blanc.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.blanc.androiderestaurant.model.Basket
import fr.isen.blanc.androiderestaurant.model.ItemBasket
import java.io.File

class BasketActivity : MenuActivity() {
    private lateinit var binding : ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val file = File(cacheDir.absolutePath + "/basket.json")

        if (file.exists()) {
            val dishModel = GsonBuilder().create().fromJson(file.readText(), Basket::class.java)
            display(dishModel.item)
        }

        val quantity = getString(R.string.basketTotalQuantity) + this.getSharedPreferences(getString(R.string.app_prefs), Context.MODE_PRIVATE).getInt(getString(R.string.basket_count), 0).toString()
        binding.basketTotalQuantity.text = quantity

        val price = getString(R.string.totalPrice) + this.getSharedPreferences(getString(R.string.app_prefs), Context.MODE_PRIVATE).getFloat(getString(R.string.spTotalPrice), 0.0f).toString()
        binding.basketTotalPrice.text = price

        binding.basketButtonBuy.setOnClickListener {
            val userId = this.getSharedPreferences(getString(R.string.app_prefs), Context.MODE_PRIVATE).getInt(getString(R.string.spUserId), 0)

           if (userId == 0) {
                startActivity(Intent(this, LoginActivity::class.java))
            } /*else {
                startActivity(Intent(this, CommandActivity::class.java))
            }*/
        }

        binding.basketButtonDeleteAll.setOnClickListener {
            if (this.getSharedPreferences(getString(R.string.app_prefs), Context.MODE_PRIVATE).getInt(getString(R.string.basket_count), 0) != 0) {
                deleteBasketData()
                finish()
            }
        }
    }

    private fun display(dishesList : List<ItemBasket>) {
        val recyclerView = binding.basketList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BasketAdapter(dishesList) {
            val intent = Intent(this, DetailActivity::class.java).putExtra("dish", it)
            startActivity(intent)
        }
    }

    private fun deleteDishBasket(dish : ItemBasket) {
        File(cacheDir.absolutePath+"/inBacket.json").delete()
        val sharedPreferences = getSharedPreferences(getString(R.string.app_prefs), MODE_PRIVATE)
        sharedPreferences.edit().putInt(getString(R.string.basket_count), 0).apply()
        invalidateOptionsMenu()
    }

    private fun deleteBasketData() {
        File(cacheDir.absolutePath + "/basket.json").delete()
        this.getSharedPreferences(getString(R.string.app_prefs), Context.MODE_PRIVATE).edit().remove(getString(R.string.spTotalPrice)).apply()
        this.getSharedPreferences(getString(R.string.app_prefs), Context.MODE_PRIVATE).edit().remove(getString(R.string.basket_count)).apply()
        Toast.makeText(this, getString(R.string.basketDeleteAllTxt), Toast.LENGTH_SHORT).show()
    }

    private fun updateSharedPreferences(quantity: Int, price: Float) {
        val sharedPreferences = this.getSharedPreferences(getString(R.string.app_prefs), Context.MODE_PRIVATE)

        val oldQuantity = sharedPreferences.getInt(getString(R.string.basket_count), 0)
        val newQuantity = oldQuantity + quantity
        sharedPreferences.edit().putInt(getString(R.string.basket_count), newQuantity).apply()

        val oldPrice = sharedPreferences.getFloat(getString(R.string.spTotalPrice), 0.0f)
        val newPrice = oldPrice - price
        sharedPreferences.edit().putFloat(getString(R.string.spTotalPrice), newPrice).apply()
    }
}

