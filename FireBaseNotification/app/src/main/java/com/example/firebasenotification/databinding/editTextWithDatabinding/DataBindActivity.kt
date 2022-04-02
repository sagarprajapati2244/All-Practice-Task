package com.example.firebasenotification.databinding.editTextWithDatabinding

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.BR
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityDataBindBinding

class DataBindActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityDataBindBinding

    private var modelClass = ModelClass(name = "", password = "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_bind)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind)

        binding.txtNAme.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                    modelClass.name = binding.txtNAme.text.toString()
                    binding.tvName.text = modelClass.name
            }

        })

        binding.txtPAssword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                    modelClass.password = binding.txtPAssword.text.toString()
                    binding.tvPassword.text = modelClass.password
            }

        })

        binding.setVariable(BR.onClick, this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_enter -> {

                if (modelClass.name.isEmpty()  && modelClass.password.isEmpty()) {
                    Toast.makeText(applicationContext, "Fill The DaTA", Toast.LENGTH_SHORT).show()
                } else {
                    binding.tvName.text = modelClass.name
                    binding.tvPassword.text = modelClass.password
                }
            }
        }
    }
}