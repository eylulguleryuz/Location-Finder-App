package com.example.t120b192.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurement")
class Measurement {
     @PrimaryKey(autoGenerate = true)
     var matavimas: Int = 0
     var x: Int = 0
     var y: Int = 0
     var atstumas: Float = 0.0F

}
