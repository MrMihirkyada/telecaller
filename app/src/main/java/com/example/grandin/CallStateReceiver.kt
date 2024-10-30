package com.example.grandin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.example.grandin.Adapter.SheetItemAdapter

class CallStateReceiver(private val context: Context, private val adapter: SheetItemAdapter) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        when (state) {
            TelephonyManager.EXTRA_STATE_IDLE -> {
                // Call has ended, start the next call if available
                adapter.onCallEnded()
            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                // A call is active
            }
            TelephonyManager.EXTRA_STATE_RINGING -> {
                // Incoming call received
            }
        }
    }
}
