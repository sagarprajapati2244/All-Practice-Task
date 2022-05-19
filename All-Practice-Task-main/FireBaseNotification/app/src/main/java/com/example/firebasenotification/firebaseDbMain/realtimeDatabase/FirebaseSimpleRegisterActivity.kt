package com.example.firebasenotification.firebaseDbMain.realtimeDatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityFirebaseSimpleRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_firebase_simple_register.*

class FirebaseSimpleRegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFirebaseSimpleRegisterBinding
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_firebase_simple_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("author")

        register()
    }

    private fun register() {
        binding.registerButton.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.firstnameInput.text.toString()) -> {
                   binding.firstnameInput.error = "Please enter first name "
                    return@setOnClickListener
                }
                TextUtils.isEmpty(binding.lastnameInput.text.toString()) -> {
                    binding.lastnameInput.error = "Please enter last name "
                    return@setOnClickListener
                }
                TextUtils.isEmpty(binding.usernameInput.text.toString()) -> {
                    binding.usernameInput.error = "Please enter user name "
                    return@setOnClickListener
                }
                TextUtils.isEmpty(binding.passwordInput.text.toString()) -> {
                    binding.passwordInput.error = "Please enter password "
                    return@setOnClickListener
                }
            }



            auth.createUserWithEmailAndPassword(binding.usernameInput.text.toString(),binding.passwordInput.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))
                        currentUSerDb?.child("author_name")?.setValue(firstnameInput.text.toString())
//                        currentUSerDb?.child("lastname")?.setValue(lastnameInput.text.toString())

                        startActivity(Intent(this, FirebaseSimpleLoginActivity::class.java))
                        Toast.makeText(this, "Registration Success. ", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(this, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this, FirebaseSimpleLoginActivity::class.java))
        }


    }
}