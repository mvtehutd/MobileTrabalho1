package com.example.myapplication

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.myapplication.repository.PalpiteRepository
import com.example.myapplication.repository.PalpitesDatabase

/**
 * REQUIREMENT:
 * You need to specify attribute android:name=".GameApplication" in AndroidManifest.xml
 * Otherwise, this class is not initialized
 */
class JogosApplication : Application() {

    // instance to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        Log.i("JogosApplication", "onCreate")
    }
}

/**
 * We want to limit the visibility of Android-related objects to ViewModels and Composable.
 *
 * So, we attach here the repositories to a GameApplication object
 *    so that we can retrieve them in the AppViewModelProvider.
 */
class AppContainer(private val context: Context) {
    val palpiteRepository : PalpiteRepository by lazy {
        PalpiteRepository(PalpitesDatabase.getDatabase(context).palpitesDao())
    }
}