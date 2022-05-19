package com.example.firebasenotification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.androidTask.activity.TaskActivity
import com.example.firebasenotification.bottomNavigation.BottomActivity
import com.example.firebasenotification.broadcastReceiver.BroadcastActivity
import com.example.firebasenotification.customFont.StyleTextActivity
import com.example.firebasenotification.databinding.ActivityMainBinding
import com.example.firebasenotification.databinding.MainDataBindingActivity
import com.example.firebasenotification.firebaseDbMain.FirebaseDbMainActivity
import com.example.firebasenotification.foregroundService.ForegroundActivity
import com.example.firebasenotification.inAppPurchase.InAPPPurchaseSubActivity
import com.example.firebasenotification.notification.FirstActivity
import com.example.firebasenotification.objectDatabase.NoteActivity
import com.example.firebasenotification.sqlLite.CrudActivity

class MainActivity : AppCompatActivity() ,View.OnClickListener{
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.setVariable(BR.onClick,this)

    }

    override fun onClick(v: View) {
        when(v.id)
        {
            R.id.btn_Firebase_Notification -> {
                val intent = Intent(this,FirstActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_Sqllite_Database -> {
                val intent = Intent(this,CrudActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_DataBinding -> {
                val intent = Intent(this, MainDataBindingActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_ObjectBoxDatabase -> {
                val intent = Intent(this, NoteActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_BottomNavigation -> {
                val intent = Intent(this,BottomActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_ForegroundService ->{
                val intent = Intent(this,ForegroundActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_BroadcastReceiver ->{
                val intent = Intent(this,BroadcastActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_Android_Task ->{
                val intent = Intent(this, TaskActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_InAppPurchase ->{
                val intent = Intent(this, InAPPPurchaseSubActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_Custom_Font ->{
                val intent = Intent(this, StyleTextActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_Firebase_Db ->{
                val intent = Intent(this, FirebaseDbMainActivity::class.java)
                startActivity(intent)
            }

        }

    }
}