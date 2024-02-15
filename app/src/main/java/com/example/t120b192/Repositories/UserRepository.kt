package com.example.t120b192.Repositories

import androidx.lifecycle.LiveData
import com.example.t120b192.DAOs.UserDAO
import com.example.t120b192.entities.UserWithSensors
import com.example.t120b192.entities.User

class UserRepository(private val userDao: UserDAO) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    fun insertUsers(users: List<User>) {
        userDao.insertUsers(users)
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
        userDao.deletePrimaryKeyIndex()
    }

    suspend fun deleteUser(mac: String, sensorius: String, stiprumas: Int) {
        userDao.delete(mac, sensorius, stiprumas)
    }

    suspend fun editUser(macOld: String, mac: String, sensorius: String, sOld:Int, stiprumas: Int) {
        userDao.edit(macOld,mac, sensorius, sOld, stiprumas)
    }

    fun getUsersWithSensors(): LiveData<List<UserWithSensors>> {
        return userDao.getUsersWithSensors()
    }
}

