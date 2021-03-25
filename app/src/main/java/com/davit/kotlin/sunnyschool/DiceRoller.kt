package com.davit.kotlin.sunnyschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.davit.kotlin.sunnyschool.databinding.ActivityDiceRollerBinding

class DiceRoller : AppCompatActivity() {

    private lateinit var binding:ActivityDiceRollerBinding
    private val diceList:MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dice_roller)

        initDiceIconsList()

        binding.roll.setOnClickListener {
            binding.dice1.setImageResource(diceList.shuffled().take(1)[0])
            binding.dice2.setImageResource(diceList.shuffled().take(1)[0])
        }
    }

    private fun initDiceIconsList(){
        diceList.add(R.drawable.ic_dice1)
        diceList.add(R.drawable.ic_dice2)
        diceList.add(R.drawable.ic_dice3)
        diceList.add(R.drawable.ic_dice4)
        diceList.add(R.drawable.ic_dice5)
        diceList.add(R.drawable.ic_dice6)
    }
}