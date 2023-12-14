package com.example.skan.presentation.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.MaterialTheme as AppTheme
import com.example.skan.R
import com.example.skan.presentation.viewModel.WelcomeViewModel
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun JetComposeApp(startScreen: String) {
    val viewModel: WelcomeViewModel = WelcomeViewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startScreen) {
        composable("welcome") { WelcomeScreen(navController, viewModel, LocalContext.current) }
        composable("register") { RegisterScreen(navController, viewModel, LocalContext.current) }
        composable("login") { LoginScreen(navController, viewModel, LocalContext.current) }
        composable("home"){ NavigationBar() }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController, viewModel: WelcomeViewModel, context: Context) {
    val literataFontFamily = FontFamily(
        Font(R.font.literata_semibold),
    )
    Surface(color = AppTheme.colors.background) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome),
                contentDescription = "Welcome Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = {
                        viewModel.markWelcomeAsShown(context)
                        navController.navigate("register")
                    },
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(0.35f)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = "Comencemos",
                        fontFamily = literataFontFamily,
                        color = Color.White.copy(alpha = 0.8f),
                        style = TextStyle(fontSize = 17.sp)
                        )
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: WelcomeViewModel, context: Context) {
    val literataFontFamily = FontFamily(
        Font(R.font.literata_semibold),
    )
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val isSaved by viewModel.isSaved.collectAsStateWithLifecycle()
    val errorMsg by viewModel.errorMsg.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    if (isSaved){
        email.value = ""
        password.value = ""
        name.value = ""
        focusManager.clearFocus()
    }

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
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Registrarse",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(fontSize = 45.sp, fontFamily = FontFamily(Font(R.font.literata_bold),))
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Nombre Completo", fontFamily = literataFontFamily)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    placeholderColor = Color.Black,
                    cursorColor = Color.Gray),
                textStyle = TextStyle(fontFamily = literataFontFamily)
            )
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Dirección de Correo", fontFamily = literataFontFamily)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    placeholderColor = Color.Black,
                    cursorColor = Color.Gray),
                textStyle = TextStyle(fontFamily = literataFontFamily)
            )
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña", fontFamily = literataFontFamily)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    placeholderColor = Color.Black,
                    cursorColor = Color.Gray),
                textStyle = TextStyle(fontFamily = literataFontFamily),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Text(text = "Terms of use", fontFamily = literataFontFamily,
                    color = Color.Black,
                    style = TextStyle(fontSize = 10.sp)
                )
                Text(text = " and ", fontFamily = literataFontFamily,
                    color = AppTheme.colors.primary,
                    style = TextStyle(fontSize = 10.sp)
                )
                Text(text = "Privacy Policy", fontFamily = literataFontFamily,
                    color = Color.Black,
                    style = TextStyle(fontSize = 10.sp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.45f),
                onClick = {
                viewModel.createUser(name.value , email.value , password.value , context)
                }
            ) {
                Text(text = "Crear Cuenta", fontFamily = literataFontFamily,
                    color = Color.White.copy(alpha = 0.8f),
                    style = TextStyle(fontSize = 17.sp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "O usa tu cuenta de ", fontFamily = literataFontFamily,
                color = Color.Black,
                style = TextStyle(fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp)
                        .clickable { },
                    tint = Color.Unspecified,
                )
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp)
                        .clickable { },
                    tint = Color.Unspecified,
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            if (errorMsg.length > 0){
                androidx.compose.material3.Text(
                    text = errorMsg,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = if (!isSaved) Color.Red else AppTheme.colors.primary,
                    fontFamily = literataFontFamily
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .wrapContentSize(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier
                            .padding(bottom = 25.dp)
                            .fillMaxWidth(0.35f)
                            .clip(RoundedCornerShape(20.dp))
                    ){
                        Text(text = "Saltar", fontFamily = literataFontFamily,
                            color = Color.White.copy(alpha = 0.8f),
                            style = TextStyle(fontSize = 15.sp)
                        )
                    }
                    Button(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier
                            .padding(bottom = 25.dp)
                            .fillMaxWidth(0.55f)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    {
                        Text(text = "Iniciar Sesión", fontFamily = literataFontFamily,
                            color = Color.White.copy(alpha = 0.8f),
                            style = TextStyle(fontSize = 15.sp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController, viewModel: WelcomeViewModel, context: Context) {
    val literataFontFamily = FontFamily(
        Font(R.font.literata_semibold),
    )
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLogged by viewModel.isLogged.collectAsStateWithLifecycle()
    val errorMsg2 by viewModel.errorMsg2.collectAsStateWithLifecycle()

    if(isLogged) {
        navController.navigate("home")
    }
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
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 150.dp)
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Iniciar Sesión",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(fontSize = 45.sp, fontFamily = FontFamily(Font(R.font.literata_bold),))
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Dirección de Correo", fontFamily = literataFontFamily)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    placeholderColor = Color.Black,
                    cursorColor = Color.Gray),
                textStyle = TextStyle(fontFamily = literataFontFamily)
            )
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña", fontFamily = literataFontFamily)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    placeholderColor = Color.Black,
                    cursorColor = Color.Gray),
                textStyle = TextStyle(fontFamily = literataFontFamily),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(35.dp))
            Button(
                onClick = {
                    viewModel.loginUser(email.value , password.value, context)
                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(0.40f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(text = "Inicio", fontFamily = literataFontFamily,
                    color = Color.White.copy(alpha = 0.8f),
                    style = TextStyle(fontSize = 17.sp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "O usa tu cuenta de ", fontFamily = literataFontFamily,
                color = Color.Black,
                style = TextStyle(fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp)
                        .clickable { },
                    tint = Color.Unspecified,
                )
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp)
                        .clickable { },
                    tint = Color.Unspecified,
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            if (errorMsg2.length > 0){
                androidx.compose.material3.Text(
                    text = errorMsg2,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.Red,
                    fontFamily = literataFontFamily
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home screen",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(2.dp)
                        .clickable { navController.navigate("home")},
                    tint = AppTheme.colors.primary,
                )
            }
        }
    }
}