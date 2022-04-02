package com.example.firebasenotification.databinding.recyclerviewDatabinding

import android.content.Context

object RecyclerItemData {

    fun ItemData(context: Context):ArrayList<Model>{
        val data = arrayListOf<Model>()
        for (i in 0..25)
        {
            data.add(i,Model("Radhe","Krishna"))
        }
        return data
    }

}