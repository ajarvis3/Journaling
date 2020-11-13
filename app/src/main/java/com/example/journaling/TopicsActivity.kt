package com.example.journaling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.journaling.data.Entries
import com.example.journaling.data.JournalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Activity for a given topic
 */
class TopicsActivity : AppCompatActivity() {
    private lateinit var mTopicsRecycler: RecyclerView
    private lateinit var mViewAdapter: RecyclerView.Adapter<*>
    private lateinit var mViewManager: RecyclerView.LayoutManager

    /**
     * Mostly the same idea as the Journals Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)
        // the id of the topic
        val topic = this.intent.getStringExtra("topic") as String

        mViewManager = LinearLayoutManager(this)

        GlobalScope.launch (Dispatchers.IO) {
            val db = JournalDatabase.getDb(applicationContext)
            val arr = db.entriesDao().get(topic).toTypedArray()
            mViewAdapter = com.example.journaling.TopicsAdapter(arr)

            withContext(Dispatchers.Main) {
                mTopicsRecycler = findViewById<RecyclerView>(R.id.entry_views).apply {
                    layoutManager = mViewManager
                    adapter = mViewAdapter
                }
            }
        }

        // Sets up button to add an entry
        val button = findViewById<Button>(R.id.add_entry_button);
        button.setOnClickListener {
            GlobalScope.launch (Dispatchers.IO) {
                val db = JournalDatabase.getDb(applicationContext);
                val id: String = UUID.randomUUID().toString()
                val entry = Entries(id, topic, "")
                db.entriesDao().insert(entry)
                withContext(Dispatchers.Main) {
                    val intent: Intent = Intent(it.context, EntryActivity::class.java).apply {
                        putExtra("id", id)
                        putExtra("topic", topic)
                    }
                    it.context.startActivity(intent)
                }
            }
        }

    }

    /**
     * Overridden to get the data again and notify of update to data
     * Could probably just do this here and never in onCreate
     */
    override fun onResume() {
        super.onResume()
        if (this::mViewAdapter.isInitialized && mViewAdapter!= null) {
            val topic = this.intent.getStringExtra("topic") as String
            GlobalScope.launch (Dispatchers.IO) {
                val db = JournalDatabase.getDb(applicationContext)
                val arr = db.entriesDao().get(topic).toTypedArray()
                withContext (Dispatchers.Main) {
                    (mViewAdapter as com.example.journaling.TopicsAdapter).setData(arr)
                    mViewAdapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}