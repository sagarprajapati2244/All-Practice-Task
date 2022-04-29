package com.example.firebaselogin

import android.app.Application
import com.example.firebasemessegingutils.FirebaseNotification


class MyApplication : Application() {
    lateinit var firebaseNotification: FirebaseNotification

    companion object {
        const val channelName = "com.example.firebaselogin"
    }

    override fun onCreate() {
        super.onCreate()
        firebaseNotification = FirebaseNotification(channelName)
    }
}