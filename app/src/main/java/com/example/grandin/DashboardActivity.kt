package com.example.grandin

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.grandin.Adapter.TabAdapter
import com.example.grandin.databinding.ActivityDashboardBinding
import com.google.android.material.tabs.TabLayout


class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
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
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("OPEN"))

        val Myadapter = TabAdapter(supportFragmentManager)
        binding.viewpager.adapter = Myadapter

        binding.viewpager.addOnPageChangeListener(object :
            TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout) {})

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewpager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


    }}


//    private fun fetchExcelData() {
//        RetrofitInstance.api.getExcelData().enqueue(object : Callback<ExcelResponse> {
//            override fun onResponse(call: Call<ExcelResponse>, response: Response<ExcelResponse>) {
//                if (response.isSuccessful) {
//                    val excelResponse = response.body()
//                    if (excelResponse != null) {
//                        // Log the entire response
//                        Log.d("MainActivity", "Message: ${excelResponse.message}")
//                        Log.d("MainActivity", "Excel File Data: ${excelResponse.data}")
//                        excelResponse.sheetData.forEachIndexed { index, sheetData ->
//                            Log.d("MainActivity", "Sheet Data #$index: $sheetData")
//                        }
//                    } else {
//                        Log.d("MainActivity", "No data received")
//                    }
//                } else {
//                    Log.e("MainActivity", "Response not successful: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ExcelResponse>, t: Throwable) {
//                Log.e("MainActivity", "Error fetching data", t)
//                Toast.makeText(this@com.example.grandin.DashboardActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}