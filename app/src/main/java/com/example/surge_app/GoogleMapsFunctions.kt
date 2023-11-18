package com.example.surge_app

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun GoogleMapComposable() {
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
                    val location = LatLng(37.4219999, -122.0862462) // Sample location
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
                }
            }
        }
}
