package fr.isen.blanc.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.blanc.androiderestaurant.databinding.ViewBasketBinding
import fr.isen.blanc.androiderestaurant.model.ItemBasket

class BasketAdapter(private val baskets: List<ItemBasket>, private val onBasketClick: (ItemBasket) -> Unit) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(
            ViewBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val basket = baskets[position]

        holder.name.text = basket.dish.name_fr
        val price = "Total : ${basket.dish.prices[0].price.toFloat() * basket.numberDish} €"
        holder.price.text = price

        val quantity = "Quantité : ${basket.numberDish}"
        holder.quantity.text = quantity

        holder.delete.setOnClickListener {
            onBasketClick(basket)
        }
    }

    override fun getItemCount(): Int {
        return baskets.size
    }

    class BasketViewHolder(binding : ViewBasketBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.basketCellTitle
        val price: TextView = binding.basketCellPrice
        val quantity: TextView = binding.basketCellQuantity
        val delete: ImageView = binding.basketCellIconDelete
    }
}
