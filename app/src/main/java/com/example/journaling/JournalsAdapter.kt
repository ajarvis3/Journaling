package com.example.journaling

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journaling.data.Topics

/**
 * Adapter used for when topics are the subject of a recyclerview
 * Used by Journals Activity
 */
class JournalsAdapter(private var data: Array<Topics>) : RecyclerView.Adapter<JournalsAdapter.MyViewHolder>() {
    /**
     * Used to follow conventions of Android Documentation
     */
    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    /**
     * Creates the viewholder with the specified layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalsAdapter.MyViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.my_text_view, parent, false) as TextView
        return MyViewHolder(textView)
    }

    /**
     * Binds data to a viewholder
     * @param holder the viewholder to which data is being bound
     * @param position the data position that will be bound to the viewholder
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text = data[position].name
        holder.textView.text = text
        // Sets up on click listener to launch the topic
        holder.textView.setOnClickListener {
            val intent = Intent(it.context, TopicsActivity::class.java).apply {
                putExtra("topic", text)
            }
            it.context.startActivity(intent)
        }
    }

    /**
     * Updates data set
     * @param update the new data
     */
    fun setData(update: Array<Topics>) {
        data = update
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size


}