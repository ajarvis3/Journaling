package com.example.journaling

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException
import java.lang.IllegalStateException

class NewPromptDialog : DialogFragment() {
    internal lateinit var listener: NewPromptAdded

    interface NewPromptAdded {
        fun onDialogPositiveClick(dialog: DialogFragment, text: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var builder = AlertDialog.Builder(it)
            val textview: EditText = requireActivity().layoutInflater.inflate(R.layout.my_edit_text, null) as EditText
            builder.setView(textview)
                .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, _ ->
                    listener.onDialogPositiveClick(this, textview.text.toString())
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                    dialog?.cancel()})

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewPromptAdded
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }
}