package com.example.grandin.Adapter
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.app.DatePickerDialog
//import android.app.Dialog
//import android.app.TimePickerDialog
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView
//import com.example.grandin.Api.SheetDataItem
//import com.example.grandin.ApiData
//import com.example.grandin.R
//import com.example.grandin.RetrofitClient
//import com.google.android.material.bottomsheet.BottomSheetDialog
//
//import retrofit2.Call
//
//
//class SheetItemAdapter(var context: Context,val sheetData: List<SheetDataItem> // Add this as a parameter to the adapter
// ) : RecyclerView.Adapter<SheetItemAdapter.SheetItemViewHolder>() {
//
//
//
//    lateinit var selectedStatus : String
//    val REQUEST_CALL_PERMISSION_CODE = 100
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SheetItemViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_business_info, parent, false) // Replace with your actual layout file name
//        return SheetItemViewHolder(itemView)
//    }
//
//        override fun onBindViewHolder(holder: SheetItemViewHolder, position: Int) {
//            val currentItem = sheetData[position]
//
//            Log.e("currentItem", "onBindViewHold"+currentItem.name+currentItem.contactNumber+currentItem.emailId+currentItem.company+currentItem.website+currentItem.businessType)
//            holder.textViewName.text = currentItem.name
//
//            holder.textViewContactNo.text = currentItem.contactNumber.toString()
//            holder.textViewCompanyName.text = currentItem.company
//            holder.textViewEmailId.text = currentItem.emailId
//            holder.textViewBusinessType.text = currentItem.businessType
//
//
////            holder.textViewContactNo.text = currentItem.contactNumber.toString()
//
////            holder.textViewContactNo.setOnClickListener {
//                val bottomSheetDialog = BottomSheetDialog(holder.itemView.context)
//                val view = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_auto_diler, null)
//                bottomSheetDialog.setContentView(view)
//                bottomSheetDialog.show()
//
//                // Access views in the bottom sheet layout
//                val buttonStart = view.findViewById<Button>(R.id.buttonStandardMode)
////                val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)
//                val editTextBrakeBetweenCalls = view.findViewById<EditText>(R.id.editTextCallDelay)
//
//                buttonStart.setOnClickListener {
//                    val timeInput = editTextBrakeBetweenCalls.text.toString()
//                    if (timeInput.isNotEmpty())
//                    {
//                        val timeInSeconds = timeInput.toLong() * 1000 // Convert seconds to milliseconds
//                        bottomSheetDialog.dismiss()
//
//                    }
//                }
//            }
//
//
//    private fun updateApiData(apiData: ApiData) {
//        val call = RetrofitClient.instance.getupdateData(apiData)
//        call.enqueue(object : retrofit2.Callback<ApiData> {
//            override fun onResponse(call: Call<ApiData>, response: retrofit2.Response<ApiData>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(context, "Data updated successfully!", Toast.LENGTH_SHORT).show()
//                } else {
//                    val errorBody = response.errorBody()!!.string()
//                    Log.e("Update failed:", "Code: ${response.code()} - Message: $errorBody")
//                    Toast.makeText(context, "Update failed: $errorBody", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ApiData>, t: Throwable) {
//                // Handle failure
//                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    // Function to initiate the phone call
//    private fun makePhoneCall(context: Context, phoneNumber: String) {
//
//
//        val callIntent = Intent(Intent.ACTION_CALL).apply {
//            data = Uri.parse("tel:$phoneNumber")
//        }
//
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            context.startActivity(callIntent)
//        } else {
//            // Request CALL_PHONE permission if not granted
//            if (context is Activity) {
//                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION_CODE)
//            } else {
//                Toast.makeText(context, "Call permission required", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return sheetData.size
//    }
//
//    class SheetItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
//        val textViewContactNo: TextView = itemView.findViewById(R.id.textViewContactNo)
//        val textViewEmailId: TextView = itemView.findViewById(R.id.textViewEmailId)
//        val textViewCompanyName: TextView = itemView.findViewById(R.id.textViewCompanyName)
//        val textViewBusinessType: TextView = itemView.findViewById(R.id.textViewBusinessType)
//    }
//
//
//    companion object {
//        private const val CALL_PERMISSION_REQUEST_CODE = 100
//    }
//
//}


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.grandin.Api.SheetDataItem
import com.example.grandin.ApiData
import com.example.grandin.CallStateReceiver
import com.example.grandin.R
import com.example.grandin.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SheetItemAdapter(var context: Context, private val sheetData: List<SheetDataItem>) :
    RecyclerView.Adapter<SheetItemAdapter.SheetItemViewHolder>() {


    lateinit var selectedStatus: String
    private var callNote: String = ""

    private var phoneNumbers = sheetData.map { it.contactNumber.toString() }
    private var currentCallIndex = 0
    private var callDelay: Long = 0 // Time delay for the next call
    private var callStateReceiver: CallStateReceiver? = null
    private var endOfCallDialog: Dialog? = null
    private var isCountdownCancelled = false

    init {
        registerCallStateReceiver()
    }

    private fun registerCallStateReceiver() {
        callStateReceiver = CallStateReceiver(context, this)
        val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        context.registerReceiver(callStateReceiver, filter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SheetItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_business_info,
                parent,
                false
            ) // Replace with your actual layout file name
        return SheetItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SheetItemViewHolder, position: Int) {
        val currentItem = sheetData[position]
//        holder.textViewName.text = currentItem.name
        holder.textViewContactNo.text = currentItem.contactNumber.toString()

        holder.textViewContactNo.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(holder.itemView.context)
            val view = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.dialog_auto_diler, null)
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()

        }

        holder.textViewContactNo.text = currentItem.contactNumber.toString()
        holder.textViewCompanyName.text = currentItem.company
        holder.textViewEmailId.text = currentItem.emailId
        holder.textViewBusinessType.text = currentItem.businessType

        holder.imgCall.setOnClickListener {
            val context = holder.itemView.context
            val bottomSheetDialog = BottomSheetDialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_auto_diler, null)
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()

            val buttonStandardMode = view.findViewById<Button>(R.id.buttonStandardMode)
            val editTextCallDelay = view.findViewById<EditText>(R.id.editTextCallDelay)

            buttonStandardMode.setOnClickListener {
                val timeInput = editTextCallDelay.text.toString()
                if (timeInput.isNotEmpty()) {
                    val timeInMillis = timeInput.toLong() * 1000 // Convert seconds to milliseconds
                    bottomSheetDialog.dismiss()

                    // Delay for specified time and then trigger the call
                    Handler(Looper.getMainLooper()).postDelayed({
                        // Place the call intent here
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data =
                            Uri.parse("tel:" + /* Add phone number here, e.g., */ "1234567890")
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CALL_PHONE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            context.startActivity(callIntent)
                        } else {
                            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                        }
                    }, timeInMillis)
                } else {
                    Toast.makeText(context, "Please enter a delay time", Toast.LENGTH_SHORT).show()
                }
            }
        }


        holder.imgCall.setOnClickListener {
            showCallDelayDialog(sheetData[currentCallIndex].contactNumber.toString())
        }

        data = currentItem.contactNumber.toString()
    }


    private fun updateApiData(apiData: ApiData) {
        val call = RetrofitClient.instance.getupdateData(apiData)
        call.enqueue(object : retrofit2.Callback<ApiData> {
            override fun onResponse(call: Call<ApiData>, response: retrofit2.Response<ApiData>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Data updated successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()!!.string()
                    Log.e("Update failed:", "Code: ${response.code()} - Message: $errorBody")
                    Toast.makeText(context, "Update failed: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<ApiData>, t: Throwable) {
                // Handle failure
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun getItemCount(): Int {
        return sheetData.size
    }


    private fun showCallDelayDialog(contactNumber: String) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_auto_diler, null)
        bottomSheetDialog.setContentView(view)

        val buttonStandardMode = view.findViewById<Button>(R.id.buttonStandardMode)
        val editTextCallDelay = view.findViewById<EditText>(R.id.editTextCallDelay)

        buttonStandardMode.setOnClickListener {
            val timeInput = editTextCallDelay.text.toString()
            if (timeInput.isNotEmpty()) {
                callDelay = timeInput.toLong() * 1000 // Convert seconds to milliseconds
                bottomSheetDialog.dismiss()
                startAutoCalling(contactNumber) // Start calling with the specified delay
            } else {
                Toast.makeText(context, "Please enter a delay time", Toast.LENGTH_SHORT).show()
            }
        }

        bottomSheetDialog.show()
    }

    private fun startAutoCalling(phoneNumber: String) {
        // Show the countdown timer dialog
        isCountdownCancelled = false

        val countdownDialog = BottomSheetDialog(context)
        val countdownView =
            LayoutInflater.from(context).inflate(R.layout.layout_countdown_timer, null)
        countdownDialog.setContentView(countdownView)

        val textViewCountdown = countdownView.findViewById<TextView>(R.id.textViewCountdown)
        val cancel_button = countdownView.findViewById<TextView>(R.id.cancel_button)
//        val skip_button = countdownView.findViewById<TextView>(R.id.skip_button)
//        val load_button = countdownView.findViewById<TextView>(R.id.load_button)
        val textViewPhoneNumber = countdownView.findViewById<TextView>(R.id.phone_number)
        val name_text = countdownView.findViewById<TextView>(R.id.name_text)

        textViewPhoneNumber.text =
            "Contact :- " + sheetData[currentCallIndex].contactNumber.toString()
        name_text.text = "Name :- " + sheetData[currentCallIndex].name.toString()

        showEndOfCallDialog(phoneNumber)

        countdownDialog.show()

        // Start the countdown
        object : CountDownTimer(callDelay, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                textViewCountdown.text = secondsRemaining.toString() // Update countdown text
            }

            override fun onFinish() {
                // Only proceed with the call if it was not canceled
                if (!isCountdownCancelled) {
                    countdownDialog.dismiss() // Dismiss countdown dialog
                    makePhoneCall(phoneNumber) // Start the phone call
                }
            }
        }.start()


        cancel_button.setOnClickListener {
            isCountdownCancelled = true
            countdownDialog.dismiss()
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(callIntent)
            startCallDelay() // Start the delay for the next call
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun startCallDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            currentCallIndex++ // Move to the next phone number
            if (currentCallIndex < phoneNumbers.size) {
//                showCallDelayDialog(phoneNumbers[currentCallIndex]) // Show dialog for the next call
//                Toast.makeText(context, "number skiped", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "All calls completed.", Toast.LENGTH_SHORT).show()
            }
        }, callDelay) // Delay as set by the user
    }

    fun onCallEnded() {
        // Start the next call based on your logic
        if (currentCallIndex < phoneNumbers.size) {
            val nextPhoneNumber = phoneNumbers[currentCallIndex]
            currentCallIndex++ // Move to the next phone number
            //            showCallDelayDialog(nextPhoneNumber) // Show delay dialog for the next call
            startAutoCalling(nextPhoneNumber)
        } else {
            // No more numbers to call
            Toast.makeText(context, "All calls completed.", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showEndOfCallDialog(phoneNumber: String) {
        // If the dialog already exists, just update its data
        if (endOfCallDialog == null) {
            endOfCallDialog = Dialog(context).apply {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_update_caller, null)
                setContentView(view)

                val imgNext = view.findViewById<ImageView>(R.id.imgNext)
                val imgClose = view.findViewById<LinearLayout>(R.id.imgClose)
                val lnrRescheduleYourCall =
                    view.findViewById<LinearLayout>(R.id.lnrRescheduleYourCall)
                val lnrCallNote = view.findViewById<LinearLayout>(R.id.lnrCallNote)


                lnrRescheduleYourCall.setOnClickListener {
                    showDatePickerDialog()
                }

                // Save Note
                lnrCallNote.setOnClickListener {
                    val input = EditText(context).apply {
                        hint = "Enter call note"
                        inputType = InputType.TYPE_CLASS_TEXT
                    }
                    AlertDialog.Builder(context)
                        .setTitle("Call Note")
                        .setView(input)
                        .setPositiveButton("Save") { _, _ ->
                            callNote = input.text.toString()
                            Toast.makeText(context, "Note saved!", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(context, "All calls completed.", Toast.LENGTH_SHORT)
                            .show()
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


    private fun showDatePickerDialog() {
        // Get the current date as default
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Show DatePickerDialog
        DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            // After selecting a date, open the TimePickerDialog
            showTimePickerDialog(selectedYear, selectedMonth, selectedDay)
        }, year, month, day).show()
    }


    private fun showTimePickerDialog(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        // Get the current time as default
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Show TimePickerDialog
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                // Once date and time are selected, store or display them as needed
                val selectedDateTime = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)
                }

                // Format the selected date and time
                val formattedDateTime = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
                ).format(selectedDateTime.time)
                Toast.makeText(
                    context,
                    "Selected Date & Time: $formattedDateTime",
                    Toast.LENGTH_SHORT
                ).show()

                // You can save selectedDateTime to use elsewhere
            },
            hour,
            minute,
            false
        ).show() // `false` for 24-hour format; change to `true` if you want 24-hour format
    }


    class SheetItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewContactNo: TextView = itemView.findViewById(R.id.textViewContactNo)
        val textViewEmailId: TextView = itemView.findViewById(R.id.textViewEmailId)
        val textViewCompanyName: TextView = itemView.findViewById(R.id.textViewCompanyName)
        val textViewBusinessType: TextView = itemView.findViewById(R.id.textViewBusinessType)
        val imgCall: ImageView = itemView.findViewById(R.id.imgCall)
    }

    companion object {
        private const val CALL_PERMISSION_REQUEST_CODE = 100
        var data: String = ""

    }

}