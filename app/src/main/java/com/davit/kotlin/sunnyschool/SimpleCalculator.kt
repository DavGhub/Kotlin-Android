package com.davit.kotlin.sunnyschool

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.davit.kotlin.sunnyschool.databinding.ActivitySimpleCalculatorBinding

class SimpleCalculator : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySimpleCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_calculator)

        binding.plus.setOnClickListener(this)
        binding.minus.setOnClickListener(this)
        binding.multiply.setOnClickListener(this)
        binding.divide.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        var a = 0.0
        var b = 0.0

        if (binding.firstNumberInput.text.isNotEmpty()) {
            a = binding.firstNumberInput.text.toString().toDouble()
        }

        if (binding.secondNumberInput.text.isNotEmpty()) {
            b = binding.secondNumberInput.text.toString().toDouble()
        }

        when (view?.id) {
            R.id.plus -> binding.result.text = (a + b).toString()
            R.id.minus -> binding.result.text = (a - b).toString()
            R.id.multiply -> binding.result.text = (a * b).toString()
            R.id.divide -> {
                if(b != 0.0){
                    binding.result.text = (a / b).toString()
                }else{
                    binding.result.text = getString(R.string.zero_error)
                }
            }
        }
    }
}