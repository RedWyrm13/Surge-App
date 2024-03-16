package com.example.surge_app
import android.content.pm.PackageManager
import android.os.Bundle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.surge_app.PolylineManager.Companion.drawPolyline
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil


@Composable
fun GoogleMapComposable(lat: Double, lon: Double, encodedPolyline: String? = null) {

    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            onCreate(Bundle())
        }
    }

    LaunchedEffect(key1 = mapView, key2 = encodedPolyline) {
        mapView.getMapAsync { googleMap ->
            MapsInitializer.initialize(context)
            val location = LatLng(lat, lon)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))

            googleMap.clear()

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

            // Draw polyline if not null
            drawPolyline(googleMap, encodedPolyline)
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

// Companion object to encapsulate the currentPolyline variable
class PolylineManager {
    companion object {
        private var currentPolyline by mutableStateOf<Polyline?>(null)

        fun drawPolyline(googleMap: GoogleMap, encodedPolyline: String?) {
            // Remove existing polyline if there is one
            currentPolyline?.remove()

            encodedPolyline?.let { polyline ->
                val decodedPolyline = PolyUtil.decode(polyline)
                val polylineOptions = PolylineOptions()
                    .addAll(decodedPolyline)
                    .width(25f)
                    .color(Color.Blue.toArgb()) // Note: Convert Color to ARGB
                currentPolyline = googleMap.addPolyline(polylineOptions)
            }
        }
    }
}
@Composable
fun AutoFillDestination(){

}

