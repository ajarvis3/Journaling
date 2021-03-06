package com.example.journaling.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Contains information about an entry
 */
@Entity(foreignKeys = [ForeignKey(entity=Topics::class, parentColumns = arrayOf("name"), childColumns = arrayOf("topic"))])
class Entries (
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "topic") val topic: String,
    @ColumnInfo(name = "name") val name: String
)