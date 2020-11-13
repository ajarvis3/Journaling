package com.example.journaling.data

import androidx.room.*

/**
 * Dao for Entries table
 */
@Dao
interface EntriesDao {
    @Query("SELECT * FROM entries")
    fun getAll(): List<Entries>

    @Query("SELECT * FROM entries WHERE topic LIKE :topic")
    fun get(topic: String): List<Entries>

    @Query("SELECT * FROM entries WHERE uid LIKE :id")
    fun getById(id: String): Entries

    @Insert
    fun insert(vararg prompts: Entries)

    @Update
    fun update(prompt: Entries)

    @Delete
    fun delete(prompts: Entries)

}