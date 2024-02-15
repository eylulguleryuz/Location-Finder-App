package com.example.t120b192.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User(
     @PrimaryKey(autoGenerate = true)
     var id: Int = 0,
     var mac: String = "",
     var sensorius: String = "",
     var stiprumas: Int = 0
){}