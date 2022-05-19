package com.example.firebasenotification.firebaseDbMain.realtimeDatabase

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityFirebaseSimpleLoginBinding
import com.google.firebase.auth.FirebaseAuth

class FirebaseSimpleLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirebaseSimpleLoginBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_firebase_simple_login)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, RealTimeDBActivity::class.java))
            finish()
        }
        login()
    }

    private fun login() {
        binding.loginButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.usernameInput.text.toString())) {
                binding.usernameInput.error = "Please enter username"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(binding.passwordInput.text.toString())) {
                binding.passwordInput.error = "Please enter password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(binding.usernameInput.text.toString(),
                binding.passwordInput.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, RealTimeDBActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed, please try again! ", Toast.LENGTH_LONG)
                            .show()
                    }
                }

        }
        binding.registerText.setOnClickListener {
            startActivity(Intent(this, FirebaseSimpleRegisterActivity::class.java))
        }
    }
}