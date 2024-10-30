package com.example.grandin

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grandin.Adapter.SheetItemAdapter
import com.example.grandin.Adapter.SheetItemAdapter.Companion.data
import com.example.grandin.Api.SheetDataItem
import com.example.grandin.databinding.ActivityDashboardDesign2Binding
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class DashboardDesign2_Activity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardDesign2Binding

    companion object {
        private const val CALL_PERMISSION_REQUEST_CODE = 1
    }

    private var callNote: String = ""
    private var sheetData: List<SheetDataItem> = mutableListOf()
    private var endOfCallDialog: Dialog? = null
    private var isCountdownCancelled = false

    private var currentCallIndex = 0
    private var callDelay: Long = 0 // Initialize this to the desired default delay
//    private val phoneNumbers = listOf("1234567890", "1234567891","1234567892","1234567893","1234567894","1234567895","1234567896","1234567897","1234567898","1234567899") // Example phone numbers


    lateinit var adapter: SheetItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardDesign2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        initview()
    }

    private fun initview() {

        RetrofitInstance.api.getExcelData().enqueue(object : Callback<ExcelResponse> {
            override fun onResponse(call: Call<ExcelResponse>, response: Response<ExcelResponse>) {
                if (response.isSuccessful) {
                    val excelResponse = response.body()
                    if (excelResponse != null) {
                        // Log the entire response
                        Log.d("CallListActivityApiResponse", "Message: ${excelResponse.message}")
                        Log.d(
                            "CallListActivityApiResponse",
                            "Excel File Data: ${excelResponse.data}"
                        )

                        // Assuming you fetch your sheetData from the response
                        sheetData = excelResponse.data // Adjust this line based on your actual response structure

//                        binding.recyclerviewCallList.layoutManager = LinearLayoutManager(this@CallListActivity)
//
//                        // Now, you can safely create the adapter with the initialized sheetData
//                        adapter = SheetItemAdapter(this@CallListActivity,sheetData)
//                        binding.recyclerviewCallList.adapter = adapter
                    } else {
                        Log.d("CallListActivityApiResponse", "No data received")
                    }
                } else {
                    Log.e(
                        "CallListActivityApiResponse",
                        "Response not successful: ${response.code()}"
                    )
                    Log.e(
                        "CallListActivityApiResponse",
                        "Response body: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<ExcelResponse>, t: Throwable) {
                Log.e("CallListActivityApiResponse", "Error fetching data", t)
                Toast.makeText(
                    this@DashboardDesign2_Activity,
                    "Error fetching data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        binding.lnrCallList.setOnClickListener {
            var i = Intent(this, CallListActivity::class.java)
            startActivity(i)
        }

        binding.call.setOnClickListener {
            if (sheetData.isNotEmpty()) {
                showCallDelayDialog(sheetData[currentCallIndex].contactNumber.toString())
            }
        }
    }


    private fun showCallDelayDialog(contactNumber: String) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_auto_diler, null)
        bottomSheetDialog.setContentView(view)

        val buttonStandardMode = view.findViewById<Button>(R.id.buttonStandardMode)
        val editTextCallDelay = view.findViewById<EditText>(R.id.editTextCallDelay)

        buttonStandardMode.setOnClickListener {
            val timeInput = editTextCallDelay.text.toString()
            if (timeInput.isNotEmpty()) {
                callDelay = timeInput.toLong() * 1000 // Convert seconds to milliseconds
                bottomSheetDialog.dismiss()
                startAutoCalling(sheetData[currentCallIndex].contactNumber.toString()) // Start calling with the specified delay
            } else {
                Toast.makeText(this, "Please enter a delay time", Toast.LENGTH_SHORT).show()
            }
        }

        bottomSheetDialog.show()
    }

    private fun startAutoCalling(phoneNumber: String) {
        // Show the countdown timer dialog
        isCountdownCancelled = false


        val countdownDialog = BottomSheetDialog(this)
        val countdownView = LayoutInflater.from(this).inflate(R.layout.layout_countdown_timer, null)
        countdownDialog.setContentView(countdownView)

        val textViewCountdown = countdownView.findViewById<TextView>(R.id.textViewCountdown)
        val textViewPhoneNumber = countdownView.findViewById<TextView>(R.id.phone_number)
        val cancel_button = countdownView.findViewById<TextView>(R.id.cancel_button)
        val name_text = countdownView.findViewById<TextView>(R.id.name_text)

        textViewPhoneNumber.text = "Phone Number :- " + sheetData[currentCallIndex].contactNumber.toString()
        name_text.text = "Name :-" + sheetData[currentCallIndex].name.toString()



        showEndOfCallDialog(phoneNumber)

        countdownDialog.show()

        // Start the countdown
        object : CountDownTimer(callDelay, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                textViewCountdown.text = secondsRemaining.toString() // Update countdown text
            }

            override fun onFinish() {
                if (!isCountdownCancelled) {
                    countdownDialog.dismiss() // Dismiss countdown dialog
                    makePhoneCall(phoneNumber) // Start the phone call
                }
            }
        }.start()


        cancel_button.setOnClickListener {
            isCountdownCancelled = true // Set the flag to indicate cancellation
            countdownDialog.dismiss()
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(callIntent)
            startCallDelay() // Start the delay for the next call
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun startCallDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            currentCallIndex++ // Move to the next phone number
            if (currentCallIndex < sheetData.size) {
//                    showCallDelayDialog(phoneNumbers[currentCallIndex]) // Show dialog for the next call
            } else {
                Toast.makeText(this, "All calls completed.", Toast.LENGTH_SHORT).show()
            }
        }, callDelay) // Delay as set by the user
    }

    fun onCallEnded() {
        if (currentCallIndex < sheetData.size) {
            val nextPhoneNumber = sheetData[currentCallIndex]
            currentCallIndex++ // Move to the next phone number
            startAutoCalling(nextPhoneNumber.toString())
        } else {
            Toast.makeText(this, "All calls completed.", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showEndOfCallDialog(phoneNumber: String)
    {
        // If the dialog already exists, just update its data
        if (endOfCallDialog == null) {
            endOfCallDialog = Dialog(this).apply {
                val view = LayoutInflater.from(this@DashboardDesign2_Activity)
                    .inflate(R.layout.dialog_update_caller, null)
                setContentView(view)

                val imgNext = view.findViewById<ImageView>(R.id.imgNext)
                val imgClose = view.findViewById<LinearLayout>(R.id.imgClose)
                val lnrCallNote = view.findViewById<LinearLayout>(R.id.lnrCallNote)

                // Save Note
                lnrCallNote.setOnClickListener {
                    val input = EditText(this@DashboardDesign2_Activity).apply {
                        hint = "Enter call note"
                        inputType = InputType.TYPE_CLASS_TEXT
                    }
                    AlertDialog.Builder(this@DashboardDesign2_Activity)
                        .setTitle("Call Note")
                        .setView(input)
                        .setPositiveButton("Save") { _, _ ->
                            callNote = input.text.toString()
                            Toast.makeText(this@DashboardDesign2_Activity, "Note saved!", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }

                // Close Dialog
                imgClose.setOnClickListener { dismiss() }

                // Navigate to Next Number
                imgNext.setOnClickListener {
                    if (currentCallIndex < sheetData.size - 1) {
                        currentCallIndex++
                        updateDialogData(currentCallIndex) // Update dialog with the new contact's details
                        startAutoCalling(sheetData[currentCallIndex].contactNumber.toString())
                    } else {
                        Toast.makeText(this@DashboardDesign2_Activity, "All calls completed.", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }
        }

        // Display the dialog and update its data
        updateDialogData(currentCallIndex)
        endOfCallDialog?.show()
    }


    private fun updateDialogData(index: Int) {
        // Update dialog views with the current contact's details
        endOfCallDialog?.findViewById<TextView>(R.id.textViewPhoneNumber)?.text =
            "Contact: ${sheetData[index].contactNumber}"
        endOfCallDialog?.findViewById<TextView>(R.id.textViewName)?.text =
            "Name: ${sheetData[index].name}"
    }


    // Function to show the DatePickerDialog
    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(formattedDate)
        }, year, month, day).show()
    }

    // Function to show the TimePickerDialog
    private fun showTimePickerDialog(onTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            onTimeSelected(formattedTime)
        }, hour, minute, true).show()
    }


}