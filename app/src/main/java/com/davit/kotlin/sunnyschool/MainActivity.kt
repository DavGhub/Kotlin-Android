package com.davit.kotlin.sunnyschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.davit.kotlin.sunnyschool.databinding.LoginBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.login)

        binding.loginBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(view?.id == R.id.login_btn){
            if(!isValidEmail(binding.email.text.toString())){
                binding.email.error = "Email is not valid"
                return
            }
            if(!isValidPassword(binding.password.text.toString())){
                binding.password.error = "Password is not valid"
                return
            }
            Toast.makeText(this,"email: ${binding.email.text} pass: ${binding.password.text}",Toast.LENGTH_SHORT).show()
        }
    }



    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val passwordRegex = "^(?!(12345678|87654321|00000000)$).{8,}$"
        pattern = Pattern.compile(passwordRegex)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern
        val emailRegex = "^([_A-Za-z0-9-+]+\\.?[_A-Za-z0-9-+]+@(gmail.com|mail.ru))\$"
        pattern = Pattern.compile(emailRegex)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}