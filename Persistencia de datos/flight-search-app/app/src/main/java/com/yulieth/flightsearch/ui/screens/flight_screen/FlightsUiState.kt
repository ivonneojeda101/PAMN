package com.yulieth.flightsearch.ui.screens.flight_screen

import com.yulieth.flightsearch.data.Airport
import com.yulieth.flightsearch.data.Favorite

data class FlightsUiState(
    val code: String = "",
    val favoriteList: List<Favorite> = emptyList(),
    val destinationList: List<Airport> = emptyList(),
    val departureAirport: Airport = Airport(),
)
