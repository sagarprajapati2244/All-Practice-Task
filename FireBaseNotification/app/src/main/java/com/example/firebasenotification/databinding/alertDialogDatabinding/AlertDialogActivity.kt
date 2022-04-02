package com.example.firebasenotification.databinding.alertDialogDatabinding

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.databinding.editTextWithDatabinding.ModelClass
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityAlertDialog2Binding
import com.example.firebasenotification.databinding.DialogCustomBinding

class AlertDialogActivity : AppCompatActivity(){

    private lateinit var binding : ActivityAlertDialog2Binding
    private val modelClass = ModelClass(name = "", password = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alert_dialog2)

        binding.btnBindingDialog.setOnClickListener {
            showCustomDialog()
        }
    }



    private fun showCustomDialog() {
        val dialogBind : DialogCustomBinding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.dialog_custom,null,false)
        val dialogBuilder = AlertDialog.Builder(this,0).create()
        dialogBuilder.apply {
            setView(dialogBind.root)
            setCancelable(false)
        }.show()

        modelClass.name = dialogBind.txtcustNAme.text.toString().trim()
        modelClass.password = dialogBind.txtcustPAssword.text.toString().trim()
        dialogBind.user = modelClass

        dialogBind.btnEnter.setOnClickListener {
            dialogBuilder.dismiss()
            Toast.makeText(applicationContext, "Your Name Is :  ${modelClass.name} and Your Password IS : ${modelClass.password}", Toast.LENGTH_SHORT).show()

        }
    }
}