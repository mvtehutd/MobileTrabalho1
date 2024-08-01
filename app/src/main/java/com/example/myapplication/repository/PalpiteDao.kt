package com.example.myapplication.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PalpiteDao {
    @Insert
    suspend fun insert(palpite: Palpite)

    @Query("SELECT * FROM Palpite WHERE id = :id")
    suspend fun get(id: Int): Palpite

    @Query("DELETE FROM Palpite WHERE id = :id")
    suspend fun delete(id: Int): Int
}