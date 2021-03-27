package com.davit.kotlin.intents.intents

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.davit.kotlin.intents.R
import com.davit.kotlin.intents.databinding.ActivityIntentReceiverBinding


class IntentReceiver : AppCompatActivity() {

    private lateinit var binding:ActivityIntentReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intent_receiver)

        val intent = intent
        val uri: Uri? = intent.data
        if (uri != null) {
            val uriString = ("URI: $uri")
            binding.intentData.text = uriString
        }
    }
}