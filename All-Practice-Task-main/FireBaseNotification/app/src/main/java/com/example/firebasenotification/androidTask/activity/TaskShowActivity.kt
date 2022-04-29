package com.example.firebasenotification.androidTask.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasenotification.R
import com.example.firebasenotification.androidTask.Result
import com.example.firebasenotification.androidTask.adapter.TaskAdapter
import com.example.firebasenotification.databinding.ActivityTaskshowBinding

class TaskShowActivity : AppCompatActivity(), TaskAdapter.OnItemClickListener {
    private lateinit var binding: ActivityTaskshowBinding
    private var list: ArrayList<Result> = arrayListOf()
    private var taskAdapter: TaskAdapter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_taskshow)

        list = intent.getParcelableArrayListExtra("list")!!
        if (list.isEmpty()) {
            binding.tvNoRecord.visibility = View.VISIBLE
            binding.rvTaskShow.visibility = View.GONE
        } else {
            binding.tvNoRecord.visibility = View.GONE
            binding.rvTaskShow.visibility = View.VISIBLE

            binding.rvTaskShow.setHasFixedSize(true)
            binding.rvTaskShow.layoutManager = LinearLayoutManager(applicationContext)
            taskAdapter = TaskAdapter(this, list,this)
            binding.rvTaskShow.adapter = taskAdapter
            taskAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onUrlPass(url: String, trackName:String) {
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show()
    }

}