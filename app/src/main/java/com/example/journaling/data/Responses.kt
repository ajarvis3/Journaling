package com.example.journaling.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Contains information about responses to a specific prompt for a specific entry
 */
@Entity(foreignKeys = [ForeignKey(entity=TopicPrompts::class, parentColumns = arrayOf("uid"), childColumns = arrayOf("prompt")), ForeignKey(entity=Entries::class, parentColumns = arrayOf("uid"), childColumns = arrayOf("entry"))])
class Responses (
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "prompt") val prompt: String,
    @ColumnInfo(name = "entry") val entry: String,
    @ColumnInfo(name = "response") val response: String
)