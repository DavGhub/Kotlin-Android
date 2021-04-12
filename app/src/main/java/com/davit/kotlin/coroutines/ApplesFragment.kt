package com.davit.kotlin.coroutines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.davit.kotlin.coroutines.databinding.FragmentApplesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ApplesFragment : Fragment() {

    private lateinit var binding:FragmentApplesBinding
    private val args:ApplesFragmentArgs by navArgs()
    private var initialNumber = 0
    private var maxNumber = 0
    private var currentNumber = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentApplesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            args.apples.also {
                initialNumber = it.initialNumber
                maxNumber = it.maxNumber
                currentNumber = it.initialNumber

                binding.currentNumber.text = initialNumber.toString()
        }

        binding.addApple.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                currentNumber++
                withContext(Dispatchers.Main){
                    binding.currentNumber.text = currentNumber.toString()
                    checkResetButtonVisibility()
                }
            }
        }

        binding.getApple.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                currentNumber--
                withContext(Dispatchers.Main) {
                    binding.currentNumber.text = currentNumber.toString()
                    checkResetButtonVisibility()
                }
            }
        }

        binding.reset.setOnClickListener {
            reset()
        }
    }


    private fun checkResetButtonVisibility(){

        binding.getApple.isEnabled = currentNumber != 0
        binding.addApple.isEnabled = currentNumber < maxNumber

        if(currentNumber == 0 || currentNumber == maxNumber){
            binding.reset.visibility = View.VISIBLE
        }else{
            binding.reset.visibility = View.GONE
        }
    }

    private fun reset(){
        currentNumber = initialNumber
        binding.currentNumber.text = initialNumber.toString()
        binding.reset.visibility = View.GONE
        binding.getApple.isEnabled = currentNumber != 0
        binding.addApple.isEnabled = currentNumber < maxNumber
    }
}