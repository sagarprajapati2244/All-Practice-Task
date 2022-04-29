package com.example.firebaselogin

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebaselogin.databinding.ActivityMainBinding
import com.example.firebasemessegingutils.FirebaseNotification

@SuppressLint("UnspecifiedImmutableFlag")
class MainActivity : AppCompatActivity() {
    private var firebaseNotification: FirebaseNotification? = null
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        firebaseNotification = (application as MyApplication).firebaseNotification

        //Firebase Notification
        binding.notifySimpleBtn.setOnClickListener {
            simpleNotification()
            firebaseNotification?.getToken()
        }
        binding.notifySImageBtn.setOnClickListener {
            simpleImageNotification()
        }
        binding.notifyUImageBtn.setOnClickListener {
            urlImageNotification()
        }

    }

    private fun urlImageNotification() {
        val notifyTitle = binding.edtTitle.text.toString()
        val notifyMessage = binding.edtMessage.text.toString()
        val notifyUrl = binding.edtImgUrl.text.toString()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title", notifyTitle)
        intent.putExtra("message", notifyMessage)
        intent.putExtra("image", notifyUrl)
        intent.action = Intent.ACTION_VIEW
        val pendingIntent =
            PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        firebaseNotification?.showImageUrlNotification(this,
            notifyTitle,
            notifyMessage,
            100,
            pendingIntent,
            notifyUrl,
            R.drawable.img)

    }

    private fun simpleImageNotification() {
        val notifyTitle = binding.edtTitle.text.toString()
        val notifyMessage = binding.edtMessage.text.toString()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title", notifyTitle)
        intent.putExtra("message", notifyMessage)
        val pendingIntent =
            PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        firebaseNotification?.showImageNotification(this,
            notifyTitle,
            notifyMessage,
            100,
            pendingIntent,
            R.drawable.img)
    }


    private fun simpleNotification() {

        val notifyTitle = binding.edtTitle.text.toString()
        val notifyMessage = binding.edtMessage.text.toString()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title", notifyTitle)
        intent.putExtra("message", notifyMessage)
        val pendingIntent =
            PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        firebaseNotification?.simpleNotification(this,
            notifyTitle,
            notifyMessage,
            100,
            pendingIntent,
            R.drawable.img)

    }

}