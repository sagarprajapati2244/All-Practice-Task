package com.example.firebasenotification.broadcastReceiver.customBroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val intentFilter = IntentFilter()
    private var count: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        intentFilter.addAction("com.example.firebasenotification.broadcastReceiver.customBroadcast")
        registerReceiver(brReceiver, intentFilter)


        binding.btnNext.setOnClickListener {
            val intent = Intent(this, CounterActivity::class.java)
            intent.putExtra("count", count)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        unregisterReceiver(brReceiver)
        super.onDestroy()
    }

    private val brReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            val str: String? = p1?.getStringExtra("increment")
            binding.tvCount.text = str
            count = p1?.getIntExtra("counter", 0)

            val str1: String? = p1?.getStringExtra("decrement")
            binding.tvCounter.text = str1

        }

    }


}
