package com.yulieth.flightsearch.ui.screens.search

import com.yulieth.flightsearch.data.Airport
import com.yulieth.flightsearch.data.Favorite

data class SearchUiState(
    val searchQuery: String = "",
    val selectedCode: String = "",
    val airportList: List<Airport> = emptyList(),
    val favoriteList: List<Favorite> = emptyList(),
)