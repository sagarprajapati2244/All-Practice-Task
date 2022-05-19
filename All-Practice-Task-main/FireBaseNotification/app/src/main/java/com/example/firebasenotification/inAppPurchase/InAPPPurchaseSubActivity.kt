package com.example.firebasenotification.inAppPurchase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.BR
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityInApppurchaseSubBinding

class InAPPPurchaseSubActivity : AppCompatActivity() {
    private lateinit var binding:ActivityInApppurchaseSubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_in_apppurchase_sub)

        binding.cardPurchaseItem.setOnClickListener {
            val intent = Intent(this,ConsumeNonConsumeActivity::class.java)
            startActivity(intent)
        }

        binding.cardSubscribeItem.setOnClickListener {
            val intent = Intent(this,SubscriptionActivity::class.java)
            startActivity(intent)
        }
    }


}