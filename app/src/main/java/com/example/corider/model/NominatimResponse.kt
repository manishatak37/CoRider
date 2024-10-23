package com.example.corider.model

import com.example.corider.model.Address


data class NominatimResponse(
    val displayName: String, // This should match your JSON response
    val lat: String,
    val lon: String
)



