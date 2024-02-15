package com.example.t120b192.database

import android.app.Application
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.t120b192.entities.CombinedData
import com.example.t120b192.Repositories.MeasurementRepository
import com.example.t120b192.Repositories.StrengthRepository
import com.example.t120b192.Repositories.UserRepository
import com.example.t120b192.entities.UserWithSensors
import com.example.t120b192.services.RetrofitClient
import com.example.t120b192.entities.Measurement
import com.example.t120b192.entities.Strength
import com.example.t120b192.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.pow


class DatabaseViewModel(application: Application) : AndroidViewModel(application) {
    private val measurementrepository: MeasurementRepository
    private val userrepository: UserRepository
    private val strengthrepository: StrengthRepository
    private val _nearestNeighborResult = MutableLiveData<Pair<Double, Pair<Int, Int>>>()
    val nearestNeighborResult: LiveData<Pair<Double, Pair<Int, Int>>>
        get() = _nearestNeighborResult
    val measurements: LiveData<List<Measurement>>
    val strengths: LiveData<List<Strength>>
    val users: LiveData<List<User>>
    var usersWithSensors: LiveData<List<UserWithSensors>>
    var combinedData: LiveData<List<CombinedData>>
    var initialized = false

    init {
        val localDatabase = LocalDatabase.getDatabase(application)
        LocalDatabase
        val measurementDao = localDatabase.measurementDao()
        measurementrepository = MeasurementRepository(measurementDao)
        measurements = measurementrepository.allMeasurements

        val userDao = localDatabase.userDao()
        userrepository = UserRepository(userDao)
        users = userrepository.allUsers
        usersWithSensors = userrepository.getUsersWithSensors()
        /*usersWithSensors.observeForever {
            Log.d("users", "users updated: $it")
        }*/
        val strengthDao = localDatabase.strengthDao()
        strengthrepository = StrengthRepository(strengthDao)
        strengths = strengthrepository.allStrengths
        combinedData = measurementrepository.getCombinedData()
        combinedData.observeForever {
            Log.d("NearestNeighbor", "CombinedData updated: $it")
        }
    }

    // in case it is needed
    fun delete(){
        viewModelScope.launch(Dispatchers.IO){
            userrepository.deleteAll()
            measurementrepository.deleteAll()
            strengthrepository.deleteAll()
        }
    }
    fun updateData(){
        usersWithSensors = userrepository.getUsersWithSensors()

    }
    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("post", "Current thread: ${Thread.currentThread().name}")
            try {
                    val measurementData = RetrofitClient.api.getMeasurements()
                    measurementrepository.insertMeasurements(measurementData)
                    Log.d("post", "Matavimai data fetched and inserted.")

                    val userData = RetrofitClient.api.getUsers()
                    userrepository.insertUsers(userData)
                    Log.d("post", "User data fetched and inserted.")
                    val strengthData = RetrofitClient.api.getStrengths()
                    strengthrepository.insertStrengths(strengthData)
                    Log.d("post", "Strength data fetched and inserted.")


            } catch (e: Exception) {
                // Handle error
                Log.e("post", "Error fetching data: ${e.message}")
            }
        }
    }
    fun insertUser(
        macEditText: EditText,
        s1EditText: EditText,
        s2EditText: EditText,
        s3EditText: EditText
    ) {
        val s1 = s1EditText.text.toString()
        val s2 = s2EditText.text.toString()
        val s3 = s3EditText.text.toString()
        val mac = macEditText.text.toString()

        if (s1.isNotEmpty() && s2.isNotEmpty() && s3.isNotEmpty()) {
            val user1 = User(mac = mac, sensorius = "wiliboxas1", stiprumas = s1.toInt())
            Log.d("user", "INSERTing: ${user1.mac} ${user1.sensorius} ${user1.stiprumas}")

            val user2 = User(mac = mac, sensorius = "wiliboxas2", stiprumas = s2.toInt())
            Log.d("user", "INSERTing: ${user2.mac} ${user2.sensorius} ${user2.stiprumas}")

            val user3 = User(mac = mac, sensorius = "wiliboxas3", stiprumas = s3.toInt())
            Log.d("user", "INSERTing: ${user3.mac} ${user3.sensorius} ${user3.stiprumas}")

            viewModelScope.launch {
                userrepository.insertUser(user1)
                userrepository.insertUser(user2)
                userrepository.insertUser(user3)
            }
            Log.d("insert", "INSERT SUCCESSFUL: $mac $s1 $s2 $s3")

        } else {
            Log.e("insert", "One or more EditText fields are empty")
        }
    }

    fun deleteUser(
        macEditText: EditText,
        s1EditText: EditText,
        s2EditText: EditText,
        s3EditText: EditText
    ) {
        val s1 = s1EditText.text.toString()
        val s2 = s2EditText.text.toString()
        val s3 = s3EditText.text.toString()
        val mac = macEditText.text.toString()

        if (s1.isNotEmpty() && s2.isNotEmpty() && s3.isNotEmpty()) {
            viewModelScope.launch {
                userrepository.deleteUser(mac, "wiliboxas1", stiprumas = s1.toInt())
                userrepository.deleteUser(mac, "wiliboxas2", stiprumas = s2.toInt())
                userrepository.deleteUser(mac, "wiliboxas3", stiprumas = s3.toInt())
                updateData()
            }
            Log.d("delete", "DELETE SUCCESSFUL: $mac $s1 $s2 $s3")

        } else {
            Log.e("delete", "One or more EditText fields are empty")
        }
    }

    fun editUser(macOld: String,
        macEditText: EditText,
                 s1Old: Int,
        s1EditText: EditText,
                 s2Old: Int,
        s2EditText: EditText,
                 s3Old: Int,
        s3EditText: EditText
    ) {
        val s1 = s1EditText.text.toString()
        val s2 = s2EditText.text.toString()
        val s3 = s3EditText.text.toString()
        val mac = macEditText.text.toString()

        if (s1.isNotEmpty() && s2.isNotEmpty() && s3.isNotEmpty()) {
            viewModelScope.launch {
                userrepository.editUser(macOld, mac, "wiliboxas1", s1Old, stiprumas = s1.toInt())
                userrepository.editUser(macOld, mac, "wiliboxas2",s2Old, stiprumas = s2.toInt())
                userrepository.editUser(macOld, mac, "wiliboxas3", s3Old, stiprumas = s3.toInt())
            }
            Log.d("edit", "Edit successful")

        } else {
            Log.e("edit", "One or more EditText fields are empty")
        }
    }
    fun euclideanDistance(rss1: List<Int>, rss2: List<Int>): Double {
        Log.d("NearestNeighbor", "vector sizes ${rss1.size} ${rss2.size}")
        Log.d("NearestNeighbor", "vector 1  ${rss1[0]} ${rss1[1]} ${rss1[2]}")
        Log.d("NearestNeighbor", "vector 2  ${rss2[0]} ${rss2[1]} ${rss2[2]}")

        return kotlin.math.sqrt(
            rss1.subList(0, 3).zip(rss2.subList(0, 3)) { a, b -> (a - b).toDouble().pow(2) }.sum()
        )
    }


    fun findNearestNeighbor(stiprumasList: List<Int>) {
        Log.d("NearestNeighbor", "Method called")

        combinedData.value.let { data ->
            Log.d("NearestNeighbor", "COMBINED DATA: $data")
            var minDistance = Double.MAX_VALUE
            var nearestNeighbor: Pair<Double, Pair<Int, Int>>? = null
            val  distanceList: List<Double> = emptyList()
            if (data != null) {
                for (entry in data) {
                    val distance = euclideanDistance(stiprumasList, entry.stiprumasList)
                    Log.d("NearestNeighbor", "distance: $distance")
                    val epsilon = 0.2
                    if (distance < minDistance) {
                        //Log.d("NearestNeighbor", "Condition met for entry: $entry")
                        minDistance = distance
                        nearestNeighbor = Pair(minDistance, Pair(entry.x, entry.y))
                    }
                }
            }
            nearestNeighbor?.let {
                _nearestNeighborResult.value = it
            //Log.d("NearestNeighbor", "Nearest neighbor updated: $it")
            }
        }
    }

}
