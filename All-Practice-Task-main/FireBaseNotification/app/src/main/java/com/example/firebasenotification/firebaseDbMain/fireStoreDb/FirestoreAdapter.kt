package com.example.firebasenotification.firebaseDbMain.fireStoreDb

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenotification.databinding.RvFirestoreItemBinding

class FirestoreAdapter(private val context: Context, private val list: ArrayList<FirestoreModel>) :
    RecyclerView.Adapter<FirestoreAdapter.ViewHolder>() {
    class ViewHolder(val binding: RvFirestoreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fireStoreModel: FirestoreModel) {
            binding.realDb = fireStoreModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvFirestoreItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.binding.buttonDelete.setOnClickListener {
            if (context is FirestoreDbActivity)
                context.deleteValue(position, data.author_name)
        }
        holder.binding.buttonEdit.setOnClickListener {
            if (context is FirestoreDbActivity) {
                context.updateValue(data)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}