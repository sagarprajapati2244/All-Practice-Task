package com.example.firebasenotification.firebaseDbMain.realtimeDatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenotification.databinding.RvItemAuthorBinding
import com.google.firebase.database.*

class RealDbAdapter(val context: Context, private val list:ArrayList<RealModel>):RecyclerView.Adapter<RealDbAdapter.ViewHolder>() {
    class ViewHolder(val binding:RvItemAuthorBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(realModel: RealModel)
        {
            binding.realDb = realModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  binding = RvItemAuthorBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.binding.buttonDelete.setOnClickListener {
            deletes(data.id!!)
            if (context is RealTimeDBActivity)
                context.deleteValue(data.author_name,position)

        }
        holder.binding.buttonEdit.setOnClickListener {
            if (context is RealTimeDBActivity)
                context.updateValue(data)
        }
    }

    private fun deletes(id: String) {
        val dbRef = FirebaseDatabase.getInstance().reference.child("author")
        val query = dbRef.child(id)
        query.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.ref.removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Unable To Delete The Data..!", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun remove(i:Int){
        list.removeAt(i)
        notifyItemRemoved(i)
        notifyItemRangeChanged(i,itemCount-i)
    }


    override fun getItemCount(): Int {
        return list.size
    }
}