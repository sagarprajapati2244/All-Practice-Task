package com.example.firebaselogin

import android.os.Build
import android.util.Log
import com.example.firebasemessegingutils.FirebaseNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService:FirebaseMessagingService() {
    private var firebaseNotification: FirebaseNotification?=null
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e("Firebase Token",p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {

        p0.notification?.let {
            firebaseNotification?.generateNotification(this, it.title!!, it.body!!,
                it.imageUrl
            )
            Log.e("Firebase MsgReceived", p0.from +"Data"+p0.data)
        }

    }
}