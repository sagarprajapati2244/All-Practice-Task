package com.example.firebasenotification.sqlLite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenotification.databinding.ItemRawBinding

class UserAdapter(
    private val context: Context,
    private val list: ArrayList<UserModel>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemRawBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserModel, itemClickListener: OnItemClickListener) {
            binding.user = data
            binding.imgEdit.setOnClickListener { itemClickListener.updateRecordDialog(data) }
            binding.imgDelete.setOnClickListener { itemClickListener.deleteAlertDialog(data) }

        }
    }

    interface OnItemClickListener {
        fun updateRecordDialog(data: UserModel)
        fun deleteAlertDialog(data: UserModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemRawBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}