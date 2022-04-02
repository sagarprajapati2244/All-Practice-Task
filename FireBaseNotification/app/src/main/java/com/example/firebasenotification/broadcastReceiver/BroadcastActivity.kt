package com.example.firebasenotification.broadcastReceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.broadcastReceiver.customBroadcast.DetailActivity
import com.example.firebasenotification.databinding.ActivityBroadcastBinding
@SuppressLint("SetTextI18n")
class BroadcastActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBroadcastBinding
    private var wifiManager:WifiManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_broadcast)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        binding.swhBtn.setOnCheckedChangeListener { _, b ->
            if (b) {
                wifiManager!!.isWifiEnabled
                binding.swhBtn.text = "Wifi is ON"
            }
            else
            {
                wifiManager!!.isWifiEnabled
                binding.swhBtn.text = "Wifi is OFF"
            }
        }

        binding.btnCustomBr.setOnClickListener {
            val intent = Intent(this,DetailActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver,intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }

    private val wifiStateReceiver:BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            when(p1?.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN))
            {
                WifiManager.WIFI_STATE_ENABLED -> {
                    binding.swhBtn.isChecked = true
                    binding.swhBtn.text = "Wifi is ON"
                    Toast.makeText(applicationContext, "WIFI IS ON", Toast.LENGTH_SHORT).show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    binding.swhBtn.isChecked = false
                    binding.swhBtn.text = "Wifi is OFF"
                    Toast.makeText(applicationContext, "WIFI IS OFF", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}