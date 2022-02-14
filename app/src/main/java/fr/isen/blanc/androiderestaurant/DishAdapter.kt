package fr.isen.blanc.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.blanc.androiderestaurant.databinding.CardViewDesignBinding


class DishAdapter(val dishes: List<DishModel>, val OnDishClicked: (DishModel) -> Unit): RecyclerView.Adapter<DishAdapter.DishViewHolder>() {
    class DishViewHolder(binding: CardViewDesignBinding):RecyclerView.ViewHolder(binding.root){
        val dishPicture = binding.dishPicture
        val dishName = binding.dishName
        val dishPrice = binding.dishPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding = CardViewDesignBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]
        Picasso.get()
            .load(if (dish.images[0].isNotEmpty()) dish.images[0] else null)
            .error(R.drawable.deadandroid)
            .placeholder(R.drawable.deadandroid)
            .resize(60,60)
            .into(holder.dishPicture)
        holder.dishName.text=dish.name_fr
        holder.dishPrice.text = dish.prices[0].price+"€"
        holder.itemView.setOnClickListener{
            OnDishClicked(dish)
        }
    }

    override fun getItemCount(): Int = dishes.size
}



