package com.example.firebasenotification.objectDatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.firebasenotification.R


class NoteAdapter(private val context: Context) :BaseAdapter(){
    private val list: MutableList<DataModel> = mutableListOf()

    class NoteViewHolder(view: View){
        val text :TextView = view.findViewById(R.id.tviNote)
        val comment : TextView = view.findViewById(R.id.tvvNote)
        val imgDelete : ImageView = view.findViewById(R.id.ivdelete)
    }

    fun setNotes(notes:List<DataModel>)
    {
        list.apply {
            clear()
            addAll(notes)
        }
        notifyDataSetChanged()
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): DataModel? {
        return if (p0 >= 0 && p0 < list.size)
        {
            list[p0]
        }
        else
        {
            null
        }
    }

    override fun getItemId(p0: Int): Long {
        return list[p0].id
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = p1?:LayoutInflater.from(p2!!.context).inflate(R.layout.item_notes,p2,false).also {
            it.tag = NoteViewHolder(it)
        }
        val holder =view.tag as NoteViewHolder



        val note = getItem(p0)
        if (note!= null)
        {
            holder.text.text =note.text
            holder.comment.text = note.comment

        }
        else
        {
            holder.text.text = ""
            holder.comment.text = ""
        }
        return view
    }
}