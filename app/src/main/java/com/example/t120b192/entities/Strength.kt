package com.example.t120b192.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "strength")
class Strength {
     @PrimaryKey(autoGenerate = true)
     var id: Int = 0
     var matavimas: Int = 0
     var sensorius: String = ""
     var stiprumas: Int = 0

}
