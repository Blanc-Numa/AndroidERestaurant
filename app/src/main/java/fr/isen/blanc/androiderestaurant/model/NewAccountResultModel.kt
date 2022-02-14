package fr.isen.blanc.androiderestaurant.model

import java.io.Serializable

data class NewAccountResultModel(val data : List<UserModel>): Serializable

data class UserModel(val id : Int)
