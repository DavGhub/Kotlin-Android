package com.davit.kotlin.intents.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.davit.kotlin.intents.R

private const val LOG_TAG = "FirstActivity"
private const val TIMER_REQUEST = 77

class FirstActivity : AppCompatActivity() {

    private var currentTime:Long = 0
    private lateinit var countDownTimer:CountDownTimer
    private lateinit var startTimer:Button
    private lateinit var nextPage:Button
    private lateinit var timerText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        startTimer = findViewById(R.id.start_timer)
        nextPage = findViewById(R.id.next_page)
        timerText = findViewById(R.id.timer)
    }

    fun startTimer(view:View){
        runTimer(40000)
    }

    fun goToNextPage(view:View){
        val nextActivity = Intent(this,ActivitySecond::class.java)
        nextActivity.putExtra("time",currentTime)
        startActivityForResult(nextActivity,TIMER_REQUEST)
    }

    private fun runTimer(timeInMillis:Long){
        countDownTimer = object :CountDownTimer(timeInMillis,2000){
            override fun onTick(p0: Long) {
                timerText.text = (p0/1000L).toString()
                currentTime = p0
            }

            override fun onFinish() {
                Log.e("Timer","Timer stopped")
            }

        }
        countDownTimer.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TIMER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val reply = data?.getLongExtra(EXTRA_RETURN,0)
                if(reply != null) runTimer(reply)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(LOG_TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        if (this::countDownTimer.isInitialized) countDownTimer.cancel()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }
}