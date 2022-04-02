package com.example.firebasenotification.bottomNavigation.fragments

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.firebasenotification.R
import com.example.firebasenotification.bottomNavigation.BottomActivity
import com.example.firebasenotification.databinding.FragmentNotificationBinding
import com.example.firebasenotification.notification.FirstActivity
import com.example.firebasenotification.notification.SecondActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.net.URL
import java.util.*

class NotificationFragment : Fragment() {

    private lateinit var binding:FragmentNotificationBinding
    private val channelId = "notification_channel"
    private val channelName = "com.example.firebaseNotification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_notification,container,false)
        val view = binding.root
        // Inflate the layout for this fragment
        getToken()
        binding.btnretrive.setOnClickListener {
            getActivityIntent()
            getImageNotification()
        }
        if (activity != null && activity is BottomActivity)
        {
            (activity as BottomActivity).supportActionBar?.title = getString(R.string.notification)
            (activity as BottomActivity).checkActiveState("Notification")
        }

        return view
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getImageNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationBuilder: NotificationCompat.Builder?
            val notificationChannel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH)
            FirstActivity.bitmap = BitmapFactory.decodeResource(resources, R.drawable.firebase_logo)
            try {
                val url = URL("http://....")
                FirstActivity.bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: IOException) {
                println(e)
            }
            val intent = Intent(requireActivity(), FirstActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(requireActivity().applicationContext,
                Calendar.getInstance().timeInMillis.toInt(),intent,0)
            notificationBuilder = NotificationCompat.Builder(requireActivity(),channelId)
                .setContentTitle("Big Picture Notification")
                .setContentText("This is Big Notification")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(FirstActivity.bitmap))
                .addAction(R.drawable.firebase_logo,"Show Activity",pendingIntent)
            val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(0,notificationBuilder.build())
        }
        else
        {
            val notificationBuilders: NotificationCompat.Builder?
            try {
                val url = URL("http://....")
                FirstActivity.bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: IOException) {
                println(e)
            }
            FirstActivity.bitmap = BitmapFactory.decodeResource(resources, R.drawable.firebase_logo)
            val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val bigPictureNotification = NotificationCompat.BigPictureStyle()
            bigPictureNotification.bigPicture(FirstActivity.bitmap).build()
            val intent = Intent(requireActivity(), NotificationFragment::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(requireActivity(),
                Calendar.getInstance().timeInMillis.toInt(),intent,0)

            notificationBuilders = NotificationCompat.Builder(requireActivity(),channelId)
                .setContentTitle("Big Picture Notification")
                .setContentText("This is Big Notification")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setStyle(bigPictureNotification)
                .addAction(R.drawable.firebase_logo,"Show Activity",pendingIntent)

            notificationManager.notify(0,notificationBuilders.build())

        }
    }

    private fun getActivityIntent() {
        createNotification("Android","Android Firebase","Android",
            NotificationCompat.PRIORITY_HIGH,100)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotification(title: String, content: String, channelId: String, priority: Int, notificationId: Int) {
        val intent = Intent(requireActivity(), SecondActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val extras = Bundle()
        extras.putString(SecondActivity.notify_title,title)
        extras.putString(SecondActivity.notify_content,content)
        intent.putExtras(extras)
        intent.action = Intent.ACTION_VIEW


        val pendingIntent = PendingIntent.getActivity(requireActivity(),notificationId,intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(requireActivity(),channelId)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground))
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setPriority(priority)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(500,500,500))

        with(NotificationManagerCompat.from(requireActivity())){
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

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {task ->
            if (task.isSuccessful)
            {
                val token = task.result
//                Log.e(TAG,token)
                val msg = getString(R.string.Firebase_Token,token)
//                Log.d(TAG,msg)
                Toast.makeText(requireActivity(),msg,Toast.LENGTH_SHORT).show()
                return@OnCompleteListener
            }
        })
    }
    companion object{
//        var TAG:String?="VK"
    }


}