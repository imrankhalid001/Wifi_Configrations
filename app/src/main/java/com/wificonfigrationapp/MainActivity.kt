package com.wificonfigrationapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnSubmit: Button
    private lateinit var edtssid: EditText
    private lateinit var edtpassword: EditText

    private lateinit var wifiManager: WifiManager
    private lateinit var connectivityManager: ConnectivityManager
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    private var hasScanned = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSubmit = findViewById(R.id.btnSubmit)
        edtssid = findViewById(R.id.edtssid)
        edtpassword = findViewById(R.id.edtpassword)
        wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        connectivityManager =  getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        btnSubmit.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                connect29AndAbove(edtssid.text.toString(), edtpassword.text.toString());
            } else {
                // Handle the case when the API level is below 29
                // You may choose to do nothing or use an alternative approach
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun connect29AndAbove(ssid: String, passPhrase: String) {
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(this, "Wifi is disabled please enable it to proceed", Toast.LENGTH_SHORT).show()
            val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
            launcher.launch(panelIntent)
        }
        val suggestion = WifiNetworkSuggestion.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(passPhrase)
            .setIsAppInteractionRequired(true)
            .build()

        wifiManager.removeNetworkSuggestions(listOf(suggestion))

        wifiManager.addNetworkSuggestions(listOf(suggestion))

        val intentFilter = IntentFilter(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (!intent.action.equals(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
                    return
                }
            }
        }
        this.registerReceiver(broadcastReceiver, intentFilter)
        hasScanned = false
    }



    // use this function to unregister from wifi

    @RequiresApi(Build.VERSION_CODES.Q)
    fun unregisterFromWifi(ssid: String, passPhrase: String) {
        val suggestion = WifiNetworkSuggestion.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(passPhrase)
            .setIsAppInteractionRequired(true)
            .build()

        // Remove the network suggestion to unregister from the network
        wifiManager.removeNetworkSuggestions(listOf(suggestion))
    }

}