package com.example.firebasenotification.firebaseDbMain.fireStoreDb

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityFirestoreDbBinding
import com.example.firebasenotification.databinding.DialogAddRtdbBinding
import com.example.firebasenotification.databinding.DialogUpdateRtdbBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class FirestoreDbActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirestoreDbBinding
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var authorList: ArrayList<FirestoreModel>
    private lateinit var adapter: FirestoreAdapter
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_firestore_db)
        binding.btnADDFireDb.setOnClickListener {
            showAddDialog()
        }
        dataBase = FirebaseFirestore.getInstance()

        authorList = ArrayList()
        adapter = FirestoreAdapter(this, authorList)
        binding.rvRealTDb.layoutManager = LinearLayoutManager(this)
        binding.rvRealTDb.setHasFixedSize(true)
        getAuthorData()
        addProgressbar()

        progressBar.visibility = View.VISIBLE

    }

    private fun addProgressbar() {
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        binding.relativeMain.addView(progressBar, params)
    }

    private fun getAuthorData() {
        dataBase.collection("author")
            .get()
            .addOnCompleteListener {
                authorList.clear()
                progressBar.visibility = View.GONE
                for (i in it.result) {
                    val authorModel = FirestoreModel(i.getString("id"), i.getString("author_name"))
                    authorList.add(authorModel)
                }
                binding.rvRealTDb.adapter = adapter
            }
            .addOnFailureListener {
                Toast.makeText(this, "Unable To Load Data..!", Toast.LENGTH_SHORT).show()
            }
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
            addData(name)
            dialog.dismiss()
        }
        dialogBind.imgClose.setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun addData(name: String) {
        val id = UUID.randomUUID().toString()
        val author = HashMap<String, Any>()
        author["id"] = id
        author["author_name"] = name

        dataBase.collection("author").document(id).set(author)
            .addOnCompleteListener {
                Toast.makeText(this,
                    "Store The Data Successfully ${name.also { author["author_name"] = it }}",
                    Toast.LENGTH_SHORT).show()
                getAuthorData()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Sorry,Unable To Store The Data..!!", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun deleteValue(position: Int, authorName: String?) {
        dataBase.collection("author").document(authorList[position].id!!)
            .delete()
            .addOnCompleteListener {
                Toast.makeText(this,
                    "Successfully Delete The Data \t $authorName..!",
                    Toast.LENGTH_SHORT).show()
                getAuthorData()
            }
            .addOnFailureListener {
                Toast.makeText(this,
                    "Unable To Delete The Data \t $authorName..!",
                    Toast.LENGTH_SHORT).show()
            }
    }

    fun updateValue(fireStoreModel: FirestoreModel) {
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
        dialogBind.editTextUpName.setText(fireStoreModel.author_name)
        dialogBind.buttonUpdate.setOnClickListener {
            val name = dialogBind.editTextUpName.text.toString()
            if (TextUtils.isEmpty(name)) {
                dialogBind.editTextUpName.error = "Please Enter Name"
                return@setOnClickListener
            }
            dataBase.collection("author").document(fireStoreModel.id!!)
                .update("author_name", name)
                .addOnCompleteListener {
                    Toast.makeText(this, "Successfully Update The Data \t $name", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    getAuthorData()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Unable To Update The Data..!", Toast.LENGTH_SHORT).show()
                }
        }
        dialogBind.imgClose.setOnClickListener {
            dialog.dismiss()
        }
    }
}


