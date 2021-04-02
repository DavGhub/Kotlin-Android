package com.davit.kotlin.fragments.ex2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import com.davit.kotlin.fragments.R
import com.davit.kotlin.fragments.databinding.FragmentTicTacToeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TicTacToeNavFragment : BaseFragment(), View.OnClickListener {
    private var player1Name: String = "Player 1"
    private var player2Name: String = "Player 2"

    private lateinit var binding:FragmentTicTacToeBinding
    private var player1IsPlaying = true
    private val gameCurrentState: MutableMap<Int, String> = mutableMapOf()
    private val winningVersions = WinningVersions()
    private var winnerName = ""
    private var p1WinCount = 0
    private var p2WinCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            player1Name = getString(ARG_PARAM1).toString()
            player2Name = getString(ARG_PARAM2).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTicTacToeBinding.inflate(inflater,container,false)

        binding.image1.setOnClickListener(this)
        binding.image2.setOnClickListener(this)
        binding.image3.setOnClickListener(this)
        binding.image4.setOnClickListener(this)
        binding.image5.setOnClickListener(this)
        binding.image6.setOnClickListener(this)
        binding.image7.setOnClickListener(this)
        binding.image8.setOnClickListener(this)
        binding.image9.setOnClickListener(this)
        binding.newGame.setOnClickListener(this)
        binding.endGame.setOnClickListener(this)

        binding.playersNames.text = getString(R.string.players_with_names,player1Name,player2Name)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TicTacToeNavFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {
        when(view){
            is ImageView -> checkClickedField(view)
            else -> {
                when(view?.id){
                    R.id.new_game -> newGame()
                    R.id.end_game -> {
                        val bundle = Bundle()
                        bundle.putInt("result1",p1WinCount)
                        bundle.putInt("result2",p2WinCount)
                        setFragmentResult("tic_tac_result", bundle)
                        navController.navigateUp()
                    }
                }
            }
        }
    }

    private fun checkClickedField(imageView: ImageView){
        if(imageView.isEnabled){

            if(player1IsPlaying){
                imageView.setImageResource(R.drawable.ic_cross)
                binding.currentPlayer.text = player2Name
                gameCurrentState[identifyClickedField(imageView)] = "X"
                player1IsPlaying = false
            }else{
                imageView.setImageResource(R.drawable.ic_circle)
                binding.currentPlayer.text = player1Name
                gameCurrentState[identifyClickedField(imageView)] = "O"
                player1IsPlaying = true
            }

            imageView.isEnabled = false
            if(gameCurrentState.size > 4){
                if(isThereAWinner(gameCurrentState,winningVersions.versionsList)){
                    stopGame()
                }
            }

            if(gameCurrentState.size == 9 && !isThereAWinner(gameCurrentState,winningVersions.versionsList)){
                binding.currentPlayer.text = "No one won"
            }
        }else{
            Toast.makeText(requireActivity(),"Are you blind?))", Toast.LENGTH_SHORT).show()
        }
    }

    private fun newGame(){
        for (i in 0..binding.parent.childCount){
            val childView = binding.parent.getChildAt(i)
            if(childView is ImageView){
                childView.setImageDrawable(null)
                childView.isEnabled = true
            }
        }
        binding.currentPlayer.text = player1Name
        player1IsPlaying = true
        gameCurrentState.clear()
    }

    private fun stopGame(){
        for (i in 0..binding.parent.childCount){
            val childView = binding.parent.getChildAt(i)
            if(childView is ImageView){
                childView.isEnabled = false
            }
        }
        binding.currentPlayer.text = "The winner is $winnerName"
        player1IsPlaying = true
        Toast.makeText(requireContext(),"Game finished", Toast.LENGTH_SHORT).show()
    }

    private fun isThereAWinner(map: MutableMap<Int, String>, versionList: List<Map<Int, String>>): Boolean {

        for (version in versionList.indices) {
            if (map.entries.containsAll(versionList[version].entries)) {
                println("Winner pattern: ${versionList[version]}")
                winnerName = if(versionList[version].containsValue("X")){
                    p1WinCount++
                    player1Name
                }else{
                    p2WinCount++
                    player2Name
                }
                return true
            }
        }

        return false
    }

    private fun identifyClickedField(field: ImageView):Int{
        return when(field.id){
            R.id.image1 -> 4
            R.id.image2 -> 5
            R.id.image3 -> 6
            R.id.image4 -> 1
            R.id.image5 -> 2
            R.id.image6 -> 3
            R.id.image7 -> 7
            R.id.image8 -> 8
            R.id.image9 -> 9
            else -> -1
        }
    }

    class WinningVersions {

        /** To win versions **/
        val v1X: Map<Int, String> = mapOf(1 to "X", 2 to "X", 3 to "X")
        val v2X: Map<Int, String> = mapOf(4 to "X", 5 to "X", 6 to "X")
        val v3X: Map<Int, String> = mapOf(7 to "X", 8 to "X", 9 to "X")
        val v4X: Map<Int, String> = mapOf(1 to "X", 4 to "X", 7 to "X")
        val v5X: Map<Int, String> = mapOf(2 to "X", 5 to "X", 8 to "X")
        val v6X: Map<Int, String> = mapOf(3 to "X", 6 to "X", 9 to "X")
        val v7X: Map<Int, String> = mapOf(1 to "X", 5 to "X", 9 to "X")
        val v8X: Map<Int, String> = mapOf(3 to "X", 5 to "X", 7 to "X")

        val v1O: Map<Int, String> = mapOf(1 to "O", 2 to "O", 3 to "O")
        val v2O: Map<Int, String> = mapOf(4 to "O", 5 to "O", 6 to "O")
        val v3O: Map<Int, String> = mapOf(7 to "O", 8 to "O", 9 to "O")
        val v4O: Map<Int, String> = mapOf(1 to "O", 4 to "O", 7 to "O")
        val v5O: Map<Int, String> = mapOf(2 to "O", 5 to "O", 8 to "O")
        val v6O: Map<Int, String> = mapOf(3 to "O", 6 to "O", 9 to "O")
        val v7O: Map<Int, String> = mapOf(1 to "O", 5 to "O", 9 to "O")
        val v8O: Map<Int, String> = mapOf(3 to "O", 5 to "O", 7 to "O")

        var versionsList: List<Map<Int, String>> =
            listOf(v1X, v2X, v3X, v4X, v5X, v6X, v7X, v8X, v1O, v2O, v3O, v4O, v5O, v6O, v7O, v8O)
    }
}