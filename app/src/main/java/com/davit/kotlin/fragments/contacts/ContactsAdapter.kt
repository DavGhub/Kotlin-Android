package com.davit.kotlin.fragments.contacts

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

class ContactsAdapter(val context: Context, private val data:MutableList<Contact>) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private var contactClickListener:ContactClickListener? = null

    interface ContactClickListener{
        fun onContactClick(contact: Contact)
    }

    fun setOnContactClickListener(contactClickListener: ContactClickListener){
        this.contactClickListener = contactClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = data[position]
        holder.name.text = currentItem.name
        holder.number.text = currentItem.number
        if(currentItem.photoUrl != null){
            Glide.with(context)
                .load(Uri.parse(currentItem.photoUrl.toString()))
                .error(R.drawable.contact_default_icon)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.image)
        }

        holder.itemView.setOnClickListener {
            contactClickListener?.onContactClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ContactViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val name:TextView = view.findViewById<TextView>(R.id.contact_name)
        val number:TextView = view.findViewById<TextView>(R.id.contact_number)
        val image:ImageView = view.findViewById<ImageView>(R.id.contact_image)
    }

    fun addContact(item:Contact){
        data.add(0,item)
        notifyItemInserted(0)
    }

    fun removeContact(position: Int){
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}