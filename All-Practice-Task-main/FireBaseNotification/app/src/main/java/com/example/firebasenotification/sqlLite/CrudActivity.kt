package com.example.firebasenotification.sqlLite

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasenotification.BR
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityCrudBinding
import com.example.firebasenotification.databinding.DialogUpdateBinding


class CrudActivity : AppCompatActivity(), View.OnClickListener, UserAdapter.OnItemClickListener {
    private lateinit var binding: ActivityCrudBinding

    private var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_crud)

        gender = binding.rbMale.text.toString()
        binding.rgGroup.setOnCheckedChangeListener { _, i ->
            gender = if (i == R.id.rb_Male) {
                binding.rbMale.text.toString()
            } else {
                binding.rbFemale.text.toString()
            }
        }

        setDataIntoRecyclerView()

        binding.setVariable(BR.onClick, this)

    }


    private fun setDataIntoRecyclerView() {
        if (getItemList().size > 0) {
            binding.rvShowData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE

            binding.rvShowData.layoutManager = LinearLayoutManager(this)
            binding.rvShowData.adapter =
                UserAdapter(this, getItemList() as ArrayList<UserModel>, this)
        } else {
            binding.rvShowData.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun addRecord(view: View?) {
        val name = binding.edtCustomerName.text.toString()
        val number = binding.edtCustomerNumber.text.toString()

        val databaseHandler = DatabaseHandler(this)
        if (name.isNotEmpty() && number.isNotEmpty()) {
            val status = databaseHandler.addUser(UserModel(view!!.id, name, number, gender))
            Log.e("status", gender)
            if (status > -1) {
                Toast.makeText(applicationContext, "Record Saved", Toast.LENGTH_SHORT).show()
                binding.edtCustomerName.text?.clear()
                binding.edtCustomerNumber.text?.clear()

                setDataIntoRecyclerView()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Please Provide All Details Sincerely!! Don't Blank It!!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun updateRecordDialog(data: UserModel) {
        val dialogBind: DialogUpdateBinding =
            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_update, null, false)
        val dialogBuilder = AlertDialog.Builder(this, 0).create()
        dialogBuilder.apply {
            setView(dialogBind.root)
            setCancelable(false)
        }.show()


        dialogBind.edtUpCustomerName.setText(data.contact_name)
        dialogBind.edtUpCustomerNumber.setText(data.contact_number)


        if (data.gender == dialogBind.rbUpMale.text.toString()) {
            dialogBind.rbUpMale.isChecked = true
        } else {
            dialogBind.rbUpFemale.isChecked = true
        }

        dialogBind.rbUpgroup.setOnCheckedChangeListener { _, i ->
            gender = if (i == R.id.rb_upMale) {
                dialogBind.rbUpMale.text.toString()
            } else {
                dialogBind.rbUpFemale.text.toString()
            }
        }

        dialogBind.tvUpdate.setOnClickListener {
            val name = dialogBind.edtUpCustomerName.text.toString()
            val number = dialogBind.edtUpCustomerNumber.text.toString()

            val databaseHandler = DatabaseHandler(this)

            if (name.isNotEmpty() && number.isNotEmpty()) {
                val status = databaseHandler.updateUser(
                    UserModel(
                        data.userId, name, number,
                        gender
                    )
                )
                if (status > -1) {
                    Toast.makeText(
                        applicationContext,
                        "Record Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.edtCustomerName.text?.clear()
                    binding.edtCustomerNumber.text?.clear()

                    setDataIntoRecyclerView()
                    dialogBuilder.dismiss()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please Provide All Details Sincerely!! Don't Blank It!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dialogBind.tvCancle.setOnClickListener {
            dialogBuilder.cancel()
        }


    }


    override fun deleteAlertDialog(data: UserModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setMessage("Are You Sure Want To Delete ${data.contact_name}")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->

            val databaseHandler = DatabaseHandler(this)
            val status = databaseHandler.deleteUser(UserModel(data.userId, "", "", ""))
            if (status > -1) {
                Toast.makeText(applicationContext, "Record Delete Successfully", Toast.LENGTH_SHORT)
                    .show()
                setDataIntoRecyclerView()
            }
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun getItemList(): MutableList<UserModel> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewUser()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_Add_Record -> {
                addRecord(v)
            }

        }

    }


}