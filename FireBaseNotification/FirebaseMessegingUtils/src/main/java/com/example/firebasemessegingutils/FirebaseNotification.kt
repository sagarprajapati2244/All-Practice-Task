package com.example.firebasemessegingutils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.net.URL


class FirebaseNotification(private var channelName: String) {


    companion object {
        const val channelId = "notification_channel"
        var bitmap: Bitmap? = null
    }


    fun generateNotification(
        context: Context,
        title: String,
        body:String,
        image: Uri?
    ) {
        try {
            val url = URL(image.toString())
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: IOException) {
            println("data error ${e.message}")
        }


        Log.e("title",title)
        Log.e("body",body)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setLargeIcon(bitmap)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .setSummaryText(body).bigPicture(
                        bitmap
                    ).setBigContentTitle(title)
            )

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, notificationBuilder.build())

    }

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.e("token", token)
                return@OnCompleteListener
            }
        })
    }

    fun simpleNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int,
        pendingIntent: PendingIntent,
        drawableImage: Int
    ):Boolean {
        return if (title.isNotEmpty() or message.isNotEmpty()) {
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(drawableImage)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        channelId,
                        NotificationManager.IMPORTANCE_HIGH
                    )
//                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                    createNotificationChannel(channel)
                }
                notify(notificationId, builder.build())
                true
            }
        } else {
            Toast.makeText(context, "Please Fill The Blanks!!", Toast.LENGTH_SHORT).show()
            false
        }
    }


    fun showImageNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int,
        pendingIntent: PendingIntent,
        image: Int
    ):Boolean {
        return if (title.isNotEmpty() or message.isNotEmpty()) {
            val pic = BitmapFactory.decodeResource(context.resources, image)
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(image)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(pic)
                .setContentIntent(pendingIntent)
                .setStyle(
                    NotificationCompat.BigPictureStyle().setSummaryText(message).bigPicture(pic)
                        .setBigContentTitle(title)
                )
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        channelId,
                        NotificationManager.IMPORTANCE_HIGH
                    )
//                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                    createNotificationChannel(channel)
                }
                notify(notificationId, builder.build())
                true
            }
        } else {
            Toast.makeText(context, "Please Fill The Blanks!!", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun showImageUrlNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int,
        pendingIntent: PendingIntent,
        image: String,
        drawableImage:Int
    ):Boolean {
        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        try {
            val url = URL(image)
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: IOException) {
            println("data error ${e.message}")
        }

      return  if (title.isNotEmpty() or message.isNotEmpty()) {

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(drawableImage)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .setStyle(
                    NotificationCompat.BigPictureStyle().setSummaryText(message).bigPicture(
                        bitmap
                    ).setBigContentTitle(title)
                )
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        channelId,
                        NotificationManager.IMPORTANCE_HIGH
                    )
//                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                    createNotificationChannel(channel)
                }
                notify(notificationId, builder.build())
                true
            }
        } else {
            Toast.makeText(context, "Please Fill The Blanks!!", Toast.LENGTH_SHORT).show()
          false
        }
    }
}