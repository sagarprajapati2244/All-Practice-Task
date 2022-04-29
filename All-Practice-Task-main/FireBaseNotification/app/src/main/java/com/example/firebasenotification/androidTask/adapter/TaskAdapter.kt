package com.example.firebasenotification.androidTask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenotification.androidTask.Result
import com.example.firebasenotification.androidTask.activity.TaskActivity
import com.example.firebasenotification.databinding.ItemListRvBinding
import com.squareup.picasso.Picasso


class TaskAdapter(
    private val context: Context,
    private var list: List<Result>,
    private var listener: OnItemClickListener
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private var mediaPlayer: MediaPlayer? = null
    private var length: Int = 0
    private var selectedId: Int = -1


    class ViewHolder(
        val binding: ItemListRvBinding,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resultsItem: Result) {
            binding.song = resultsItem
            binding.btnPlay.setOnClickListener {
                onItemClickListener.onUrlPass(
                    resultsItem.previewUrl.toString(),
                    resultsItem.trackName.toString()
                )
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.binding.ivFav.setOnCheckedChangeListener { _, b ->
            if (b) {
                holder.binding.song?.isFavourite = true
                data.isFavourite = true
                Log.e("checked", data.trackName.toString())

            } else {
                holder.binding.song?.isFavourite = false
                data.isFavourite = false
                val intent = Intent("com.example.firebasenotification")
                intent.putExtra("listData", data.trackId)
                context.sendBroadcast(intent)
            }
        }

        if (data.isPlaying) {
            holder.binding.btnPlay.visibility = View.GONE
            holder.binding.btnPause.visibility = View.VISIBLE
        } else {
            holder.binding.btnPlay.visibility = View.VISIBLE
            holder.binding.btnPause.visibility = View.GONE
        }



        holder.binding.btnPlay.setOnClickListener {
            val audio = data.previewUrl
            listener.onUrlPass(data.previewUrl.toString(), data.trackName.toString())
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
                list[selectedId].isPlaying = false
                mediaPlayer = null
            }

            selectedId = position
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                mediaPlayer!!.setDataSource(audio)
                mediaPlayer!!.prepareAsync()
                mediaPlayer!!.setOnPreparedListener {
                    it.start()
                }
                data.isPlaying = true
                notifyDataSetChanged()
                if (context is TaskActivity)
                    context.downloadMusic(data.previewUrl.toString())
                Toast.makeText(context, "Audio started playing..", Toast.LENGTH_SHORT).show()
            } else {
                mediaPlayer!!.seekTo(length)
                mediaPlayer!!.start()
                data.isPlaying = true
                holder.binding.btnPlay.visibility = View.GONE
                holder.binding.btnPause.visibility = View.VISIBLE
            }


        }
        holder.binding.btnPause.setOnClickListener {
            selectedId = position
            mediaPlayer!!.pause()
            length = mediaPlayer!!.currentPosition
            data.isPlaying = false
            holder.binding.btnPlay.visibility = View.VISIBLE
            holder.binding.btnPause.visibility = View.GONE
            Toast.makeText(context, "Audio Has been paused", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()
        }

        Picasso.get().load(data.artworkUrl100).into(holder.binding.imgPic)
    }


    override fun getItemCount(): Int {
        return list.size
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setFilter(filterList: ArrayList<Result>) {
        list = filterList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onUrlPass(url: String, trackName: String)
    }

}







