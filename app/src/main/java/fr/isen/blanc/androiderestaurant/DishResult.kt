package fr.isen.blanc.androiderestaurant

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DishResult (val data: List<Categorymodel>): Serializable

data class Categorymodel (val name_fr: String, val items: List<DishModel>)
data class DishModel(
    val name_fr: String,
    @SerializedName("images") val pictures: List<String>,
    val prices: List<Price>
    ) : Serializable{
        fun getFirstPicture() = pictures[0]
        fun getFormattedPrice() = prices[0].price + "€"
    }

data class Price(val price: String): Serializable


