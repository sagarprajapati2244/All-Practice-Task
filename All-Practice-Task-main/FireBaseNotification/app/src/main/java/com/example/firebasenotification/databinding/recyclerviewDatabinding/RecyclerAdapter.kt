package com.example.firebasenotification.databinding.recyclerviewDatabinding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenotification.databinding.RecyclerItemBinding

class RecyclerAdapter(val context: Context, private val list:ArrayList<Model>):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
//    private lateinit var binding: RecyclerItemBinding

    class ViewHolder(private val binding:RecyclerItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data:Model)
        {
            binding.datarecycler = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val  binding = RecyclerItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}