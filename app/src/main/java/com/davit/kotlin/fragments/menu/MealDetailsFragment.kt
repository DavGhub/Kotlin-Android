package com.davit.kotlin.fragments.menu

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.davit.kotlin.fragments.R
import com.davit.kotlin.fragments.databinding.FragmentMealDetailsBinding


class MealDetailsFragment : Fragment() {

    private lateinit var binding:FragmentMealDetailsBinding
    private val args:MealDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false)

        if(args.meal?.iconUrl != null){
            Glide.with(this)
                .load(Uri.parse(args.meal?.iconUrl))
                .error(R.drawable.ic_fastfood)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.detailsMealImage)
        }
        binding.detailsMealName.text = args.meal?.name
        binding.detailsRestaurantName.text = args.meal?.restaurantName
        binding.mealRating.text = args.meal?.rating.toString()

        return binding.root
    }
}