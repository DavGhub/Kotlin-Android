package com.davit.kotlin.sunnyschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.davit.kotlin.sunnyschool.databinding.ActivityTicTacToeBinding

class TicTacToe : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityTicTacToeBinding
    private var player1IsPlaying = true
    private val gameCurrentState: MutableMap<Int, String> = mutableMapOf()
    private val winningVersions = WinningVersions()
    private var winnerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tic_tac_toe)

        binding.image1.setOnClickListener(this)
        binding.image2.setOnClickListener(this)
        binding.image3.setOnClickListener(this)
        binding.image4.setOnClickListener(this)
        binding.image5.setOnClickListener(this)
        binding.image6.setOnClickListener(this)
        binding.image7.setOnClickListener(this)
        binding.image8.setOnClickListener(this)
        binding.image9.setOnClickListener(this)
        binding.reset.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view){
            is ImageView -> checkClickedField(view)
            else -> {
                resetGame()
                Toast.makeText(this,"Reset",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkClickedField(imageView:ImageView){
        if(imageView.isEnabled){

            if(player1IsPlaying){
                imageView.setImageResource(R.drawable.ic_cross)
                binding.currentPlayer.text = getString(R.string.player_2)
                gameCurrentState[identifyClickedField(imageView)] = "X"
                player1IsPlaying = false
            }else{
                imageView.setImageResource(R.drawable.ic_circle)
                binding.currentPlayer.text = getString(R.string.player_1)
                gameCurrentState[identifyClickedField(imageView)] = "O"
                player1IsPlaying = true
            }
            println(gameCurrentState)
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
            Toast.makeText(this,"Are you blind?))",Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetGame(){
        for (i in 0..binding.parent.childCount){
            val childView = binding.parent.getChildAt(i)
            if(childView is ImageView){
                childView.setImageDrawable(null)
                childView.isEnabled = true
            }
        }
        binding.currentPlayer.text = getString(R.string.player_1)
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
        Toast.makeText(this,"Game finished",Toast.LENGTH_SHORT).show()
    }

    private fun isThereAWinner(map: MutableMap<Int, String>, versionList: List<Map<Int, String>>): Boolean {

        for (version in 0 until versionList.size - 1) {
            if (map.entries.containsAll(versionList[version].entries)) {
                println("Winner pattern: ${versionList[version]}")
                winnerName = if(versionList[version].containsValue("X")){
                    "Player 1"
                }else{
                    "Player 2"
                }
                return true
            }
        }

        return false
    }

    private fun identifyClickedField(field:ImageView):Int{
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