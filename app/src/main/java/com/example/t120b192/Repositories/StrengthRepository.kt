package com.example.t120b192.Repositories

import androidx.lifecycle.LiveData
import com.example.t120b192.DAOs.MeasurementDAO
import com.example.t120b192.DAOs.StrengthDAO
import com.example.t120b192.DAOs.UserDAO
import com.example.t120b192.entities.Measurement
import com.example.t120b192.entities.Strength
import com.example.t120b192.entities.User

class StrengthRepository(private val strengthDao: StrengthDAO) {
    val allStrengths: LiveData<List<Strength>> = strengthDao.getAllStrengths()
    suspend fun deleteAll() {
        strengthDao.deleteAll()
        strengthDao.deletePrimaryKeyIndex()
    }
    fun insertStrengths(strengths: List<Strength>) {
        strengthDao.insertStrengths(strengths)
    }

}

