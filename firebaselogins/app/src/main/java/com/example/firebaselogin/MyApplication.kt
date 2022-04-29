package com.example.firebaselogin

import android.app.Application
import com.ups.firebaselib.FirebaseFacebookAuth
import com.ups.firebaselib.FirebaseGoogleAuth
import com.ups.firebaselib.FirebasePhoneAuth

class MyApplication :Application(){
    lateinit var firebaseGoogleAuth: FirebaseGoogleAuth
    lateinit var firebasePhoneAuth: FirebasePhoneAuth
    lateinit var firebaseFacebookAuth: FirebaseFacebookAuth
    override fun onCreate() {
        super.onCreate()
        firebaseGoogleAuth = FirebaseGoogleAuth(this)
        firebasePhoneAuth = FirebasePhoneAuth(this)
        firebaseFacebookAuth = FirebaseFacebookAuth(this)
    }
}