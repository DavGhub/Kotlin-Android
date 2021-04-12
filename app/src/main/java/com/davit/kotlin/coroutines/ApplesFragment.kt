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


            args.apples.let {
                initialNumber = it.initialNumber
                maxNumber = it.maxNumber
                currentNumber = it.initialNumber

                binding.currentNumber.text = initialNumber.toString()
        }

        binding.addApple.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                currentNumber++
                lifecycleScope.launch(Dispatchers.Main){
                    binding.currentNumber.text = currentNumber.toString()
                    checkResetButtonVisibility()
                }
            }
        }

        binding.getApple.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                currentNumber--
                lifecycleScope.launch(Dispatchers.Main) {
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
        if(currentNumber == 0 || currentNumber == maxNumber){
            binding.reset.visibility = View.VISIBLE
        }
    }

    private fun reset(){
        currentNumber = initialNumber
        binding.currentNumber.text = initialNumber.toString()
        binding.reset.visibility = View.GONE
    }
}