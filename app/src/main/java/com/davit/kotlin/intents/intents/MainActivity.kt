package com.davit.kotlin.intents.intents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.davit.kotlin.intents.R
import com.davit.kotlin.intents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    fun openWebsite(view: View) {
        val webPage: Uri = Uri.parse(binding.websiteEdittext.text.toString())
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }

    fun openLocation(view: View) {
        val location = Uri.parse("geo:0,0?q=${binding.locationEdittext.text.toString()}")
        val intent = Intent(Intent.ACTION_VIEW, location)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle location");
        }
    }

    fun shareText(view: View) {
        ShareCompat.IntentBuilder
            .from(this)
            .setType("text/plain")
            .setChooserTitle(getString(R.string.share_text_title))
            .setText(binding.shareEdittext.text.toString())
            .startChooser()
    }
}