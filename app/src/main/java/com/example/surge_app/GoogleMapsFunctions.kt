package com.example.surge_app


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
