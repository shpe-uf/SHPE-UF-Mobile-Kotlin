package com.example.shpe_uf_mobile_kotlin.data.models.MapsDirections

import android.graphics.Color
import android.util.Log
import com.example.shpe_uf_mobile_kotlin.ui.pages.home.TravelMode
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil

data class RouteDetails(
    val polylineOptions: CustomPolylineOptions,
    val durationText: String,
    val distanceText: String,
    val durationValue: Int? = null,
    val distanceValue: Int? = null,
    val startAddress: String? = null,
    val endAddress: String? = null,
    val summary: String? = null
)

data class CustomPolylineOptions(
    val points: List<LatLng>,
    val colorKt: Int,
    val width: Float
)

class DirectionsRepository(private val apiKey: String) {

    private val api = RetrofitClient.instance

    suspend fun getDirections(
        origin: LatLng?,
        destination: LatLng?,
        mode: TravelMode = TravelMode.DRIVING,
        alternatives: Boolean = false
    ): Result<List<RouteDetails>> {
        return try {
            val originStr = "${origin?.latitude},${origin?.longitude}"
            val destinationStr = "${destination?.latitude},${destination?.longitude}"

            val response = api.getDirections(
                origin = originStr,
                destination = destinationStr,
                mode = mode.value,
                apiKey = apiKey,
                alternatives = alternatives
            )

            if (response.isSuccessful) {
                val directionsResponse = response.body()

                if (directionsResponse == null) {
                    return Result.failure(Exception("Empty response from API"))
                }

                when (directionsResponse.status) {
                    "OK" -> {
                        val routes = directionsResponse.routes.map { route ->
                            parseRoute(route)
                        }
                        Result.success(routes)
                    }
                    "ZERO_RESULTS" -> Result.failure(Exception("No route found"))
                    "OVER_QUERY_LIMIT" -> Result.failure(Exception("API quota exceeded"))
                    "REQUEST_DENIED" -> Result.failure(Exception("Request denied: ${directionsResponse.errorMessage}"))
                    "INVALID_REQUEST" -> Result.failure(Exception("Invalid request"))
                    else -> Result.failure(Exception("Unknown error: ${directionsResponse.status}"))
                }
            } else {
                Result.failure(Exception("API error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("DirectionsRepository", "Error fetching directions", e)
            Result.failure(e)
        }
    }

    private fun parseRoute(route: Route): RouteDetails {
        val leg = route.legs.first()
        val decodedPath = PolyUtil.decode(route.overviewPolyline.points)

        return RouteDetails(
            polylineOptions = CustomPolylineOptions(
                points = decodedPath,
                colorKt = Color.BLUE,
                width = 10f
            ),
            durationText = leg.duration.text,
            distanceText = leg.distance.text,
            durationValue = leg.duration.value,
            distanceValue = leg.distance.value,
            startAddress = leg.startAddress,
            endAddress = leg.endAddress,
            summary = route.summary
        )
    }
}