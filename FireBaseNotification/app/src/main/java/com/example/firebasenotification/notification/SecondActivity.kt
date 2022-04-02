package com.example.firebasenotification.notification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySecondBinding

    companion object{
        const val notify_title : String = "notify_title"
        const val notify_content : String = "notify_content"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_second)
        updateUI(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        updateUI(intent)
    }

    private fun updateUI(intent: Intent?) {
        val title = (intent?.extras?.get(notify_title)) as String?
        val content = (intent?.extras?.get(notify_content)) as String?
        binding.tvBody.text = title
        binding.tvTitle.text = content
    }
}