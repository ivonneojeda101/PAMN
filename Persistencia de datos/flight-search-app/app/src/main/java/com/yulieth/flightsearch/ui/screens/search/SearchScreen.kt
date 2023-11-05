package com.yulieth.flightsearch.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yulieth.flightsearch.NavigationDestination
import com.yulieth.flightsearch.R
import com.yulieth.flightsearch.data.Favorite
import com.yulieth.flightsearch.ui.screens.flight_screen.FlightScreen as FlightScreen

object SearchDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onSelectCode: (String) -> Unit
) {
    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState().value

    Column( modifier = modifier) {
        Row(modifier = modifier){
            SearchTextField(
                uiState.searchQuery,
                onQueryChange = {
                    viewModel.updateQuery(it)
                    viewModel.updateSelectedCode("")
                    viewModel.onQueryChange(it)
                }
            )
        }
        Row(modifier = modifier) {
            if (uiState.searchQuery.isEmpty()) {

                val favoriteList = uiState.favoriteList
                val airportList = uiState.airportList

                if (favoriteList.isNotEmpty()) {
                    FavoriteResult(
                        airportList = airportList,
                        favoriteList = favoriteList,
                        onFavoriteClick = { departureCode: String, destinationCode: String ->
                            val tmp = Favorite(
                                id = favoriteList.filter { item ->
                                    (item.departureCode == departureCode && item.destinationCode == destinationCode)
                                }.first().id,
                                departureCode = departureCode,
                                destinationCode = destinationCode,
                            )
                            viewModel.removeDbFavorite(tmp)
                        },
                    )
                } else {
                    Text(text = "No Favorites yet")
                }
            } else {
                val airports = uiState.airportList

                SearchResults(
                    modifier = modifier,
                    airports = airports,
                    onSelectCode = onSelectCode
                )
            }
        }
    }
}