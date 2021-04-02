package com.davit.kotlin.fragments.ex1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.davit.kotlin.fragments.R
import com.davit.kotlin.fragments.databinding.FragmentDiceRollerBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DiceRollerFragment : Fragment() {
    private var player1Name: String? = "Player 1"
    private var player2Name: String? = "Player 2"
    private var firstPlayerIsPlaying = true
    private var isTheGameEnded = false
    private var firstPlayerWinCount = 0
    private var secondPlayerWinCount = 0
    private var firstPlayerRollResult = 0
    private var secondPlayerRollResult = 0

    private lateinit var binding: FragmentDiceRollerBinding
    private val diceList: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            player1Name = it.getString(ARG_PARAM1)
            player2Name = it.getString(ARG_PARAM2)
        }

        initDiceIconsList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentDiceRollerBinding.inflate(inflater, container, false)

        binding.currentPlayer.text = player1Name

        binding.roll.setOnClickListener {
            val firstImage = diceList.shuffled().take(1)[0]
            val secondImage = diceList.shuffled().take(1)[0]
            binding.dice1.setImageResource(firstImage)
            binding.dice2.setImageResource(secondImage)
            val dice1Result = diceList.indexOf(firstImage) + 1
            val dice2Result = diceList.indexOf(secondImage) + 1

            if (!isTheGameEnded) {
                if (firstPlayerIsPlaying) {
                    binding.currentPlayer.text = player2Name
                    firstPlayerRollResult = dice1Result + dice2Result
                    firstPlayerIsPlaying = false
                } else {
                    binding.currentPlayer.text = player1Name
                    secondPlayerRollResult = dice1Result + dice2Result
                    firstPlayerIsPlaying = true
                    isTheGameEnded = true
                }
            }

            if (isTheGameEnded) {
                if (firstPlayerRollResult > secondPlayerRollResult) {
                    firstPlayerWinCount++
                } else {
                    secondPlayerWinCount++
                }
                isTheGameEnded = false
            }
        }

        binding.endGame.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("result1",firstPlayerWinCount)
            bundle.putInt("result2",secondPlayerWinCount)
            setFragmentResult("dice_result", bundle)
            activity?.supportFragmentManager?.popBackStack()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiceRollerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun initDiceIconsList() {
        diceList.add(R.drawable.ic_dice1)
        diceList.add(R.drawable.ic_dice2)
        diceList.add(R.drawable.ic_dice3)
        diceList.add(R.drawable.ic_dice4)
        diceList.add(R.drawable.ic_dice5)
        diceList.add(R.drawable.ic_dice6)
    }
}