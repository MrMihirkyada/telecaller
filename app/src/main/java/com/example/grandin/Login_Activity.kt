package com.example.grandin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.grandin.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login_Activity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.DarkOrange)
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)


        initview()
    }

    private fun initview() {
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPasswordToggle.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Create LoginRequest object
                val loginRequest = AgentData(username, password)


//                Toast.makeText(this@Login_Activity, "Login Successfully", Toast.LENGTH_SHORT).show()
//                var i = Intent(this@Login_Activity, DashboardDesign2_Activity::class.java)
//                startActivity(i)


                Log.e("LoginActivityApiResponse", "initview: "+loginRequest)
                RetrofitLoginInstance.api.getLoginData(loginRequest).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse != null)
                            {
                                // Log the entire response
                                Log.d("LoginActivityApiResponse", "Message: ${loginResponse.message}")
                                Log.d("LoginActivityApiResponse", "Login File Data: ${loginResponse.data}")
                                Log.d("LoginActivityApiResponse", "Login File success: ${loginResponse.success}")

                                var i = Intent(this@Login_Activity, DashboardDesign2_Activity::class.java)
                                startActivity(i)
                                Toast.makeText(this@Login_Activity, "Login Successfully", Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                Log.d("LoginActivityApiResponse", "No data received")
                            }
                        }
                        else
                        {
                            Log.e("LoginActivityApiResponse", "Response not successful: ${response.code()}")
                            Log.e("LoginActivityApiResponse", "Response body: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("LoginActivityApiResponse", "Error fetching data", t)
                        Toast.makeText(this@Login_Activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@Login_Activity, "User name or Password is Empty", Toast.LENGTH_SHORT).show()
            }
    }



//    private fun initview() {
//
//
//
//        binding.btnLogin.setOnClickListener {
//
//            var username = binding.edtUsername.text.toString()
//
//            var password = binding.edtPasswordToggle.text.toString()
//
//            if (username.isNotEmpty() && password.isNotEmpty()) {
//                RetrofitLoginInstance.api.getLoginData().enqueue(object : Callback<LoginResponse> {
//                    override fun onResponse(
//                        call: Call<LoginResponse>,
//                        response: Response<LoginResponse>
//                    ) {
//                        if (response.isSuccessful) {
//                            val loginResponse = response.body()
//                            if (loginResponse != null) {
//                                // Log the entire response
//                                Log.d(
//                                    "LoginActivityApiResponse",
//                                    "Message: ${loginResponse.message}"
//                                )
//                                Log.d(
//                                    "LoginActivityApiResponse",
//                                    "Login File Data: ${loginResponse.data}"
//                                )
//                                Log.d(
//                                    "LoginActivityApiResponse",
//                                    "Login File success: ${loginResponse.success}"
//                                )
//                            } else {
//                                Log.d("LoginActivityApiResponse", "No data received")
//                            }
//                        } else {
//                            Log.e(
//                                "LoginActivityApiResponse",
//                                "Response not successful: ${response.code()}"
//                            )
//                            Log.e(
//                                "LoginActivityApiResponse",
//                                "Response body: ${response.errorBody()?.string()}"
//                            )
//                        }
//                    }
//
//                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                        Log.e("LoginActivityApiResponse", "Error fetching data", t)
//                        Toast.makeText(
//                            this@Login_Activity,
//                            "Error fetching data",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                })
//            }
//            else
//            {
//                Toast.makeText(this@Login_Activity, "User name or Password is Empty", Toast.LENGTH_SHORT).show()
//            }
////            else
////            {
////                Toast.makeText(this@Login_Activity, "User name or Password is Empty", Toast.LENGTH_SHORT).show()
////            }
//        }
    }
}