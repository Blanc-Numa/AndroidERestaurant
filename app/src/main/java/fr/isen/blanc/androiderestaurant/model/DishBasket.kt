package fr.isen.blanc.androiderestaurant.model

import fr.isen.blanc.androiderestaurant.DishModel
import java.io.Serializable

data class Basket(val item: MutableList<ItemBasket>): Serializable

data class ItemBasket(val dish: DishModel, var numberDish : Int): Serializable
