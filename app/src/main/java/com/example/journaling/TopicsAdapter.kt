package com.example.journaling

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.journaling.data.Topics

class TopicsAdapter(private var data: Array<Topics>) : RecyclerView.Adapter<TopicsAdapter.MyViewHolder>() {

    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsAdapter.MyViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.my_text_view, parent, false) as TextView
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val text = data[position].name
        holder.textView.text = text
        holder.textView.setOnClickListener {
            Log.i("HERE", text)
            val intent = Intent(it.context, TopicsActivity::class.java).apply {
                putExtra("topic", text)
            }
            it.context.startActivity(intent)
        }
    }

    fun setData(update: Array<Topics>) {
        data = update
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size


}