package com.example.myapplication.repository

class PalpiteRepository(private val palpiteDao: PalpiteDao) {
    suspend fun inserirPalpite(palpite: Palpite) = palpiteDao.insert(palpite)

    suspend fun deletarPalpite(id: Int) = palpiteDao.delete(id)

    suspend fun obterPalpite(id: Int) = palpiteDao.get(id)
}