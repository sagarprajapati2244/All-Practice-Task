package com.example.firebasenotification.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firebasenotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.net.URL


const val channelId = "notification_channel"
const val channelName = "com.example.firebasenotification"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //Step of Notification of Firebase
    //generate The Notification First one
    //then attach the notification created attach the custom layout
    //then show the notification

    override fun onNewToken(p0: String) {
        Log.e("TAG","RefreshToken : $p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification!=null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                generateNotification(
                    remoteMessage.notification!!.title!!,
                    remoteMessage.notification!!.body!!,
                    remoteMessage.notification!!.imageUrl
                )
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(body: String?, title: String?) {
        val intent = Intent(applicationContext,SecondActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val extras = Bundle()
        extras.putString(SecondActivity.notify_title, title.toString())
        extras.putString(SecondActivity.notify_content, body.toString())
        intent.putExtras(extras)
        intent.action = Intent.ACTION_VIEW

        val pendingIntent = PendingIntent.getActivity(applicationContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(applicationContext,channelId)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_launcher_foreground))
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(title.toString())
            .setContentText(body.toString())
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(500,500,500))

        with(NotificationManagerCompat.from(applicationContext)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                val channel = NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                createNotificationChannel(channel)
            }
//            notify(0,builder.build())
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0,builder.build())
    }

//
    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateNotification(title: String, message: String, imageUrl: Uri?)
    {
        var notificationBuilder: Notification.Builder?=null
        val intent = Intent(this, FirstActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        sendNotification(title,message)
//        val extras = Bundle()
//        extras.putString(SecondActivity.notify_title, title)
//        extras.putString(SecondActivity.notify_content, message)
//        intent.putExtras(extras)
//        intent.action = Intent.ACTION_VIEW


        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
        try {
            val url = URL(imageUrl.toString())
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

        } catch (e: IOException) {
            println(e)
        }


        notificationBuilder = Notification.Builder(this,channelId).setContentTitle("Big Picture Notification")
            .setContentText("This is Big Notification")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setStyle(Notification.BigPictureStyle(notificationBuilder).bigPicture(bitmap))
            .addAction(R.drawable.firebase_logo,"Show Activity",pendingIntent)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,notificationBuilder.build())

    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteViews = RemoteViews("com.example.firebasenotification",R.layout.custom_notification_layout)
        remoteViews.setTextViewText(R.id.tv_title,title)
        remoteViews.setTextViewText(R.id.tv_desc,message)
        remoteViews.setImageViewResource(R.id.notification_logo,R.drawable.firebase_logo)

        return remoteViews
    }

    companion object{
        var bitmap: Bitmap?=null
        var body:String?=null
        var title:String?=null
    }
}