package com.example.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.repository.Palpite
import com.example.myapplication.repository.PalpiteDao
import com.example.myapplication.repository.PalpitesDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PalpitesDatabaseTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var palpiteDao: PalpiteDao
    private lateinit var db: PalpitesDatabase

    val PALPITE1 = Palpite(1, "São Paulo")
    val PALPITE2 = Palpite(2, "Cruzeiro")

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(context, PalpitesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        palpiteDao = db.palpitesDao()
    }

    @After
    fun closeDb() {
        db.close()
    }


    @Test
    fun testInsertAndSelect() = runTest {
        palpiteDao.insert(PALPITE1)
        palpiteDao.insert(PALPITE2)
        val item1 = palpiteDao.get(1)
        val item2 = palpiteDao.get(2)
        Assert.assertEquals(2, item2.id)
        Assert.assertEquals("Cruzeiro", item2.time)
        Assert.assertEquals(1, item1.id)
        Assert.assertEquals("São Paulo", item1.time)
    }

    @Test
    fun testDeleteAndGetPalpiteInexistente() = runTest {
        palpiteDao.insert(PALPITE1)
        palpiteDao.insert(PALPITE2)
        val item1 = palpiteDao.get(1)
        val item2 = palpiteDao.get(2)
        Assert.assertEquals(2, item2.id)
        Assert.assertEquals("Cruzeiro", item2.time)
        Assert.assertEquals(1, item1.id)
        Assert.assertEquals("São Paulo", item1.time)
        palpiteDao.delete(2)
        val itemdeletado = palpiteDao.get(2)
        Assert.assertNull(itemdeletado)
    }
}