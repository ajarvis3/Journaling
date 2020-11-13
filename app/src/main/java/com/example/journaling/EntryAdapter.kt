package com.example.journaling

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.journaling.data.JournalDatabase
import com.example.journaling.data.Responses
import com.example.journaling.data.TopicPrompts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException
import java.util.*
import kotlin.math.log

/**
 * Adapter for an Entry
 * Each ViewHolder holds a prompt and its response
 * Same idea as the other adapters
 */
class EntryAdapter(private var data: Array<TopicPrompts>, private val context: Context, private val entry: String) : RecyclerView.Adapter<EntryAdapter.MyViewHolder>() {

    class MyViewHolder(val myView: View) : RecyclerView.ViewHolder(myView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false) as View
        return EntryAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryAdapter.MyViewHolder, position: Int) {
        try {
            val view = holder.itemView as ViewGroup
            val textView = view.getChildAt(0) as TextView
            val edittext = view.getChildAt(1) as EditText
            val response = data[position]
            val promptId = data[position].uid
            textView.text = response.prompt

            GlobalScope.launch (Dispatchers.IO) {
                val db = JournalDatabase.getDb(context)
                var response: Responses? = db.responsesDao().get(entry, promptId)

                withContext(Dispatchers.Main) {
                    // Sets EditText's text
                    if (response == null)
                        edittext.setText("")
                    else
                        edittext.setText(response!!.response)

                    edittext.setOnFocusChangeListener { _, _ ->
                        // Create a new response if none was previously created
                        // Otherwise use same info as old response but update text
                        if (response == null) {
                            val newResponse = Responses(
                                UUID.randomUUID().toString(),
                                promptId,
                                entry,
                                edittext.text.toString()
                            )
                            response = newResponse
                            GlobalScope.launch(Dispatchers.IO) {
                                db.responsesDao().insert(newResponse)
                            }
                        } else {
                            val newResponse = Responses(
                                response!!.uid,
                                response!!.prompt,
                                response!!.entry,
                                edittext.text.toString()
                            )
                            GlobalScope.launch(Dispatchers.IO) {
                                db.responsesDao().update(newResponse)
                            }
                        }
                    }
                }
            }
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

    /**
     * Getter for data
     */
    fun getData() = data

    /**
     * Updates the data
     * @param newData the updated data
     */
    fun setData(newData: Array<TopicPrompts>) {
        data = newData
    }


}