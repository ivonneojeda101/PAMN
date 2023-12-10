package com.example.skan.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material.MaterialTheme as AppTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skan.R
import com.example.skan.domain.entities.Product
import com.example.skan.presentation.viewModel.SearchViewModel

data class SearchResult(val title: String, val description: String, val imageResId: Int)

@Composable
@Preview
fun SearchScreen() {
    val viewModel: SearchViewModel = SearchViewModel()
    val searchText: String by viewModel.searchText.observeAsState(initial = "")
    val listProducts: List<Product> by viewModel.listProducts.observeAsState(initial = listOf())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6EC7D7),
                        Color(0xFFAEE2FA),
                        Color(0xFFEAF7F9)
                    ), // Specify your gradient colors here
                    startY = 0f,
                    endY = 2000f // Adjust the end position as needed
                )
            )

    ) {
        Row(){
            mainHeader()
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                start = 25.dp,
                top = 70.dp,)
        ) {
            SearchBarC(text = searchText, viewModel = viewModel)
        }

        Row(){
            Box(
                modifier = Modifier
                    .padding(
                        start = 18.dp,
                        top = 160.dp,
                        end = 18.dp,
                        bottom = 100.dp
                    )
                    .background(Color.White.copy(alpha = 0.6f), shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    listProducts.forEach { result ->
                        ResultItem(result) { selectedResult ->
                            // Handle selection of result and navigate to another activity
                            // Use context.startActivity(intent) to start another activity
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarC(text: String, viewModel: SearchViewModel) {
    var active by remember { mutableStateOf(false) } // Active state for SearchBar

    DockedSearchBar (
        query = text,
        onQueryChange = {
            viewModel.updateSearchText(it)
        },
        onSearch = {
            active = false
            viewModel.searchProduct(it)
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(text = "Buscar")
        },

        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }) {}
}


@Composable
fun ResultItem(product: Product, onItemClick: (SearchResult) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.facemask),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
                .weight(1f)
        ) {
            product.name?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
            product.description?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}