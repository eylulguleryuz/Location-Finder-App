package com.example.t120b192.services

import com.example.t120b192.entities.Measurement
import com.example.t120b192.entities.Strength
import com.example.t120b192.entities.User
import retrofit2.http.GET

interface Api {
    @GET("/api/measurements")
    suspend fun getMeasurements(): List<Measurement>

    @GET("/api/users")
    suspend fun getUsers(): List<User>

    @GET("/api/strengths")
    suspend fun getStrengths(): List<Strength>

}