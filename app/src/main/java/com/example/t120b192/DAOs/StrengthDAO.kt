package com.example.t120b192.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.t120b192.entities.Measurement
import com.example.t120b192.entities.Strength
import com.example.t120b192.entities.User

@Dao
interface StrengthDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStrengths(users: List<Strength>)
    @Query("DELETE FROM strength")
    suspend fun deleteAll()
    @Query("DELETE FROM sqlite_sequence WHERE name = 'strength'")
    suspend fun deletePrimaryKeyIndex()
    @Query("SELECT * FROM strength")
    fun getAllStrengths(): LiveData<List<Strength>>
}
