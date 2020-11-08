package com.example.journaling.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Entries::class, TopicPrompts::class, Topics::class, Responses::class), version = 3)
abstract  class JournalDatabase : RoomDatabase() {
    abstract fun topicsDao(): TopicsDao
    abstract fun topicPromptsDao(): TopicPromptsDao
    abstract fun responsesDao(): ResponsesDao
    abstract fun entriesDao(): EntriesDao

    companion object Instance {
        lateinit var db: JournalDatabase

        fun getDb(context: Context): JournalDatabase {
            if (!this::db.isInitialized) {
                db = Room.databaseBuilder(
                    context,
                    JournalDatabase::class.java, "journal-database"
                ).fallbackToDestructiveMigration().build()
            }
            return db
        }
    }
}