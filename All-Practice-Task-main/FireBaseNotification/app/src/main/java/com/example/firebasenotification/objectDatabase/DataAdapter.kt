package com.example.firebasenotification.objectDatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenotification.databinding.ItemNotesBinding


class DataAdapter(private val context: Context, private val list: ArrayList<DataModel>, private var listener: OnItemClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>(){

    
    class ViewHolder(private val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataModel, itemClickListener: OnItemClickListener) {
            binding.users = data
            binding.ivdelete.setOnClickListener { itemClickListener.deleteDialog(data,adapterPosition) }
            itemView.setOnClickListener{
                itemClickListener.onItemclick(data,adapterPosition)
            }
        }
    }

    fun setNotes(notes:ArrayList<DataModel>)
    {
        list.apply {
            clear()
            addAll(notes)
        }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun deleteDialog(data: DataModel,position: Int)
        fun onItemclick(data: DataModel,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemNotesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data,listener)
    }

    fun getCount(): Int {
        return list.size
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemId(p0: Int): Long {
        return list[p0].id
    }

    fun remove(i:Int){
        list.removeAt(i)
        notifyItemRemoved(i)
        notifyItemRangeChanged(i,getCount()-i)
    }

    fun getItem(i: Int): DataModel? {
        return if (i >= 0 && i < list.size) {
            list[i]
        } else {
            null
        }
    }




}