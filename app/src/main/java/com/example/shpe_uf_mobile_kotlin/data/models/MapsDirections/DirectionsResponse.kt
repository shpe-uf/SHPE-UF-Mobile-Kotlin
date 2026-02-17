package com.example.shpe_uf_mobile_kotlin.data.models.MapsDirections

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(
    @SerializedName("routes") val routes: List<Route>,
    @SerializedName("status") val status: String,
    @SerializedName("error_message") val errorMessage: String?
)

data class Route(
    @SerializedName("summary") val summary: String,
    @SerializedName("legs") val legs: List<Leg>,
    @SerializedName("overview_polyline") val overviewPolyline: OverviewPolyline,
    @SerializedName("warnings") val warnings: List<String>?,
    @SerializedName("bounds") val bounds: Bounds?
)

data class Leg(
    @SerializedName("distance") val distance: Distance,
    @SerializedName("duration") val duration: Duration,
    @SerializedName("start_address") val startAddress: String,
    @SerializedName("end_address") val endAddress: String,
    @SerializedName("start_location") val startLocation: Location,
    @SerializedName("end_location") val endLocation: Location,
    @SerializedName("steps") val steps: List<Step>?
)

data class Distance(
    @SerializedName("text") val text: String,
    @SerializedName("value") val value: Int
)

data class Duration(
    @SerializedName("text") val text: String,
    @SerializedName("value") val value: Int
)

data class Location(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)

data class OverviewPolyline(
    @SerializedName("points") val points: String
)

data class Step(
    @SerializedName("distance") val distance: Distance,
    @SerializedName("duration") val duration: Duration,
    @SerializedName("html_instructions") val htmlInstructions: String,
    @SerializedName("travel_mode") val travelMode: String,
    @SerializedName("polyline") val polyline: OverviewPolyline
)

data class Bounds(
    @SerializedName("northeast") val northeast: Location,
    @SerializedName("southwest") val southwest: Location
)