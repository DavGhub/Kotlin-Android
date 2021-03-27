package com.davit.kotlin.intents.lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.davit.kotlin.intents.R
import com.davit.kotlin.intents.databinding.ActivityLifeCycleBinding


private const val LOG_TAG = "ActivityLifeCycle"
const val EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE"
private const val TEXT_REQUEST = 1

class ActivityLifeCycle : AppCompatActivity() {

    private lateinit var binding:ActivityLifeCycleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_life_cycle)

        if(savedInstanceState != null){
            val isVisible = savedInstanceState.getBoolean("reply_visible")
            if (isVisible) {
                binding.textHeaderReply.visibility = View.VISIBLE
                binding.textMessageReply.text = savedInstanceState.getString("reply_text")
                binding.textMessageReply.visibility = View.VISIBLE
            }
        }

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(binding.textHeaderReply.visibility == View.VISIBLE){
            outState.putBoolean("reply_visible", true)
            outState.putString("reply_text", binding.textMessageReply.text.toString())
        }
    }

    fun launchSecondActivity(view: View?) {
        Log.d(LOG_TAG, "Button clicked!")
        val intent = Intent(this, SecondActivity::class.java)
        val message: String = binding.editTextMain.text.toString()
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                val reply = data?.getStringExtra(EXTRA_REPLY)
                binding.textHeaderReply.visibility = View.VISIBLE
                binding.textMessageReply.text = reply
                binding.textMessageReply.visibility = View.VISIBLE
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
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }
}