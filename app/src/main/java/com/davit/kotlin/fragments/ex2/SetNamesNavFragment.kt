package com.davit.kotlin.fragments.ex2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.davit.kotlin.fragments.R
import com.davit.kotlin.fragments.databinding.FragmentSetNamesBinding


class SetNamesNavFragment : BaseFragment(), View.OnClickListener {
    private lateinit var binding: FragmentSetNamesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSetNamesBinding.inflate(inflater, container, false)

        binding.click = this

        return binding.root
    }


    override fun onClick(view: View?) {
        if(view?.id == R.id.save){
            val name1 = binding.firstInput.text.toString()
            val name2 = binding.secondInput.text.toString()
            if(name1.isNotEmpty() && name2.isNotEmpty()){
                setFragmentResult("names", bundleOf())
                val sharedPreferences = requireActivity().getSharedPreferences("names", Context.MODE_PRIVATE)
                sharedPreferences.edit().apply {
                    putString("player1", name1)
                    putString("player2", name2)
                }.apply()
                navController.navigateUp()
            }
        }
    }
}