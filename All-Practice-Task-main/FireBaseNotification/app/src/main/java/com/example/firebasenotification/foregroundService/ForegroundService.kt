package com.example.firebasenotification.foregroundService

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.firebasenotification.R

class ForegroundService: Service() {

    private val channelID = "Foreground Service"

    companion object{
        fun startService(context: Context,message: String)
        {
            val intent = Intent(context,ForegroundService::class.java)
            intent.putExtra("inputExtra",message)
            ContextCompat.startForegroundService(context,intent)
        }
        fun stopService(context: Context)
        {
            val intent = Intent(context,ForegroundService::class.java)
            context.stopService(intent)
        }
    }




    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notifyIntent = Intent(this,ForegroundActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(applicationContext,0,notifyIntent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE else PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this,channelID)
            .setContentTitle("Foreground Services")
            .setContentText("$input\tProcess Is Running...")
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setAutoCancel(true)
            .setTimeoutAfter(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1,notification)

        Handler(Looper.getMainLooper()).postDelayed({
            stopForeground(true)
            stopService(intent)
            stopSelf()
        },10000)


        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val serviceChannel = NotificationChannel(channelID,"Foreground Service Channel",NotificationManager.IMPORTANCE_DEFAULT)

             val manager = getSystemService(NotificationManager::class.java)
             manager.createNotificationChannel(serviceChannel)
        }
    }
}