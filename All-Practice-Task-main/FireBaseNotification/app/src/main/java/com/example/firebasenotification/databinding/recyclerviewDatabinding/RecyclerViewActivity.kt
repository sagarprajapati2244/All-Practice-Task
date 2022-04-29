package com.example.firebasenotification.databinding.recyclerviewDatabinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_recycler_view)
        
        val recyclerdata = RecyclerItemData.ItemData(this)
        binding.rvRelateData.adapter = RecyclerAdapter(this,recyclerdata)
        binding.rvRelateData.layoutManager = LinearLayoutManager(this)
        binding.rvRelateData.setHasFixedSize(true)
    }
}