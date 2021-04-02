package com.davit.kotlin.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class WithNavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_nav)
        val navController = Navigation.findNavController(this,R.id.global_host_fragment)
//        navController.navigate(R.id.resultsNavFragment)
    }
}