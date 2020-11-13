package com.example.journaling.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Prompts for a specific topic
 */
@Entity (foreignKeys = [ForeignKey(entity=Topics::class, parentColumns = arrayOf("name"), childColumns = arrayOf("topic"))])
class TopicPrompts (
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "topic") val topic: String,
    @ColumnInfo(name= "prompt") val prompt: String
)