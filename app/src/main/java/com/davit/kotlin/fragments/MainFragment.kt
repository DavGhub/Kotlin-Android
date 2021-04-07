package com.davit.kotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.davit.kotlin.fragments.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding:FragmentMainBinding
    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(),R.id.main_host_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.goToContacts.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_contactsFragment)
        }

        binding.goToMenu.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_menuFragment)
        }

        return binding.root
    }
}