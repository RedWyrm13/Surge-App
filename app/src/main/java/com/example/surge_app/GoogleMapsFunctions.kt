package com.example.surge_app

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

data class GeocodingResponse(
    val results: List<Result>,
    val status: String
)

data class Result(
    val geometry: Geometry
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)

@Composable
fun GoogleMapComposable(lat: Double, lon: Double) {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            onCreate(Bundle())
        }
    }

    LaunchedEffect(key1 = mapView) {
        mapView.getMapAsync { googleMap ->
            MapsInitializer.initialize(context)
            val location = LatLng(lat, lon)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))

            // Check if location permission is granted
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap.isMyLocationEnabled = true
            }
        }
    }

    DisposableEffect(mapView) {
        mapView.onResume()
        onDispose {
            mapView.onPause()
        }
    }

    if (LocalInspectionMode.current) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Map Preview", modifier = Modifier.align(Alignment.Center))
        }
    } else {
        AndroidView({ mapView }, Modifier.fillMaxSize())
    }
}
interface GeocodingApiService {
    @GET("maps/api/geocode/json")
    suspend fun getCoordinates(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): GeocodingResponse
}



object PlacesApiManager {
    private var initialized = false

    fun initializePlaces(context: Context) {
        if (!initialized) {
            try {
                val apiKeyInstance = ApiKey(context)
                val apiKeyValue = apiKeyInstance.apiKey
                Places.initialize(context, apiKeyValue)
                initialized = true
            } catch (e: IOException) {
                // Handle any exceptions when reading the API key from the properties file
                e.printStackTrace()
            }
        }
    }
}
