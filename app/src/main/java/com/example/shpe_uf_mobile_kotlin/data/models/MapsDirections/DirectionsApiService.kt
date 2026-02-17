package com.example.shpe_uf_mobile_kotlin.data.models.MapsDirections

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApiService {
    @GET("maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode") mode: String = "driving",
        @Query("key") apiKey: String,
        @Query("alternatives") alternatives: Boolean = false,
        @Query("departure_time") departureTime: String? = null,
        @Query("avoid") avoid: String? = null
    ): Response<DirectionsResponse>
}