package com.example.pictureinpicturemode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureinpicturemode.databinding.ItemRowBinding

class CryptoViewAdapter(private val videoList:ArrayList<Model>,
        private val   mItemlist:ItemClickListener
                        ):RecyclerView.Adapter<CryptoViewAdapter.CryptViewHolder>() {
    class CryptViewHolder(val bind:ItemRowBinding):RecyclerView.ViewHolder(bind.root) {
        fun bind(model: Model)
        {
            bind.user = model
        }
    }

    interface ItemClickListener{
        fun onItemClick(model: Model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptViewHolder {
        val bind = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CryptViewHolder, position: Int) {
        val data = videoList[position]
        holder.bind(data)
        holder.bind.cardView.setOnClickListener { mItemlist.onItemClick(data) }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}