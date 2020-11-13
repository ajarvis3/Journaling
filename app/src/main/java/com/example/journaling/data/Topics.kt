package com.example.journaling.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Topics for a journal
 */
@Entity
data class Topics (
    @PrimaryKey val name: String
)