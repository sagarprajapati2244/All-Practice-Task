package com.example.firebasenotification.firebaseDbMain.realtimeDatabase

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityRealTimeDbactivityBinding
import com.example.firebasenotification.databinding.DialogAddRtdbBinding
import com.example.firebasenotification.databinding.DialogUpdateRtdbBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RealTimeDBActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealTimeDbactivityBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var adapter: RealDbAdapter
    private lateinit var authorList: ArrayList<RealModel>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase:FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_real_time_dbactivity)

        authorList = ArrayList()
        adapter = RealDbAdapter(this, authorList)
        binding.rvRealTDb.layoutManager = LinearLayoutManager(this)
        binding.rvRealTDb.setHasFixedSize(true)
        binding.btnADDRtDb.setOnClickListener {
            showAddDialog()
        }
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase.getReference("author")
        getUserData()

    }

    private fun getUserData() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                authorList.clear()
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val author = i.getValue(RealModel::class.java)
                        authorList.add(author!!)
                    }
                    binding.rvRealTDb.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RealTimeDBActivity, "The Error Is: $error", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun showAddDialog() {
        val dialogBind: DialogAddRtdbBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
            R.layout.dialog_add_rtdb,
            null,
            false)
        val dialog = AlertDialog.Builder(this, 0).create()
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setView(dialogBind.root)
            setCancelable(false)
        }.show()
        dialogBind.buttonAdd.setOnClickListener {
            val name = dialogBind.editTextName.text.toString().trim()

            if (name.isEmpty()) {
                dialogBind.editTextName.error = "Please Enter Name"
                return@setOnClickListener
            }



            val authorId = dbRef.push().key

            val author = RealModel(authorId, name)

            dbRef.child(authorId!!).setValue(author).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    Toast.makeText(this,
                        "Author Name Add Successfully \t ${author.author_name}",
                        Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this,
                        "Author Name Unable To Add  \t ${author.author_name}",
                        Toast.LENGTH_SHORT).show()
                }

            }
            dialog.dismiss()
        }
        dialogBind.imgClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun deleteValue(authorName: String, position: Int) {
        adapter.remove(position)
        Toast.makeText(this, "Successfully Delete The Data \t $authorName..!", Toast.LENGTH_SHORT)
            .show()
    }

    fun updateValue(realModel: RealModel) {
        val dialogBind: DialogUpdateRtdbBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
            R.layout.dialog_update_rtdb,
            null,
            false)
        val dialog = AlertDialog.Builder(this, 0).create()
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setView(dialogBind.root)
            setCancelable(false)
        }.show()
        dialogBind.editTextUpName.setText(realModel.author_name)
        dialogBind.buttonUpdate.setOnClickListener {
            val name = dialogBind.editTextUpName.text.toString()
            if (TextUtils.isEmpty(name)) {
                dialogBind.editTextUpName.error = "Please Enter Name"
                return@setOnClickListener
            }
            val ref = FirebaseDatabase.getInstance().getReference("author")

            val author = RealModel(realModel.id, name)

            ref.child(realModel.id!!).setValue(author)

            Toast.makeText(this,
                "Successfully Update the Data \t $name",
                Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialogBind.imgClose.setOnClickListener {
            dialog.dismiss()
        }
    }

}