package com.papputech.empty

import PhoneCallReceiver
import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {

    private val REQUEST_READ_PHONE_STATE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://192.168.1.10:5050/")
                        .build()

                    val api = retrofit.create(AndroidTV::class.java)
                    val response = api.pauseTV()
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(this@MainActivity, "Manual test successful", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }) {
                Text("Manual Test")
            }
        }

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                REQUEST_READ_PHONE_STATE
            )
        } else {
            registerPhoneCallReceiver()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_PHONE_STATE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    registerPhoneCallReceiver()
                } else {
                    Toast.makeText(this, "Permission denied. Cannot monitor phone calls.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registerPhoneCallReceiver() {
        val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(PhoneCallReceiver(), filter)
        Toast.makeText(this, "Phone call monitoring enabled.", Toast.LENGTH_SHORT).show()
    }
}
