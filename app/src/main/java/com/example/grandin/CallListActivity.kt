package com.example.grandin

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grandin.Adapter.SheetItemAdapter
import com.example.grandin.Api.SheetDataItem
import com.example.grandin.databinding.ActivityCallListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallListActivity : AppCompatActivity() {

    lateinit var binding: ActivityCallListBinding
    lateinit var sheetData : List<SheetDataItem>
    var adapter: SheetItemAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        initview()
    }

    private fun initview()
    {
        RetrofitInstance.api.getExcelData().enqueue(object : Callback<ExcelResponse>
        {
            override fun onResponse(call: Call<ExcelResponse>, response: Response<ExcelResponse>)
            {
                if (response.isSuccessful)
                {
                    val excelResponse = response.body()
                    if (excelResponse != null)
                    {
                        // Log the entire response
                        Log.d("CallListActivityApiResponse", "Message: ${excelResponse.message}")
                        Log.d("CallListActivityApiResponse", "Excel File Data: ${excelResponse.data}")

                        // Assuming you fetch your sheetData from the response
                        sheetData = excelResponse.data // Adjust this line based on your actual response structure

                        binding.recyclerviewCallList.layoutManager = LinearLayoutManager(this@CallListActivity)

                        // Now, you can safely create the adapter with the initialized sheetData
                        adapter = SheetItemAdapter(this@CallListActivity,sheetData)
                        binding.recyclerviewCallList.adapter = adapter
                    } else {
                        Log.d("CallListActivityApiResponse", "No data received")
                    }
                }
                else
                {
                    Log.e("CallListActivityApiResponse", "Response not successful: ${response.code()}")
                    Log.e("CallListActivityApiResponse", "Response body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ExcelResponse>, t: Throwable) {
                Log.e("CallListActivityApiResponse", "Error fetching data", t)
                Toast.makeText(this@CallListActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
       })
    }
}