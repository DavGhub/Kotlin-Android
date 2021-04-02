package com.davit.kotlin.fragments.ex1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.davit.kotlin.fragments.R
import com.davit.kotlin.fragments.databinding.FragmentResultsBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ResultsFragment : Fragment(), View.OnClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentResultsBinding
    private var player1Name: String = "Player 1"
    private var player2Name: String = "Player 2"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.e("ResultsFragment", "1: $param1 && 2: $param2")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultsBinding.inflate(inflater, container, false)

        binding.ticTacToe.setOnClickListener(this)
        binding.diceRoller.setOnClickListener(this)
        binding.setNames.setOnClickListener(this)

        binding.player1.text = getString(R.string.player1_result, "Player 1", 0)
        binding.player2.text = getString(R.string.player1_result, "Player 2", 0)


        setNamesFragmentResult()
        setTicTacToeResults()
        setDiceResults()

        return binding.root
    }

    private fun setNamesFragmentResult() {
        setFragmentResultListener("names") { _, _ ->
            getSavedNamesFromPreferences()
            binding.player1.text = getString(R.string.player1_result, player1Name, 0)
            binding.player2.text = getString(R.string.player2_result, player2Name, 0)

        }
    }

    private fun setTicTacToeResults(){
        setFragmentResultListener("tic_tac_result") { _, bundle ->
            getSavedNamesFromPreferences()

            binding.player1.text =
                getString(R.string.player1_result, player1Name, bundle.getInt("result1"))
            binding.player2.text =
                getString(R.string.player2_result, player2Name, bundle.getInt("result2"))
        }
    }

    private fun setDiceResults(){
        setFragmentResultListener("dice_result") { _, bundle ->
            getSavedNamesFromPreferences()

            binding.player1.text =
                getString(R.string.player1_result, player1Name, bundle.getInt("result1"))
            binding.player2.text =
                getString(R.string.player2_result, player2Name, bundle.getInt("result2"))
        }
    }

    private fun getSavedNamesFromPreferences() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("names", Context.MODE_PRIVATE)
        sharedPreferences.apply {
            player1Name = getString("player1", player1Name).toString()
            player2Name = getString("player2", player2Name).toString()

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tic_tac_toe -> runTransaction(
                TicTacToeFragment.newInstance(
                    player1Name,
                    player2Name
                ), "tic-tac"
            )
            R.id.dice_roller -> runTransaction(DiceRollerFragment.newInstance(player1Name,player2Name), "dice")
            R.id.set_names -> runTransaction(SetNamesFragment(), "names")
        }
    }

    private fun runTransaction(fragment: Fragment, name: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment, name)
            .addToBackStack(name)
            .commit()
    }
}