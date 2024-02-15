package com.example.t120b192.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.t120b192.entities.CombinedData
import com.example.t120b192.entities.Measurement

@Dao
interface MeasurementDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeasurements(measurements: List<Measurement>)
    @Query("DELETE FROM measurement")
    suspend fun deleteAll()
    @Query("DELETE FROM sqlite_sequence WHERE name = 'measurement'")
    suspend fun deletePrimaryKeyIndex()
    @Query("SELECT * FROM measurement")
    fun getAllMeasurements(): LiveData<List<Measurement>>

    @Query("SELECT measurement.matavimas, measurement.x, measurement.y, GROUP_CONCAT(strength.sensorius) AS sensoriusList, GROUP_CONCAT(strength.stiprumas) AS stiprumasList, measurement.atstumas FROM measurement INNER JOIN strength ON measurement.matavimas = strength.matavimas GROUP BY measurement.matavimas")
    fun getCombinedData(): LiveData<List<CombinedData>>
}
