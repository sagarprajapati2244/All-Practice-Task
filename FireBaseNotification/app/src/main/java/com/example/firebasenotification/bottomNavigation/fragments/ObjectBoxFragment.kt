package com.example.firebasenotification.bottomNavigation.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasenotification.R
import com.example.firebasenotification.bottomNavigation.BottomActivity
import com.example.firebasenotification.databinding.DialogNotesBinding
import com.example.firebasenotification.databinding.FragmentObjectBoxBinding
import com.example.firebasenotification.objectDatabase.DataAdapter
import com.example.firebasenotification.objectDatabase.DataModel
import com.example.firebasenotification.objectDatabase.DataModel_
import com.example.firebasenotification.objectDatabase.ObjectBox
import io.objectbox.Box
import io.objectbox.kotlin.query
import io.objectbox.query.Query
import java.text.DateFormat
import java.util.*


class ObjectBoxFragment : Fragment() , DataAdapter.OnItemClickListener{
    private lateinit var binding: FragmentObjectBoxBinding
    private lateinit var notesBox: Box<DataModel>
    private lateinit var notesQuery: Query<DataModel>
    private lateinit var noteAdapter: DataAdapter

    private val listData: ArrayList<DataModel> = arrayListOf()
    var note: DataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_object_box,container,false)
        val view = binding.root

        setUpViews()
        notesBox = ObjectBox.boxStore.boxFor(DataModel::class.java)

        notesQuery = notesBox.query {
            order(DataModel_.text)
        }
        updateNotes()
        binding.btnAddNote.setOnClickListener {
            addNote()
        }
        if (activity != null)
        {
            (activity as BottomActivity).supportActionBar?.title = getString(R.string.object_box_db)
        }
        (activity as BottomActivity).checkActiveState("Object Box Db")
        // Inflate the layout for this fragment
        return view
    }
    private fun setUpViews() {
        noteAdapter = DataAdapter(requireActivity(),listData,this)
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = noteAdapter
        }

        binding.btnAddNote.apply {
            isEnabled = false
        }

        binding.edtNote.apply {
            setOnEditorActionListener(editorActionListener)
            addTextChangedListener(textChangedListener)
        }
    }



    private fun addNote() {
        val noteText = binding.edtNote.text.toString()
        binding.edtNote.setText("")

        val df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
        val comment = "Added on " + df.format(Date())

        note = DataModel(text = noteText, comment = comment, date = Date())
        notesBox.put(note!!)

        Log.e("noteBox", note!!.text.toString())
        updateNotes()
    }

    private fun updateNotes() {
        val notes = notesQuery.find()
        noteAdapter.setNotes(notes as ArrayList<DataModel>)
    }



    private fun updateNote(dataModel: DataModel) {
        val dialogBind: DialogNotesBinding =
            DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.dialog_notes, null, false)
        val dialogBuilder = AlertDialog.Builder(requireActivity(), 0).create()
        dialogBuilder.apply {
            setView(dialogBind.root)
            setCancelable(false)
        }.show()
        dialogBind.edtUpNotes.setText(dataModel.text)
        dialogBind.tvUpNote.text = dataModel.comment

        dialogBind.tvUpdate.setOnClickListener {
            val name = dialogBind.edtUpNotes.text.toString()
            dialogBind.edtUpNotes.setText("")
            val df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
            val comment = "Added on " + df.format(Date())
            dataModel.text=name
            dataModel.comment=comment
//            note = DataModel(text = name, comment = comment, date = Date())
            notesBox.put(dataModel)
            Log.e("update", note!!.text.toString())
            updateNotes()
            dialogBuilder.dismiss()
        }
        dialogBind.tvCancle.setOnClickListener {
            dialogBuilder.cancel()
        }
    }

    private val editorActionListener = TextView.OnEditorActionListener { v, i, _ ->
        if (i == EditorInfo.IME_ACTION_DONE)
        {
            addNote()
            val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            true
        }
        else
        {
            false
        }
    }

    private val textChangedListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.btnAddNote.isEnabled = p0!!.isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    override fun deleteDialog(data: DataModel, position: Int) {
        noteAdapter.remove(position).also {
            notesBox.remove(data.id)
            Log.e("Delete",data.text.toString())
        }
    }


    override fun onItemclick(data: DataModel, position: Int) {
        noteAdapter.getItem(position)?.also {
            updateNote(it)
        }
        updateNotes()
    }

}