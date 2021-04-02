package com.davit.kotlin.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.davit.kotlin.fragments.databinding.ActivityWithoutNavBinding
import com.davit.kotlin.fragments.ex1.ResultsFragment

class WithoutNavActivity : AppCompatActivity(){

    private lateinit var binding:ActivityWithoutNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_without_nav)

        runTransaction()

    }

    private fun runTransaction(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view,ResultsFragment(),"results")
            .addToBackStack("results")
            .commit()
    }
}