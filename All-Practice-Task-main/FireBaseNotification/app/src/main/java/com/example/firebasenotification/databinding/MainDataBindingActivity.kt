package com.example.firebasenotification.databinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.BR
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.alertDialogDatabinding.AlertDialogActivity
import com.example.firebasenotification.databinding.editTextWithDatabinding.DataBindActivity
import com.example.firebasenotification.databinding.recyclerviewDatabinding.RecyclerViewActivity

class MainDataBindingActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityMainDataBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_data_binding)

        binding.setVariable(BR.onClick,this)
    }

    override fun onClick(v: View) {
        when(v.id)
        {
            R.id.btn_EditTextDataBind -> {
                val intent = Intent(this,DataBindActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_RecyclerDataBind -> {
                val intent = Intent(this,RecyclerViewActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_AlertDialogDataBind -> {
                val intent = Intent(this, AlertDialogActivity::class.java)
                startActivity(intent)
            }
        }
    }
}