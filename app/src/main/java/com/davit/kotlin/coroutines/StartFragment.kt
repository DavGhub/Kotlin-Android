package com.davit.kotlin.coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.davit.kotlin.coroutines.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private lateinit var binding:FragmentStartBinding
    private lateinit var navController: NavController

    private var isFirstInputFilled = false
    private var isSecondInputFilled = false
    private var initialNumber = 0
    private var maxNumber = 0


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(),R.id.main_host_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStartBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextInitialNumber.doOnTextChanged{ inputText, _, _, _ ->
            if(inputText.toString().isNotEmpty()){
                initialNumber = inputText.toString().toInt()
                isFirstInputFilled = true
            }
            checkForButtonVisibility()
        }

        binding.editTextMaxNumber.doOnTextChanged{ inputText, _, _, _ ->
            if(inputText.toString().isNotEmpty()){
                maxNumber = inputText.toString().toInt()
                isSecondInputFilled = true
            }
            checkForButtonVisibility()
        }

        binding.next.setOnClickListener {
            if(maxNumber < initialNumber){
                binding.editTextMaxNumber.error = getString(R.string.error_message)
            }else{
                val selectedNumbers = ApplesDefaultCounts(initialNumber,maxNumber)
                val action = StartFragmentDirections.actionStartFragmentToApplesFragment(selectedNumbers)
                navController.navigate(action)
            }
        }
    }






    private fun checkForButtonVisibility(){
        if(isFirstInputFilled && isSecondInputFilled){
            binding.next.isEnabled = true
        }
    }
}