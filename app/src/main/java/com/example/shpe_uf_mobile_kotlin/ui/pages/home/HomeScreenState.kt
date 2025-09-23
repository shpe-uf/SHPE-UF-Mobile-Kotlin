package com.example.shpe_uf_mobile_kotlin.ui.pages.home

import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.time.YearMonth

data class HomeScreenState(
    val events: List<HomeViewModel.Event> = emptyList(),
    val currentDate: LocalDate = LocalDate.now(),
    val monthDisplayedName : String = LocalDate.now().month.name,
    val isRefreshing: Boolean = false,
    val lastDateLoaded: LocalDate = LocalDate.now(),
    var isFetching: Boolean = false,
    var nextPage: Int = 0,

    // Window visibility
    val isEventDetailsVisible: Boolean = false,
    val selectedEvent: HomeViewModel.Event? = null,

    val isNotificationWindowVisible: Boolean = false,
    val notificationSettings: NotificationSelection = NotificationSelection(),
    val allNotificationCurrentColor: Color = Color(0xFF933815),
    // -------------------

    val selectedEventLocation: LatLng? = null,
    val isMapLoading: Boolean = false,
    val mapError: String? = null,

    val appScreenMode: AppScreenMode = AppScreenMode.HOME_FEED,
    val mapDestinationLatLng: LatLng? = null,
    val mapUserLocationLatLng: LatLng? = null,
    val mapDrivingRoute: RouteDetails? = null,
    val mapWalkingRoute: RouteDetails? = null,
    val mapSelectedTravelMode: TravelMode = TravelMode.DRIVING,
    val isMapDataLoading: Boolean = false,
    val mapErrorMessage: String? = null

)

data class NotificationSelection (
    var gbmNotification: Boolean = false,
    var socialNotification: Boolean = false,
    var workshopNotification: Boolean = false,
    var infoSessionNotification: Boolean = false,
    var volunteeringNotification: Boolean = false,
    var allNotificationSelection: Boolean = false
)



