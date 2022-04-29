package com.example.firebasenotification.bottomNavigation.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenotification.R
import com.example.firebasenotification.bottomNavigation.BottomActivity
import com.example.firebasenotification.databinding.DialogUpdateBinding
import com.example.firebasenotification.databinding.FragmentSqliteBinding
import com.example.firebasenotification.sqlLite.DatabaseHandler
import com.example.firebasenotification.sqlLite.UserAdapter
import com.example.firebasenotification.sqlLite.UserModel


class SQLiteFragment : Fragment(),  UserAdapter.OnItemClickListener {

    private lateinit var binding: FragmentSqliteBinding

    private var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sqlite,container,false)
        val view = binding.root
        // Inflate the layout for requireActivity() fragment
        gender = binding.rbMale.text.toString()
       onclickEvent()
        if (activity != null)
        {
            (activity as BottomActivity).supportActionBar?.title = getString(R.string.sqlite_database)
        }
        (activity as BottomActivity).checkActiveState("Sqlite Database")
        setDataIntoRecyclerView()
        return view
    }

    private fun onclickEvent() {
        binding.rgGroup.setOnCheckedChangeListener { _, i ->
            gender = if (i == R.id.rb_Male) {
                binding.rbMale.text.toString()
            } else {
                binding.rbFemale.text.toString()
            }
        }
        binding.btnAddRecord.setOnClickListener {
            addRecord()
        }
        binding.floatingActionButton.setOnClickListener {
            binding.crdFillData.visibility = View.VISIBLE
            binding.floatingActionButton.visibility = View.GONE
        }
    }

    private fun setDataIntoRecyclerView() {
        if (getItemList().size > 0) {
            binding.rvShowData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE
            binding.rvShowData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 10 && binding.floatingActionButton.isExtended) {
                        binding.floatingActionButton.shrink()
                    }


                    if (dy < -10 && !binding.floatingActionButton.isExtended) {
                        binding.floatingActionButton.extend()
                    }


                    if (!recyclerView.canScrollVertically(-1)) {
                        binding.floatingActionButton.extend()
                    }
                }
            })

            binding.rvShowData.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvShowData.adapter =
                UserAdapter(requireActivity(), getItemList() as ArrayList<UserModel>, this)
        } else {
            binding.rvShowData.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun addRecord() {
        val name = binding.edtCustomerName.text.toString()
        val number = binding.edtCustomerNumber.text.toString()

        val databaseHandler = DatabaseHandler(requireActivity())
        if (name.isNotEmpty() && number.isNotEmpty()) {
            val status = databaseHandler.addUser(UserModel(requireView().id, name, number, gender))
            Log.e("status", gender)
            if (status > -1) {
                Toast.makeText(requireActivity(), "Record Saved", Toast.LENGTH_SHORT).show()
                binding.edtCustomerName.text?.clear()
                binding.edtCustomerNumber.text?.clear()

                setDataIntoRecyclerView()
                binding.crdFillData.visibility = View.GONE
                binding.floatingActionButton.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(
                requireActivity(),
                "Please Provide All Details Sincerely!! Don't Blank It!!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun updateRecordDialog(data: UserModel) {
        val dialogBind: DialogUpdateBinding =
            DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.dialog_update, null, false)
        val dialogBuilder = AlertDialog.Builder(requireActivity(), 0).create()
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

            val databaseHandler = DatabaseHandler(requireActivity())

            if (name.isNotEmpty() && number.isNotEmpty()) {
                val status = databaseHandler.updateUser(
                    UserModel(
                        data.userId, name, number,
                        gender
                    )
                )
                if (status > -1) {
                    Toast.makeText(
                        requireActivity(),
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
                    requireActivity(),
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
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Delete Record")
        builder.setMessage("Are You Sure Want To Delete ${data.contact_name}")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->

            val databaseHandler = DatabaseHandler(requireActivity())
            val status = databaseHandler.deleteUser(UserModel(data.userId, "", "", ""))
            if (status > -1) {
                Toast.makeText(requireActivity(), "Record Delete Successfully", Toast.LENGTH_SHORT)
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
        val databaseHandler = DatabaseHandler(requireActivity())

        return databaseHandler.viewUser()
    }


}