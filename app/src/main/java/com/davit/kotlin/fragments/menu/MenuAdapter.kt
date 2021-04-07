package com.davit.kotlin.fragments.menu

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.davit.kotlin.fragments.R

class MenuAdapter(val context: Context, private val data: MutableList<Meal>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    private var menuItemClickListener: MenuItemClickListener? = null
    private var basketClickListener: BasketClickListener? = null


    interface BasketClickListener {
        fun addInBasket(meal: Meal)
        fun removeFromBasket(position: Int)
    }

    interface MenuItemClickListener {
        fun onMealClick(meal: Meal)
    }

    fun setOnMealClickListener(menuItemClickListener: MenuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener
    }

    fun setOnBasketClickListener(basketClickListener: BasketClickListener){
        this.basketClickListener = basketClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = data[position]
        holder.name.text = currentItem.name
        holder.restaurantName.text = currentItem.restaurantName
        holder.rating.text = currentItem.rating.toString()
        Glide.with(context)
            .load(Uri.parse(currentItem.iconUrl))
            .error(R.drawable.ic_fastfood)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.mealImage)

        holder.itemView.setOnClickListener {
            menuItemClickListener?.onMealClick(currentItem)
        }

        holder.favorite.setOnClickListener {
            if(holder.favorite.drawable.constantState == ContextCompat.getDrawable(context,R.drawable.favorite_border)?.constantState){
                holder.favorite.setImageResource(R.drawable.favorite_filled)
            }else{
                holder.favorite.setImageResource(R.drawable.favorite_border)
            }
        }

        holder.basket.setOnClickListener {
            if(holder.basket.drawable.constantState == ContextCompat.getDrawable(context,R.drawable.add_shopping_cart)?.constantState){
                holder.basket.setImageResource(R.drawable.add_shopping_cart_passive)
                basketClickListener?.addInBasket(currentItem)
            }else{
                holder.basket.setImageResource(R.drawable.add_shopping_cart)
                basketClickListener?.removeFromBasket(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.meal_name)
        val restaurantName: TextView = view.findViewById(R.id.restaurant_name)
        val mealImage: ImageView = view.findViewById(R.id.meal_image)
        val favorite: ImageView = view.findViewById(R.id.favorite)
        val basket: ImageView = view.findViewById(R.id.basket)
        val rating: TextView = view.findViewById(R.id.meal_rating)
        val ratingCount: TextView = view.findViewById(R.id.meal_rating_count)
    }

    fun addMeal(item: Meal) {
        data.add(0, item)
        notifyItemInserted(0)
    }

    fun removeMeal(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}