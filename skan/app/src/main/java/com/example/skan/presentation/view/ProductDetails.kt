package com.example.skan.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.graphics.component1
import androidx.core.graphics.component2

@Composable
fun ProductDetails() {
    ScrollableGrayBackground()
}

@Composable
fun ScrollableGrayBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {

        Box(
            modifier = Modifier
                .padding(24.dp)
                .background(Color.White.copy(alpha = 0.5f),shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = "ProductDetails",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(16.dp))
                FirstContainer()
                Spacer(modifier = Modifier.height(16.dp))
                PointsList()
                Spacer(modifier = Modifier.height(16.dp))
                SecondContainer()
                Spacer(modifier = Modifier.height(16.dp))
                ThirdContainer()
                repeat(3) { index ->
                    ContainerWithText(text = "Container${index + 1}")
                }
            }
        }
    }
}

@Composable
fun ContainerWithText(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun FirstContainer() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Title: Quick Product Notes ",
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Help",
            modifier = Modifier
                .padding(end = 4.dp)
                .clickable {
                    // Handle click on help icon
                }
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "(Click on Icons for more information)",
            color = Color.Gray
        )
    }
}

@Composable
fun PointsList() {
    val points = listOf(
        "Paraben-Free" to true,
        "Sulfate-Free" to true,
        "Alcohol-Free" to true,
        "Silicone-Free" to false,
        "EU Allergen-Free" to false,
        "Fungal Acne (Malassezia) Safe" to false
    )

    points.forEach { (point, isSelected) ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = point,
                modifier = Modifier.weight(1f)
            )
            StatusIcon(isSelected)
        }
    }
}

@Composable
fun StatusIcon(isSelected: Boolean) {
    val icon: ImageVector = if (isSelected) {
        Icons.Default.Check
    } else {
        Icons.Default.Close
    }
    val iconColor = if (isSelected) Color.Green else Color.Red

    Icon(
        imageVector = icon,
        contentDescription = "",
        tint = iconColor
    )
}

@Composable
fun SecondContainer() {
    Text(
        text = "Notable Effects & Ingredients",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    val effectsAndIngredients = listOf(
        "Acne Fighting" to 2,
        "Brightening" to 3,
        "UV Protection" to 5,
        "Wound Healing" to 4,
        "Anti Aging" to 6
    )
    effectsAndIngredients.forEach { (effect, ingredientCount) ->
        EffectRow(effect = effect, ingredientCount = ingredientCount)
    }
}

@Composable
fun EffectRow(effect: String, ingredientCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        EffectIcon(effect)
        Text(
            text = effect.capitalize(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$ingredientCount ingredients",
            color = Color.Gray
        )
        IngredientsList(ingredientCount)
    }
}

@Composable
fun EffectIcon(effect: String) {
    val icon: ImageVector = when (effect) {
        "Acne Fighting" -> Icons.Default.AddCircle
        "Brightening" -> Icons.Default.AddCircle
        "UV Protection" -> Icons.Default.AddCircle
        "Wound Healing" -> Icons.Default.AddCircle
        "Anti Aging" -> Icons.Default.AddCircle
        else -> Icons.Default.AddCircle
    }
    Icon(
        imageVector = icon,
        contentDescription = "Effect Icon",
        tint = MaterialTheme.colorScheme.onPrimary, // Change the color as needed
        modifier = Modifier.padding(end = 8.dp)
    )
}

@Composable
fun IngredientsList(ingredientCount: Int) {
    val ingredients = listOf(
        "Ingredient 1",
        "Ingredient 2",
        "Ingredient 3",
        "Ingredient 4"
    )
    Column {
        ingredients.take(ingredientCount.coerceAtMost(4)).forEach { ingredient ->
            IngredientRow(ingredient)
        }
    }
}

@Composable
fun IngredientRow(ingredient: String) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
            .size(100.dp, 40.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = ingredient,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun ThirdContainer() {
    Text(
        text = "Ingredients Related to Skin Types",
        fontSize = 18.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "Click on the arrow next to the Skin Type! Green = Good & Red = Bad",
        color = Color.Gray,
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    val skinTypes = listOf(
        "Dry Skin" to 2 to 1,
        "Oily/Acne-Prone Skin" to 3 to 2,
        "Sensitive Skin" to 1 to 3
    )

    skinTypes.forEach { (skinTypePair, count) ->
        val (skinType, values) = skinTypePair
        val (goodCount, badCount) = values
        SkinTypeRow(skinType = skinType, goodCount = goodCount, badCount = badCount)
        Spacer(modifier = Modifier.height(8.dp))
    }


}

@Composable
fun SkinTypeRow(skinType: String, goodCount: Int, badCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Face,
            contentDescription = "Skin Type Icon",
            tint = Color.Gray,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = skinType,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        RectangleIndicator(goodCount, badCount)
    }
}

@Composable
fun RectangleIndicator(goodCount: Int, badCount: Int) {
    val total = goodCount + badCount
    val goodPercentage = (goodCount.toFloat() / total) * 100
    val badPercentage = (badCount.toFloat() / total) * 100

    Box(
        modifier = Modifier
            .size(100.dp, 20.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.LightGray, MaterialTheme.colorScheme.onSurface),
                    startX = 0f,
                    endX = 100f
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(goodPercentage.dp)
                .background(Color.Green)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(badPercentage.dp)
                .background(Color.Red)
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$goodCount",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = "$badCount",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
    }
}