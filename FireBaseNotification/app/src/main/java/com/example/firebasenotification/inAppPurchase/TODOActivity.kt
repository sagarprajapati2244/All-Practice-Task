package com.example.firebasenotification.inAppPurchase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityTodoactivityBinding

class TODOActivity : AppCompatActivity() {
    private lateinit var binding:ActivityTodoactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_todoactivity)



    }
}