package com.example.journaling.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TopicPromptsDao {
    @Query("SELECT * FROM topicprompts")
    fun getAll(): List<TopicPrompts>

    @Query("SELECT * FROM topicprompts WHERE topic LIKE :name")
    fun get(name: String): List<TopicPrompts>

    @Query("SELECT * FROM topicprompts WHERE uid LIKE :id")
    fun getById(id: String): TopicPrompts

    @Insert
    fun insert(vararg prompts: TopicPrompts)

    @Delete
    fun delete(prompts: TopicPrompts)
}