package com.example.surge_app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


//I need to make the search request class take in a string for query which will be passed to the POST
//function. I also neeed to set the value of the searchrequest properties to default values

data class SearchRequest(
    var query: String
)

