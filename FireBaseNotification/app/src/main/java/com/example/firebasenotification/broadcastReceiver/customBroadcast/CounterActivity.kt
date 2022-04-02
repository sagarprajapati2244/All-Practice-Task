package com.example.firebasenotification.broadcastReceiver.customBroadcast

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityCounterBinding

class CounterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCounterBinding
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_counter)

        counter = intent.getIntExtra("count", 0)

        binding.btnIncrement.setOnClickListener {
            counter++
            val intent =
                Intent("com.example.firebasenotification.broadcastReceiver.customBroadcast")
            intent.putExtra("increment", "The Value Is Increment Is : \t $counter")
            intent.putExtra("counter", counter)
            Log.e("Count", counter.toString())
            sendBroadcast(intent)
        }



        binding.btnDecrement.setOnClickListener {
            counter--
            val intent =
                Intent("com.example.firebasenotification.broadcastReceiver.customBroadcast")
            intent.putExtra("decrement", "The Value Is Decrement Is : \t $counter")
            intent.putExtra("counter", counter)
            Log.e("Counter", counter.toString())
            sendBroadcast(intent)
        }

    }
}