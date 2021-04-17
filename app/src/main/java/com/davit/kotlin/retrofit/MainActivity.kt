package com.davit.kotlin.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.davit.kotlin.retrofit.databinding.ActivityMainBinding
import com.davit.kotlin.retrofit.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var usersAdapter:UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstRequest()
        secondRequest()

    }

    private fun firstRequest(){
        val firstCall = RetrofitApiService.getService(BASE_URL_1)?.create(RetrofitAPI::class.java)?.getProducts(2)
        lifecycleScope.launch(Dispatchers.Default){
            val response = firstCall?.execute()
            withContext(Dispatchers.Main){
                binding.id.text = getString(R.string.id,response?.body()?.data?.id ?: "no data")
                binding.name.text = getString(R.string.name,response?.body()?.data?.name ?: "no data")
                binding.description.text = getString(R.string.description,response?.body()?.data?.description ?: "no data")
                binding.price.text = getString(R.string.price,response?.body()?.data?.price ?: "no data")
                binding.discount.text = getString(R.string.discount_amount,response?.body()?.data?.discount_amount ?: "no data")
            }
        }
    }

    private fun secondRequest(){
        val secondCall = RetrofitApiService.getService(BASE_URL_2)?.create(RetrofitAPI::class.java)?.getUsers(1)
        lifecycleScope.launch(Dispatchers.Default){
            val response = secondCall?.enqueue(object:Callback<UserModel>{
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    Log.e("Response","response: ${response.body().toString()}")
                    Log.e("Response","url: ${response.raw().request().url()}")
                    response.let {
                        it.body()?.data?.let { it1 -> initAdapter(it1) }
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.e("Fail","Error message: ${t.message}")
                }

            })
        }
    }

    private fun initAdapter(usersList:MutableList<UserModel.User>){
        usersAdapter = UsersAdapter(this, usersList)
        val layoutManager = LinearLayoutManager(this)
        binding.usersRecyclerview.layoutManager = layoutManager
        binding.usersRecyclerview.adapter = usersAdapter
    }
}