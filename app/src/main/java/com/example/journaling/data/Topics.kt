package com.example.journaling.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Topics (
    @PrimaryKey val name: String
)