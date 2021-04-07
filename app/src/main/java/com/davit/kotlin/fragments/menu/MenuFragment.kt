package com.davit.kotlin.fragments.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.davit.kotlin.fragments.R
import com.davit.kotlin.fragments.databinding.FragmentMenuBinding


class MenuFragment : Fragment(), MenuAdapter.MenuItemClickListener,
    MenuAdapter.BasketClickListener {

    private lateinit var binding:FragmentMenuBinding
    private lateinit var menuAdapter: MenuAdapter
    private val mealList:MutableList<Meal> = ArrayList()
    private val basketList:MutableList<Meal> = ArrayList()
    private lateinit var navController: NavController


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(),R.id.main_host_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        initMenuList()
        initAdapter(mealList)

        binding.addMeal.setOnClickListener {
            val newMeal = Meal("Խորոված","Վաղինակի մոտ","",5.0f)
            addMeal(newMeal)
            mealList.add(0,newMeal)
        }

        binding.removeMeal.setOnClickListener {
            removeMeal()
            mealList.removeAt(0)
        }

        binding.basketBtn.setOnClickListener {
            val basket = Basket(basketList)
            val action = MenuFragmentDirections.actionMenuFragmentToMealsBasketFragment(basket)
            navController.navigate(action)
        }

        return binding.root
    }

    private fun initAdapter(list:MutableList<Meal>){
        menuAdapter = MenuAdapter(requireActivity(),list)
        menuAdapter.setOnMealClickListener(this)
        menuAdapter.setOnBasketClickListener(this)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.menuRecyclerview.layoutManager = layoutManager
        binding.menuRecyclerview.adapter = menuAdapter
    }

    private fun initMenuList(){
        for(i in 1..35){
            mealList.add(Meal("Meal $i","Restaurant $i","url",3.7f))
        }
    }

    private fun addMeal(meal:Meal){
        menuAdapter.addMeal(meal)
        binding.menuRecyclerview.scrollToPosition(0)
    }

    private fun removeMeal(){
        menuAdapter.removeMeal(0)
    }

    override fun onMealClick(meal: Meal) {
        val action = MenuFragmentDirections.actionMenuFragmentToMealDetailsFragment(meal)
        navController.navigate(action)
    }

    override fun addInBasket(meal: Meal) {
        basketList.add(meal)
    }

    override fun removeFromBasket(position: Int) {
        basketList.removeAt(position)
    }
}