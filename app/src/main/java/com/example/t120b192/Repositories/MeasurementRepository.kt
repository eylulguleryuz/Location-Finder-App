package com.example.t120b192.Repositories

import androidx.lifecycle.LiveData
import com.example.t120b192.entities.CombinedData
import com.example.t120b192.DAOs.MeasurementDAO
import com.example.t120b192.entities.Measurement

class MeasurementRepository(private val measurementDao: MeasurementDAO) {
    val allMeasurements: LiveData<List<Measurement>> = measurementDao.getAllMeasurements()

    fun insertMeasurements(measurements: List<Measurement>) {
        measurementDao.insertMeasurements(measurements)
    }
    suspend fun deleteAll() {
        measurementDao.deleteAll()
        measurementDao.deletePrimaryKeyIndex()
    }

    fun getCombinedData(): LiveData<List<CombinedData>> {
        return measurementDao.getCombinedData()
    }


}

