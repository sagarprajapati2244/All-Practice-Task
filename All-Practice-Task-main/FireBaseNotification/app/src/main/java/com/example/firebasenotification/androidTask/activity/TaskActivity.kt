package com.example.firebasenotification.androidTask.activity

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenotification.R
import com.example.firebasenotification.androidTask.Result
import com.example.firebasenotification.androidTask.Songs
import com.example.firebasenotification.androidTask.`object`.ApiClient
import com.example.firebasenotification.androidTask.adapter.TaskAdapter
import com.example.firebasenotification.databinding.ActivityTaskBinding
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("UNCHECKED_CAST")
class TaskActivity : AppCompatActivity(), TaskAdapter.OnItemClickListener {
    private lateinit var binding: ActivityTaskBinding
    private var list: List<Result> = ArrayList()
    private var showList: ArrayList<Result> = arrayListOf()
    private var taskAdapter: TaskAdapter? = null
    private val intentFilter = IntentFilter()
    private var tempStr:String = ""
    private var trackNames:String = ""
    private var manager: DownloadManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task)
        getData()
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                filterData(p0.toString())
            }

        })

        binding.floatingActionButton.setOnClickListener {
            for (i in list)
                if (i.isFavourite)
                    if (showList.contains(i))
                        Toast.makeText(this, "Already Exist In Favourite!!", Toast.LENGTH_SHORT)
                            .show() else showList.add(i)
                else
                    showList.remove(i)

            val intent = Intent(this, TaskShowActivity::class.java)
            intent.putParcelableArrayListExtra("list", showList)
            startActivity(intent)
            Log.e("Msg", showList.toString())
        }

        binding.rvData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 10 && binding.floatingActionButton.isExtended) binding.floatingActionButton.shrink()

                if (dy < -10 && !binding.floatingActionButton.isExtended) binding.floatingActionButton.extend()

                if (!recyclerView.canScrollVertically(-1)) binding.floatingActionButton.extend()

            }
        })

        intentFilter.addAction("com.example.firebasenotification")
        registerReceiver(broadcastReceiver, intentFilter)

    }


    private fun filterData(text: String) {
        val filteredNames = ArrayList<Result>()
//        list.filterTo(filterList){
//            it.trackName.lowercase().contains(str.lowercase())
//        }

        for (i in list)
            if (i.trackName!!.contains(text, ignoreCase = true) || i.isWorking) filteredNames.add(i)
        taskAdapter?.setFilter(filteredNames)

    }


    private fun getData() {
        val call: Call<ResponseBody> = ApiClient.retrofitBuilder.Data()
        call.enqueue(object : Callback<ResponseBody> {


            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Error", t.message.toString())
                Toast.makeText(
                    applicationContext,
                    "The Error IS : ${t.message.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val res = response.body()?.string()
                val songs: Songs = Gson().fromJson(res, Songs::class.java)
                list = songs.results

                list = list.sortedBy {
                    it.trackName
                }

                binding.rvData.setHasFixedSize(true)
                binding.rvData.layoutManager = LinearLayoutManager(applicationContext)
                taskAdapter = TaskAdapter(this@TaskActivity, list,this@TaskActivity)
                binding.rvData.adapter = taskAdapter
                taskAdapter!!.notifyDataSetChanged()
            }

        })
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    fun downloadMusic(previewUrl: String) {
        val direct = Environment.getExternalStorageDirectory()
        if (!direct.exists())
            direct.mkdirs()
        tempStr = previewUrl
        manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri: Uri = Uri.parse(tempStr)
        val request = DownloadManager.Request(downloadUri)
        request.setDestinationUri(Uri.fromFile(direct))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        request.setAllowedOverRoaming(false)
        request.setTitle(trackNames)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"$trackNames.mp3")
        manager!!.enqueue(request)

    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        override fun onReceive(p0: Context?, p1: Intent?) {
            val trackId: Int? = p1?.getIntExtra("listData", 0)
            for (i in list)
                if (i.trackId == trackId)
                    i.isFavourite = false
            taskAdapter?.notifyDataSetChanged()
        }

    }

    override fun onUrlPass(url: String, trackName:String) {
        tempStr = url
        trackNames = trackName
    }


}


