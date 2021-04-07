package com.davit.kotlin.fragments.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.davit.kotlin.fragments.R
import com.davit.kotlin.fragments.databinding.FragmentMealsBasketBinding


class MealsBasketFragment : Fragment(), BasketAdapter.BasketItemClickListener {

    private lateinit var binding:FragmentMealsBasketBinding
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var navController: NavController
    private val args:MealsBasketFragmentArgs by navArgs()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(),R.id.main_host_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

       binding = FragmentMealsBasketBinding.inflate(inflater, container, false)

        val basketList = args.mealList?.mealList
        if(basketList != null){
            initAdapter(basketList)
        }

        return binding.root
    }

    private fun initAdapter(list:MutableList<Meal>){
        basketAdapter = BasketAdapter(requireActivity(),list)
        basketAdapter.setOnBasketItemClickListener(this)
        val layoutManager = GridLayoutManager(requireActivity(),3)
        binding.favoriteMealsRecyclerview.layoutManager = layoutManager
        binding.favoriteMealsRecyclerview.adapter = basketAdapter
    }

    override fun onMealClick(meal: Meal) {
        val action = MealsBasketFragmentDirections.actionMealsBasketFragmentToMealDetailsFragment(meal)
        navController.navigate(action)
    }
}