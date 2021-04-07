package com.davit.kotlin.fragments.menu

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.davit.kotlin.fragments.R

class BasketAdapter(val context: Context, private val data: MutableList<Meal>) :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>()  {

    private var basketItemClickListener: BasketItemClickListener? = null


    interface BasketItemClickListener {
        fun onMealClick(meal: Meal)
    }

    fun setOnBasketItemClickListener(basketItemClickListener: BasketItemClickListener){
        this.basketItemClickListener = basketItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.basket_item, parent, false)
        return BasketAdapter.BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val currentItem = data[position]
        holder.name.text = currentItem.name
        holder.rating.text = currentItem.rating.toString()
        Glide.with(context)
            .load(Uri.parse(currentItem.iconUrl))
            .error(R.drawable.ic_fastfood)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.mealImage)

        holder.itemView.setOnClickListener {
            basketItemClickListener?.onMealClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class BasketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.basket_meal_name)
        val mealImage: ImageView = view.findViewById(R.id.basket_meal_image)
        val rating: TextView = view.findViewById(R.id.basket_meal_rating)
    }
}