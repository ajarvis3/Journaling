package com.example.journaling

import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.journaling.data.Entries
import com.example.journaling.data.JournalDatabase
import com.example.journaling.data.TopicPrompts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EntryActivity : AppCompatActivity(), NewPromptDialog.NewPromptAdded {
    lateinit var mViewManager: RecyclerView.LayoutManager
    lateinit var mViewAdapter: RecyclerView.Adapter<*>
    lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        val uid: String = this.intent.getStringExtra("id")
        val topic: String = this.intent.getStringExtra("topic")
        val title = findViewById<EditText>(R.id.entry_title)

        mViewManager = LinearLayoutManager(this)

        GlobalScope.launch (Dispatchers.IO) {
            // need to check that a response exists for each prompt
            val db = JournalDatabase.getDb(applicationContext)
            val arr = db.topicPromptsDao().get(topic).toTypedArray()
            val entryTitle = db.entriesDao().getById(uid).name
            mViewAdapter = EntryAdapter(arr, applicationContext, uid)

            withContext(Dispatchers.Main) {
                title.setText(entryTitle)
                mRecyclerView = findViewById<RecyclerView>(R.id.entry_recycler).apply {
                    layoutManager = mViewManager
                    adapter = mViewAdapter
                }
            }
        }

        val button = findViewById<Button>(R.id.add_prompt)
        button.setOnClickListener {
            NewPromptDialog().show(supportFragmentManager, null)
        }

        title.setOnFocusChangeListener { view: View, b: Boolean ->
            val newEntry = Entries(uid, topic, (view as EditText).text.toString())
            GlobalScope.launch (Dispatchers.IO) {
                val db = JournalDatabase.getDb(applicationContext)
                db.entriesDao().update(newEntry)
            }
        }

        val save = findViewById<Button>(R.id.save_button_entry) as Button
        save.setOnClickListener {
            val focus = currentFocus
            focus?.clearFocus()
        }
     }


    override fun onDialogPositiveClick(dialog: DialogFragment, text: String) {
        val topic: String = this.intent.getStringExtra("topic")
        val uid: String = UUID.randomUUID().toString()
        val prompt = TopicPrompts(uid, topic, text)
        GlobalScope.launch (Dispatchers.IO) {
            val db = JournalDatabase.getDb(applicationContext)
            db.topicPromptsDao().insert(prompt)
            val arr = db.topicPromptsDao().get(topic).toTypedArray()
            withContext(Dispatchers.Main) {
                (mViewAdapter as EntryAdapter).setData(arr)
                mViewAdapter.notifyDataSetChanged()
            }
        }
    }
}