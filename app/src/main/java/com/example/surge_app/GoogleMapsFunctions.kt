package com.example.surge_app

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng

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

@Composable
fun locationComponent(): Location {
    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    // Default location - Harry Reid International Airport, Las Vegas
    val defaultLocation = Location("").apply {
        latitude = 36.0840  // Latitude for Harry Reid International Airport
        longitude = -115.1537  // Longitude for Harry Reid International Airport
    }

    var currentLocation by remember { mutableStateOf(defaultLocation) }
    val locationListener = LocationListener { location ->
        currentLocation = location
    }

    DisposableEffect(key1 = locationManager) {
        // Permission check and request should be handled before this point
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                0f,
                locationListener
            )
        } catch (e: SecurityException) {
            Log.e("LocationComponent", "Security exception: ${e.message}")
        }

        onDispose {
            locationManager.removeUpdates(locationListener)
        }
    }

    return currentLocation
}