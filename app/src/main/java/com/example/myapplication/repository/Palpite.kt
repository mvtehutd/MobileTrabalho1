package com.example.myapplication.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Palpite(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val time: String
)

