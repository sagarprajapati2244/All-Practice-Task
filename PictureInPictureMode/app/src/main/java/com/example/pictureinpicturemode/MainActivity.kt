package com.example.pictureinpicturemode

import android.content.Intent
import android.database.DatabaseUtils
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pictureinpicturemode.databinding.ActivityMainBinding
import com.example.pictureinpicturemode.databinding.ActivityPipBinding

class MainActivity : AppCompatActivity(), CryptoViewAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var videoList:ArrayList<Model>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        videoList = ArrayList()
        videoList.add(Model("radha krishna",
            "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"))
        videoList.add(Model("radha","https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"))
        val layoutManager = LinearLayoutManager(this)
        binding.rvData.layoutManager = layoutManager
        binding.rvData.adapter = CryptoViewAdapter(videoList,this)
    }

    override fun onItemClick(model: Model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startActivity(Intent(this,PipActivity::class.java).putExtra("video",model.videoUrl))
        }
    }
}