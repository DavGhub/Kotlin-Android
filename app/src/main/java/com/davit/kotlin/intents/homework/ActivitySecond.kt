package com.davit.kotlin.intents.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import com.davit.kotlin.intents.R

private const val LOG_TAG = "ActivitySecond"
const val EXTRA_RETURN = "com.davit.kotlin.extra.RETURN"

class ActivitySecond : AppCompatActivity() {

    private lateinit var timerText:TextView
    private var currentTime = 0L
    private lateinit var timer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second2)

        timerText = findViewById(R.id.timer)

        val intent = intent
        val intentTime = intent.getLongExtra("time",0)

        timer = object: CountDownTimer(intentTime,1000){
            override fun onTick(p0: Long) {
                timerText.text = (p0/1000L).toString()
                currentTime = p0
            }

            override fun onFinish() {
                Log.e("SecondTimer","Timer stopped")
            }

        }

        timer.start()
    }

    fun returnTime(view: View?) {
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_RETURN, currentTime)
        setResult(RESULT_OK, replyIntent)
        finish()
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
        timer.cancel()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }
}