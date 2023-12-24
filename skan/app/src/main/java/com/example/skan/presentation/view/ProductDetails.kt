package com.example.skan.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import com.example.skan.R
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product
import com.example.skan.domain.entities.Review
import com.example.skan.presentation.viewModel.ProductDetailsViewModel
import androidx.compose.material.MaterialTheme as AppTheme

@Composable
@Preview
fun ProductDetails() {
    ScrollableGrayBackground()
}

@Composable
fun ScrollableGrayBackground() {
    val viewModel = ProductDetailsViewModel()
    val product: Product by viewModel.product.observeAsState(initial = Product())
    val reviews: List<Review> by viewModel.reviews.observeAsState(initial = emptyList())
    val saveProduct: Boolean by viewModel.saveProduct.observeAsState(initial = false)
    val isFavorite: Boolean by viewModel.isFavorite.observeAsState(initial = false)
    val showReviewScreen: Boolean by viewModel.showReviewScreen.observeAsState(initial = false)
    val idUser: Int by viewModel.idUser.observeAsState(initial = 0)
    val applicationContext = LocalContext.current
    viewModel.getData(applicationContext)

    if (saveProduct){
        SaveScreen(product)
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF6EC7D7), Color(0xFFAEE2FA), Color(0xFFEAF7F9)),
                        startY = 0f,
                        endY = 2000f
                    )
                )
        ) {
            Row(){
                mainHeader()
            }
            Row(){
                Box(
                    modifier = Modifier
                        .padding(
                            start = 18.dp,
                            top = 70.dp,
                            end = 18.dp,
                            bottom = 100.dp
                        )
                        .background(
                            Color.White.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Descripción del Producto",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (product.id != null && product.id!! > 0) {
                            ProductCard(product.name!!, product.description!!)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            if (product.id != null && product.id!! > 0 && idUser>0) {
                                if (!isFavorite){
                                    Icon(
                                        painter = painterResource(id = R.drawable.favorite),
                                        contentDescription = "Favorite Icon",
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(2.dp)
                                            .clickable { viewModel.addFavorite(applicationContext) },
                                        tint = Color.Unspecified,
                                    )
                                }
                                Icon(
                                    painter = painterResource(id = R.drawable.review),
                                    contentDescription = "Review Icon",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(2.dp)
                                        .clickable {
                                            viewModel.updateShowReviewScreen(true)
                                        },
                                    tint = Color.Unspecified,
                                )
                            }

                            if (product.id != null && product.id== 0){
                                Icon(
                                    painter = painterResource(id = R.drawable.save),
                                    contentDescription = "Save Icon",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable { viewModel.updateSave(true) },
                                    tint = AppTheme.colors.primary,
                                )
                            }
                        }
                        FirstContainer(product)
                        Spacer(modifier = Modifier.height(16.dp))
                        PointsList(product)
                        Spacer(modifier = Modifier.height(16.dp))
                        SecondContainer(product)
                        Spacer(modifier = Modifier.height(16.dp))
                        ThirdContainer(product)
                        Spacer(modifier = Modifier.height(16.dp))
                        FourthContainer(product)
                        Spacer(modifier = Modifier.height(16.dp))
                        IngredientTable(product.ingredients)
                        Spacer(modifier = Modifier.height(8.dp))
                        ReviewList(reviews)
                        if (showReviewScreen){
                            ReviewScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun mainHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 20.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(140.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(0.dp)),
                contentScale = ContentScale.Crop
            )
            Icon(
                painter = painterResource(id = R.drawable.help),
                contentDescription = "Help",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun FirstContainer(product: Product) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = "Notas rápidas sobre el producto "
        )
        Icon(
            painter = painterResource(id = R.drawable.help),
            tint = Color.Unspecified,
            contentDescription = "Help",
            modifier = Modifier
                .size(15.dp)
                .clickable {
                }
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            fontSize = 10.sp,
            text = "(Click en el icono para mayor información)",
            color = Color.Gray
        )
    }
}

@Composable
fun PointsList(product: Product) {
    val points = listOf(
        "Libre de parabenos" to product.parabenFree,
        "Libre de sulfatos" to product.sulfateFree,
        "Libre de alcohol" to product.alcoholFree,
        "Libre de siliconas" to product.siliconeFree,
        "Libre de alérgenos de la UE" to product.euAllergenFree,
        "Seguro para acné" to product.fungalAcne
    )

    points.forEach { (point, isSelected) ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                fontSize = 13.sp,
                text = point,
                modifier = Modifier.weight(1f)

            )
            if (isSelected != null) {
                StatusIcon(isSelected)
            }
            else{
                StatusIcon(true)
            }
        }
    }
}

@Composable
fun StatusIcon(isSelected: Boolean) {
    val icon: ImageVector = if (isSelected) {
        Icons.Default.CheckCircle
    } else {
        Icons.Default.Close
    }
    val iconColor = if (isSelected) Color(0xFF30b0c7) else Color(0xFF535353)

    Icon(
        imageVector = icon,
        contentDescription = "",
        tint = iconColor
    )
}

@Composable
fun SecondContainer(product: Product) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Efectos e Ingredientes notables ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        Icon(
            painter = painterResource(id = R.drawable.help),
            tint = Color.Unspecified,
            contentDescription = "Help",
            modifier = Modifier
                .size(15.dp)
                .clickable {
                }
        )
    }

    val effectsAndIngredients = listOf(
        "Combate acné" to (product.acneFighting?.size ?: 0),
        "Ilumina" to (product.brightening?.size ?: 0),
        "Protección UV" to (product.uvProtection?.size ?: 0),
        "Promueve curación" to (product.woundHealing?.size ?: 0),
        "Anti-envejecimiento" to (product.antiAging?.size ?: 0)
    )
    effectsAndIngredients.forEach { (effect, ingredientCount) ->
        if (ingredientCount > 0 ){
            EffectRow(effect = effect, ingredientCount = ingredientCount, product = product)
        }
    }
}

@Composable
fun EffectRow(effect: String, ingredientCount: Int, product: Product) {
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
            modifier = Modifier.weight(1f),
            fontSize = 13.sp,
        )
        Text(
            fontSize = 13.sp,
            text = "$ingredientCount ingrediente(s)",
            color = Color.Gray
        )
        IngredientsList(effect, product)
    }
}

@Composable
fun EffectIcon(effect: String) {
    val icon: Painter? = when (effect) {
        "Combate acné" -> painterResource(id = R.drawable.acne_fighting)
        "Ilumina" -> painterResource(id = R.drawable.brightening)
        "Protección UV" -> painterResource(id = R.drawable.uv_protection)
        "Promueve curación" -> painterResource(id = R.drawable.promotes_wound_healing)
        "Anti-envejecimiento" -> painterResource(id = R.drawable.anti_aging)
        else -> null
    }
    if (icon != null) {
        Icon(
            painter = icon,
            contentDescription = "Effect Icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(35.dp)
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun IngredientsList(effect: String, product: Product) {
    val ingredients: List<String>? = when (effect) {
        "Combate acné" -> product.acneFighting
        "Ilumina" -> product.brightening
        "Protección UV" -> product.uvProtection
        "Promueve curación" -> product.woundHealing
        "Anti-envejecimiento" -> product.antiAging
        else -> listOf<String>()
    }

    val elements = ingredients?.size ?: 0
        Column {
            ingredients?.take(elements.coerceAtMost(4))?.forEach { ingredient ->
                IngredientRow(ingredient)
            }
    }
}

@Composable
fun IngredientRow(ingredient: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
            .size(100.dp, 40.dp)
            .background(Color.LightGray)
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
fun ThirdContainer(product: Product) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Ingredientes relacionados con los tipos de piel ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            //modifier = Modifier.padding(bottom = 8.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.help),
            tint = Color.Unspecified,
            contentDescription = "Help",
            modifier = Modifier
                .size(15.dp)
                .clickable {
                }
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Verde = Bueno & Rojo = Malo",
        color = Color.Gray,
        fontSize = 10.sp,
        modifier = Modifier.padding(bottom = 10.dp)
    )
    val skinTypes = listOf(
        "Piel Seca" to product.goodDrySkin to product.badDrySkin,
        "Piel Grasa/Tendencia acné" to product.goodOilSkin to product.badOilSkin,
        "Piel Sensible" to product.goodSensitiveSkin to product.badSensitiveSkin
    )

    for ((pair1, pair2) in skinTypes) {
        val (skinType, goodCountN) = pair1
        val goodCount = goodCountN ?: 0
        val (secondPair, badCountN) = pair2 ?: 0
        val badCount = badCountN ?: 0

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
            fontSize = 13.sp,
            text = skinType,
            fontWeight = FontWeight.Bold,
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(goodPercentage.dp)
                .background(Color(0xFF30b0c7))
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(badPercentage.dp)
                .background(Color(0xFFF18283))
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (goodCount > 0){
                Text(
                    text = "$goodCount",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }

            if (badCount > 0){
                Text(
                    text = "$badCount",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }

            if (badCount == 0 && goodCount == 0){
                Text(
                    text = "Ninguno",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun FourthContainer(product: Product) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Desglose de la seguridad de los ingredientes (EWG Calificación Seguridad) ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        Icon(
            painter = painterResource(id = R.drawable.help),
            tint = Color.Unspecified,
            contentDescription = "Help",
            modifier = Modifier
                .size(15.dp)
                .clickable {
                }
        )
    }

    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        ColorSquare(Color(0xFF30b0c7), " Riesgo\n Bajo")
        ColorSquare(Color(0xFFF3AE75), " Riesgo\n Moderado")
        ColorSquare(Color(0xFFF18283), " Riesgo\n Alto")
        ColorSquare(Color(0xFFB8B7B7), " Desconocido")
    }

    MultiColorRectBrush(product)
}

@Composable
fun ColorSquare(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color)
        )
        Text(text = label, maxLines = 2, minLines = 2)
    }
}

@Composable
fun MultiColorRectBrush(product: Product) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(Color.White)
    ) {

        var count1 = 0
        var count2 = 0
        var count3 = 0
        var count4 = 0
        var total = product.ingredients.size

        if (total > 0) {
            for (ingredient in product.ingredients) {
                if (ingredient.ewg == 1 || ingredient.ewg == 2) {
                    count1++
                } else if (ingredient.ewg == 3 || ingredient.ewg == 4 || ingredient.ewg == 5 || ingredient.ewg == 6) {
                    count2++
                } else if (ingredient.ewg == 7 || ingredient.ewg == 8 || ingredient.ewg == 9 || ingredient.ewg == 10) {
                    count3++
                } else {
                    count4++
                }
            }
        }

        if (total == 0){total = 1}

        val color1Percentage = ((count1)/total).toFloat()
        val color2Percentage = ((count2)/total).toFloat()
        val color3Percentage = ((count3)/total).toFloat()
        val color4Percentage = ((count4)/total).toFloat()

        Box() {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(color1Percentage)
                    .background(Color(0xFF98E0DD))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(color2Percentage)
                    .background(Color(0xFFF3AE75))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(color3Percentage)
                    .background(Color(0xFFF18283))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(color4Percentage)
                    .background(Color(0xFFB8B7B7))
            )
        }
    }
}

@Composable
fun IngredientTable(ingredients: List<Ingredient>) {
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = " EWG  ", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(text = " CIR  ", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(text = "Ingrediente y función cosmética", Modifier.weight(6f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(text = "Notas  ", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }

        ingredients.forEach { ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.05f)
                        .padding(horizontal = 4.dp)
                ) {
                    Column {
                        Text(
                            text = ingredient.ewg?.toString() ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Column {
                        Text(
                            text = ingredient.cir?.toString() ?: " ",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(0.35f)
                        .padding(horizontal = 4.dp)
                ) {
                    Column {
                        Text(
                            text = ingredient.name,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Justify,
                            fontSize = 12.sp,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(0.15f)
                        .padding(horizontal = 4.dp)
                ) {
                    Column (horizontalAlignment = Alignment.CenterHorizontally,){
                        for (effect in ingredient.notableEffects){
                            IconWithDescription(effect.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IconWithDescription(description: String)
{
    val value: Pair<String, Painter>? = when (description) {
        "ACNE_FIGHTING" -> Pair("Combate el acné", painterResource(id = R.drawable.acne_fighting))
        "BRIGHTENING" -> Pair("Iluminador", painterResource(id = R.drawable.brightening))
        "UV_PROTECTION" -> Pair("Protección UV", painterResource(id = R.drawable.uv_protection))
        "WOUND_HEALING" -> Pair("Promueve curación", painterResource(id = R.drawable.promotes_wound_healing))
        "ANTI_AGING" -> Pair("Anti-envejecimiento", painterResource(id = R.drawable.anti_aging))
        else -> {null}
    }


    if (value != null) {
        Icon(
            painter = value.second,
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = Color.Unspecified
        )

        Text(
            text = value.first,
            modifier = Modifier.padding(top = 1.dp),
            maxLines = 2,
            fontSize = 8.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ProductCard(name: String, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.5f))
        ) {
            Image(
                painter = painterResource(id = R.drawable.facemask),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(0.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = TextStyle(color = Color.Gray),
            fontSize = 14.sp
        )
    }
}

@Composable
fun ReviewScreen(viewModel: ProductDetailsViewModel) {
    val reviewTitle: String by viewModel.reviewTitle.observeAsState(initial = "")
    val reviewText: String by viewModel.reviewText.observeAsState(initial = "")
    val selectedStars: Int by viewModel.selectedStars.observeAsState(initial = 0)

    Dialog(
        onDismissRequest = { viewModel.updateShowReviewScreen(false)}
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF6EC7D7), Color(0xFFAEE2FA), Color(0xFFEAF7F9)),
                    )
                )
                .background(Color.White.copy(alpha = 0.6f), shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.80f)
                .padding(
                    start = 3.dp,
                    top = 3.dp,
                    end = 3.dp,
                    bottom = 3.dp
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .clickable { viewModel.updateShowReviewScreen(false) } // Close on icon click
                            .padding(8.dp)
                    )
                }

                Text(
                    text = "Reseña",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )

                Text(
                    text = "Título",
                    modifier = Modifier.padding(vertical = 2.dp)
                )

                TextField(
                    value = reviewTitle,
                    onValueChange = { viewModel.updateReviewTitle(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )

                Text(
                    text = "Escribe tu reseña",
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                TextField(
                    value = reviewText,
                    onValueChange = { viewModel.updateReviewText(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp)
                        .padding(8.dp),
                    maxLines = 6
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            if (index < selectedStars) {
                                Icons.Filled.Star
                            } else {
                                Icons.Default.Star
                            },
                            tint = if (index < selectedStars) {
                                Color(0xFFffd966)
                            } else {
                                Color.Unspecified
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    viewModel.updateStars(index + 1);
                                }
                        )
                    }
                }

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.material.MaterialTheme.colors.background),
                    onClick = {
                        viewModel.createReview(reviewTitle, reviewText, selectedStars)
                        viewModel.updateShowReviewScreen(false)
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Guardar Reseña")
                }
            }
        }
    }
}

@Composable
fun ReviewList(reviews: List<Review>) {
    Column {
        Text(
            text = "Reseñas de los usuarios",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        Divider()
        reviews.forEach { review ->
            ReviewItem(review = review)
            Divider()
        }
    }
}
@Composable
fun ReviewItem(review: Review) {
    val initial = review.authorName!!.substring(0, 1).uppercase()

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color(0xFFffa07a)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initial,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            review.authorName?.let { Text(text = it, fontWeight = FontWeight.Bold) }
            review.title?.let { Text(text = it, fontWeight = FontWeight.SemiBold, color = Color.DarkGray) }
            review.description?.let { Text(text = it, textAlign = TextAlign.Justify) }
            Row {
                repeat(5) { index ->
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < review.rate!!) {
                            Color(0xFFffd966)
                        } else {
                            Color.Unspecified
                        },
                    )
                }
            }
            Text(text = review.creationDate.toString(), color = Color.Gray)
        }
    }
}


