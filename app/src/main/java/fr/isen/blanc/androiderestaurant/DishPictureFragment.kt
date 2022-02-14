package fr.isen.blanc.androiderestaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.blanc.androiderestaurant.databinding.FragmentDishPictureBinding


class DishPictureFragment : Fragment() {
    private lateinit var binding: FragmentDishPictureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDishPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("picture_url")?.let {pictureURL ->
            Picasso.get()
                .load(if(pictureURL.isNotEmpty())pictureURL else null)
                .error(R.drawable.deadandroid)
                .placeholder(R.drawable.deadandroid)
                .into(binding.dishPictureFrag)
        }
    }
    companion object{
        fun newIstance(pictureUrl: String) =
            DishPictureFragment().apply {
                arguments = Bundle().apply {
                    putString("picture_url", pictureUrl)
                }
            }
    }
}