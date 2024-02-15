package com.example.t120b192.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.t120b192.entities.UserWithSensors
import com.example.t120b192.entities.User

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<User>)

    @Insert
    suspend fun insert(user: User)

    @Query("DELETE FROM sqlite_sequence WHERE name = 'user'")
    suspend fun deletePrimaryKeyIndex()
    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("DELETE FROM user WHERE mac = :mac AND sensorius = :sensorius AND stiprumas = :stiprumas")
    suspend fun delete(mac: String, sensorius: String, stiprumas: Int)

    @Query("UPDATE user SET mac = :mac, sensorius = :sensorius, stiprumas = :stiprumas WHERE mac = :macOld AND sensorius = :sensorius AND stiprumas = :sOld")
    suspend fun edit(macOld: String, mac: String, sensorius: String, sOld: Int, stiprumas: Int)

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>

    @Transaction
    @Query("SELECT mac, GROUP_CONCAT(sensorius) AS sensoriusList, GROUP_CONCAT(stiprumas) AS stiprumasList FROM user GROUP BY mac")
    fun getUsersWithSensors(): LiveData<List<UserWithSensors>>
}