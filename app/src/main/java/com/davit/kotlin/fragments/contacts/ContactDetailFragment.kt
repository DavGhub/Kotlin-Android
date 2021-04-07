package com.davit.kotlin.fragments.contacts

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
import com.davit.kotlin.fragments.databinding.FragmentContactDetailBinding


class ContactDetailFragment : Fragment() {

    private lateinit var binding:FragmentContactDetailBinding
    private val args: ContactDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentContactDetailBinding.inflate(inflater,container,false)

        if(args.contact?.photoUrl != null){
            Glide.with(this)
                .load(Uri.parse(args.contact?.photoUrl))
                .error(R.drawable.contact_default_icon)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.detailsContactImage)
        }
        binding.detailsContactName.text = args.contact?.name
        binding.detailsContactNumber.text = args.contact?.number

        return binding.root
    }
}