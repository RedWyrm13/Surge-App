package com.example.surge_app.network

import android.location.Location
import android.util.Log
import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.GeocodingResponse
import java.io.File
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

