package com.example.journaling.data

import androidx.room.*

@Dao
interface ResponsesDao {
    @Query("SELECT * FROM responses")
    fun getAll(): List<Responses>

    @Query("SELECT * FROM responses WHERE entry LIKE :entry AND prompt LIKE :prompt")
    fun get(entry: String, prompt: String): Responses?

    @Query("SELECT * FROM responses WHERE entry LIKE :entry")
    fun get(entry: String): List<Responses>

    @Insert
    fun insert(vararg prompts: Responses)

    @Update
    fun update(response: Responses)

    @Delete
    fun delete(prompts: Responses)
}