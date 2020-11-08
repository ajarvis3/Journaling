package com.example.journaling.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TopicsDao {
    @Query("SELECT * FROM topics")
    fun getAll(): List<Topics>

    @Insert
    fun insert(vararg topics: Topics)

    @Delete
    fun delete(topic: Topics)
}