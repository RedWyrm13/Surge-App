package com.example.surge_app

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch


@Composable
fun GoogleMapComposable(lat: Double, lon: Double) {
    if (LocalInspectionMode.current) {
        // Preview-specific UI
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Map Preview", modifier = Modifier.align(Alignment.Center))
        }
    }
        else{
            val context = LocalContext.current
            val mapView = MapView(context).apply {
                onCreate(Bundle())
                onResume() // Important to get the map to display immediately
            }

            AndroidView({ mapView }, Modifier.fillMaxSize()) { mapView ->
                mapView.getMapAsync { googleMap ->
                    MapsInitializer.initialize(context)
                    val location = LatLng(lat, lon)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
                }
            }
        }
}
@Composable
fun LocationComponent(onLocationUpdate: (Location) -> Unit) {
    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val coroutineScope = rememberCoroutineScope()
    var location by remember { mutableStateOf<Location?>(null) }

    val locationListener = LocationListener { location ->
        onLocationUpdate(location)
    }

    DisposableEffect(key1 = locationManager) {
        // Request location updates
        coroutineScope.launch {
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
            } catch (e: SecurityException) {
            }
        }

        onDispose {
            locationManager.removeUpdates(locationListener)
        }
    }


}

