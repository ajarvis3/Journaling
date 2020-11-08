package com.example.journaling

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.journaling.data.JournalDatabase
import com.example.journaling.data.Topics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException
import java.lang.IllegalStateException


class NewTopicFragment : DialogFragment() {
    internal lateinit var listener: NewTopicAdded

    interface NewTopicAdded {
        fun onDialogPositiveClick(dialog: DialogFragment, text: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var builder = AlertDialog.Builder(it)
            val textview: TextView = requireActivity().layoutInflater.inflate(R.layout.my_edit_text, null) as TextView
            builder.setView(textview)
                .setPositiveButton("Add", DialogInterface.OnClickListener {dialog, _ ->
                    listener.onDialogPositiveClick(this, textview.text.toString())
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {dialog, _ ->
                    dialog?.cancel()})

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewTopicAdded
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }
}