package com.example.myapplication

import com.example.retrofit1app_kt2.service.JogosRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class JogosRepositoryTest {

    private val repository = JogosRepository()

    @Test
    fun `test GetJogoById successfully`(): Unit = runBlocking {
        val response = repository.getJogo(548)
        assertEquals("Atlético-GO", response.timeCasa)
        assertEquals("São Paulo", response.timeVisitante)
    }

    @Test
    fun `test GetJogoById with unexisting id`(): Unit = runBlocking {
        try {
            val response = repository.getJogo(2)
            fail()
        } catch (e: Exception) {
            assertEquals("HTTP 404 ", e.message)
        }

    }

    @Test
    fun `test GetCampeonatoById successfully`(): Unit = runBlocking {
        val response = repository.getCampeonato(427)
        assertEquals("Brasileirão Série A", response.campeonato)
    }

    @Test
    fun `test GetCampeonatoById with unexisting id`(): Unit = runBlocking {
        try {
            val response = repository.getCampeonato(2)
            fail()
        } catch (e: Exception) {
            assertEquals("HTTP 404 ", e.message)
        }

    }
}