package com.example.firebasenotification.foregroundService

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityForegroundBinding

class ForegroundActivity : AppCompatActivity() {

    private lateinit var binding:ActivityForegroundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_foreground)

        binding.btnStartService.setOnClickListener {
            val input = binding.edtData.text.toString()
            if (input.isEmpty())
            {
                binding.edtData.error = "Please Fill The Blank Data"
            }
            else
            {
            val intent = Intent(this,ForegroundService::class.java)
            intent.putExtra("inputExtra",input)
            ContextCompat.startForegroundService(this,intent)
                binding.edtData.text!!.clear()

//            ForegroundService.startService(this,"Foreground Service Is Running....")
            }
        }



        binding.btnStopService.setOnClickListener {
            val intent = Intent(this,ForegroundService::class.java)
            stopService(intent)

//            ForegroundService.stopService(this)
        }


    }
}