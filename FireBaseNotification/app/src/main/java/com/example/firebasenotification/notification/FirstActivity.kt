package com.example.firebasenotification.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityFirstBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.net.URL
import java.util.*

class FirstActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFirstBinding
    private val channelId = "notification_channel"
    private val channelName = "com.example.firebaseNotification"

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_first)

        getToken()



        binding.btnretrive.setOnClickListener {
            getActivityIntent()
            getImageNotification()
        }
    }

    private fun getActivityIntent() {
        createNotification("Android","Android Firebase","Android",NotificationCompat.PRIORITY_HIGH,100)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotification(title: String, content: String, channelId: String, priority: Int, notificationId: Int) {
        val intent = Intent(applicationContext, SecondActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val extras = Bundle()
        extras.putString(SecondActivity.notify_title,title)
        extras.putString(SecondActivity.notify_content,content)
        intent.putExtras(extras)
        intent.action = Intent.ACTION_VIEW


        val pendingIntent = PendingIntent.getActivity(applicationContext,notificationId,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(applicationContext,channelId)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground))
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setPriority(priority)
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
//                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                createNotificationChannel(channel)
            }
            notify(notificationId,builder.build())
        }

    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getImageNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationBuilder: NotificationCompat.Builder?
            val notificationChannel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH)
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.firebase_logo)
            try {
                val url = URL("http://....")
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: IOException) {
                println(e)
            }
            val intent = Intent(this, FirstActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(this,Calendar.getInstance().timeInMillis.toInt(),intent,0)
            notificationBuilder = NotificationCompat.Builder(this,channelId).setContentTitle("Big Picture Notification")
                .setContentText("This is Big Notification")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .addAction(R.drawable.firebase_logo,"Show Activity",pendingIntent)
            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(0,notificationBuilder.build())
        }
        else
        {
            try {
                val url = URL("http://....")
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: IOException) {
                println(e)
            }
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.firebase_logo)
            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val bigPictureNotification = NotificationCompat.BigPictureStyle()
            bigPictureNotification.bigPicture(bitmap).build()
            val intent = Intent(this, FirstActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(this,Calendar.getInstance().timeInMillis.toInt(),intent,0)

            val notificationBuilder = NotificationCompat.Builder(this,channelId)
                .setContentTitle("Big Picture Notification")
                .setContentText("This is Big Notification")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setStyle(bigPictureNotification)
                .addAction(R.drawable.firebase_logo,"Show Activity",pendingIntent)

            notificationManager.notify(0,notificationBuilder.build())

        }


    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {task ->
            if (task.isSuccessful)
            {
                val token = task.result
                Log.e(TAG,token)
                val msg = getString(R.string.Firebase_Token,token)
                Log.d(TAG,msg)
                Toast.makeText(baseContext,msg,Toast.LENGTH_SHORT).show()
                return@OnCompleteListener
            }
        })
    }

    companion object{
        var TAG:String?="VK"
        var bitmap:Bitmap?=null
    }


}