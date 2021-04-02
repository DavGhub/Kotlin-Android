package com.davit.kotlin.fragments.ex2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.davit.kotlin.fragments.R

open class BaseFragment : Fragment() {
    protected lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.global_host_fragment)
    }
}