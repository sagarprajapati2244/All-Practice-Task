package com.example.firebasenotification.customFont

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityStyleTextBinding

class StyleTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStyleTextBinding
    private val str ="Radha Krishna"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_style_text)

        val typeface = Typeface.createFromAsset(this.assets,"fonts/pacifico.ttf")
        binding.tvTypeFace.text = str
        binding.tvTypeFace.textSize = 20F
        binding.tvTypeFace.typeface = typeface
    }
}