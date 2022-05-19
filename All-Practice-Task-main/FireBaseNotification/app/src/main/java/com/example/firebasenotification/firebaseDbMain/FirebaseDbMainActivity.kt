package com.example.firebasenotification.firebaseDbMain

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityFirebaseDbMainBinding
import com.example.firebasenotification.firebaseDbMain.fireStoreDb.FirestoreDbActivity
import com.example.firebasenotification.firebaseDbMain.realtimeDatabase.FirebaseSimpleRegisterActivity

class FirebaseDbMainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFirebaseDbMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_firebase_db_main)

        binding.cardRealDb.setOnClickListener {
            val intent = Intent(this, FirebaseSimpleRegisterActivity::class.java)
            startActivity(intent)
        }
        binding.cardFireStoreDb.setOnClickListener {
            val intent = Intent(this, FirestoreDbActivity::class.java)
            startActivity(intent)
        }
    }
}