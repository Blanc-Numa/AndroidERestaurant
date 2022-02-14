package fr.isen.blanc.androiderestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import com.google.gson.GsonBuilder
import fr.isen.blanc.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.blanc.androiderestaurant.model.Basket
import fr.isen.blanc.androiderestaurant.model.ItemBasket
import java.io.File


class DetailActivity : MenuActivity() {

    private lateinit var binding: ActivityDetailBinding
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dish = (intent.getSerializableExtra("dish") as DishModel)
        binding.detailTitle.text = dish.name_fr
        var ingredientStr = dish.ingredients.joinToString(", "){it.name_fr}
        binding.dishIngredient.text = ingredientStr
        binding.dishPhotoPager.adapter = DishPictureAdapter(this,dish.images)

        binding.numberTotal.text = "1"
        var numberDish =  1
        binding.totalPrice.text = "Ajouter au panier : " + (dish.prices[0].price.toFloat() * numberDish ) + "€"

        //add
        binding.dishMoreButton.setOnClickListener{
            numberDish += 1
            binding.numberTotal.text = "" + numberDish
            binding.totalPrice.text = "Ajouter au panier : " + (dish.prices[0].price.toFloat() * numberDish )+ "€"

        }
        //minus
        binding.dishLessButton.setOnClickListener{
            if (numberDish > 0) {
                numberDish -= 1
                binding.numberTotal.text = "" + numberDish
                binding.totalPrice.text = "Ajouter au panier : " + (dish.prices[0].price.toFloat() * numberDish)+ "€"
            }

        }
        binding.command.setOnClickListener{
            var jsonFile = File(cacheDir.absolutePath+"/inBacket.json")

            if(jsonFile.exists()){

                // read file + deserializer
                var deserializingFile =GsonBuilder().create().fromJson(jsonFile.readText(), Basket::class.java)

                deserializingFile.item.firstOrNull { it.dish == dish }?.let {
                    it.numberDish += numberDish
                } ?: run {
                    deserializingFile.item.add(ItemBasket(dish, numberDish))
                }
                //reserialize
                saveInMemory(deserializingFile, jsonFile)
                saveDishPrice(dish.prices[0].price.toFloat() * numberDish)

            }else{
                val basket = Basket(mutableListOf(ItemBasket(dish, numberDish)))
                saveInMemory(basket,jsonFile)
                saveDishPrice(dish.prices[0].price.toFloat() * numberDish)
            }



            val text = "Ajouté au panier"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

    }

    private fun saveInMemory(basket: Basket, file: File) {
        saveDishCount(basket)
        file.writeText(GsonBuilder().create().toJson(basket))
    }



    fun saveDishCount(basket: Basket) {
        val count = basket.item.sumOf { it.numberDish }

        val sharedPreferences = getSharedPreferences(getString(R.string.app_prefs), MODE_PRIVATE)
        sharedPreferences.edit().putInt(getString(R.string.basket_count), count).apply()
        invalidateOptionsMenu()
    }

    var priceTotal = 0.0
    fun saveDishPrice(price : Float){

        priceTotal += price
        val sharedPreferences = getSharedPreferences(getString(R.string.app_prefs), MODE_PRIVATE)
        sharedPreferences.edit().putFloat(getString(R.string.spTotalPrice), priceTotal.toFloat()).apply()
        invalidateOptionsMenu()

    }


}
