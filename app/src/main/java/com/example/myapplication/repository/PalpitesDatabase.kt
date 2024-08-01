package com.example.myapplication.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Palpite::class], version = 1, exportSchema = false)
abstract class PalpitesDatabase : RoomDatabase() {
    abstract fun palpitesDao(): PalpiteDao
    companion object {
        @Volatile
        private var Instance: PalpitesDatabase? = null

        fun getDatabase(context: Context): PalpitesDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PalpitesDatabase::class.java, "palpites_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}