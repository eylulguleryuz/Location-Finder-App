package com.example.t120b192.entities

data class CombinedData(
    val matavimas: Int,
    val x: Int,
    val y: Int,
    var sensoriusList: List<String>,
    var stiprumasList: List<Int>,
    val atstumas: Float
)
