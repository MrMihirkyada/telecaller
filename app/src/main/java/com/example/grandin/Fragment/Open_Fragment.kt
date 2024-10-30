package com.example.grandin.Fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grandin.Adapter.SheetItemAdapter
import com.example.grandin.Api.SheetDataItem
import com.example.grandin.databinding.FragmentOpenBinding


class Open_Fragment : Fragment() {

    private var _binding: FragmentOpenBinding? = null
    private val binding get() = _binding!!
    private lateinit var sheetData : List<SheetDataItem>
    val REQUEST_CALL_PERMISSION_CODE = 100

    var adapter: SheetItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenBinding.inflate(inflater, container, false)
        val view = binding.root

        // Fetch the Excel data when the fragment is created
        fetchExcelData()
        return view
    }

    private fun fetchExcelData() {


//        // Sample data (this should match the data from the table)
//        val businessList = listOf(
//            Business("Rakesh", "1234567890", "user1@gmail.com", "Apollo", "www.apollo.com", "Hospital"),
//            Business("Rajesh", "1234567891", "user2@gmail.com", "Minerva", "www.minerva.com", "Hotel"),
//            Business("Sam", "1234567892", "user3@gmail.com", "Star Hospital", "www.starhospital.com", "Hospital"),
//            Business("John", "1234567893", "user4@gmail.com", "Taj Hotel", "www.tajhotel.com", "Hotel"),
//            Business("Lokesh", "1234567894", "user5@gmail.com", "Fitness Hub", "www.fitnesshub.com", "Gym"),
//            Business("Ajay", "1234567895", "user6@gmail.com", "Rainbow School", "www.rainbowschool.com", "School"),
//            Business("Vijay", "1234567896", "user7@gmail.com", "Fortune Grand", "www.fortunegrand.com", "Hotel"),
//            Business("Prakash", "1234567897", "user8@gmail.com", "Capital Way", "www.capitalway.com", "Hotel"),
//            Business("Rahul", "1234567898", "user9@gmail.com", "The Gateway", "www.thegateway.com", "Hotel"),
//            Business("Kiran", "1234567899", "user10@gmail.com", "Tulip Manohar", "www.tulipmanohar.com", "Hotel")
//        )


        // Setting up the adapter with the data
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = SheetItemAdapter(requireContext(),sheetData)

        Log.e("fetchExcelData", "fetchExcelData: "+sheetData)
//        RetrofitInstance.api.getExcelData().enqueue(object : Callback<ExcelResponse> {
//            override fun onResponse(call: Call<ExcelResponse>, response: Response<ExcelResponse>) {
//                if (response.isSuccessful) {
//                    val excelResponse = response.body()
//                    if (excelResponse != null) {
//                        // Log the entire response
//                        Log.d("MainActivityApiResponse", "Message: ${excelResponse.message}")
//                        Log.d("MainActivityApiResponse", "Excel File Data: ${excelResponse.data}")
//
//                        // Assuming you fetch your sheetData from the response
//                        sheetData = excelResponse.data // Adjust this line based on your actual response structure
//
//                        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
//
//                        // Now, you can safely create the adapter with the initialized sheetData
//                        adapter = SheetItemAdapter(requireContext(),sheetData)
//                        binding.recyclerview.adapter = adapter
//                    } else {
//                        Log.d("MainActivityApiResponse", "No data received")
//                    }
//                } else {
//                    Log.e("MainActivityApiResponse", "Response not successful: ${response.code()}")
//                    Log.e("MainActivityApiResponse", "Response body: ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ExcelResponse>, t: Throwable) {
//                Log.e("MainActivityApiResponse", "Error fetching data", t)
//                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
//            }
//        })
    }


//    private fun makePhoneCall(phoneNumber: String) {
//        val callIntent = Intent(Intent.ACTION_CALL).apply {
//            data = Uri.parse("tel:$phoneNumber")
//        }
//
//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            ContextCompat.startActivity(callIntent)
//        } else {
//            // Request the CALL_PHONE permission
//            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION_CODE)
//        }
//    }
//
//    val phoneNumbers = listOf("1234567890", "1234567891", "1234567892","1234567893","1234567894","1234567895","1234567896","1234567897","1234567898","1234567899") // Add all numbers here
//    var currentIndex = 0 // Index to track the current phone number
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CALL_PERMISSION_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, make the call
//                makePhoneCall(phoneNumbers[currentIndex])
//            } else {
//                Toast.makeText(requireContext(), "Call permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}
