package com.davit.kotlin.retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.davit.kotlin.retrofit.models.UserModel

class UsersAdapter(private val context: Context, private val userList: MutableList<UserModel.User>) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.recyclerview_user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[holder.adapterPosition]
        holder.id.text = currentItem.id.toString()
        holder.firstName.text = currentItem.first_name
        holder.lastName.text = currentItem.last_name
        holder.email.text = currentItem.email
        Glide.with(context).load(currentItem.avatar)
            .error(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.avatar)
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.user_id)
        val firstName: TextView = view.findViewById(R.id.first_name)
        val lastName: TextView = view.findViewById(R.id.last_name)
        val email: TextView = view.findViewById(R.id.email)
        val avatar:ImageView = view.findViewById(R.id.avatar)
    }
}