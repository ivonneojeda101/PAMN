package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BussinessCardApp()
                }
            }
        }
    }
}

@Composable
fun BussinessCardApp() {
    Column (
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colors.primary),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                ) {
                    Box(
                        Modifier.background(color = MaterialTheme.colors.primaryVariant)
                    ) {
                        val image = painterResource(R.drawable.android_logo)
                        Image(
                            painter = image,
                            contentDescription = "Android Logo",
                            modifier = Modifier
                                .width(100.dp) // Scale width to 50% of the original
                                .height(100.dp) // Scale height to 50% of the original
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(4.dp),
                ) {
                    Text(
                        text = stringResource(R.string.name),
                        fontSize = 48.sp
                    )
                }
                Row(
                    modifier = Modifier.padding(4.dp),
                ) {
                    Text(
                        text = stringResource(R.string.role),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
        ) {
            Column() {
                val myAppIcons = Icons.Rounded
                Row() {
                    Column(
                        modifier = Modifier
                            .weight(0.31f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Icon(
                            myAppIcons.Phone,
                            tint = MaterialTheme.colors.secondaryVariant,
                            contentDescription = "Phone Icon"
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.69f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = stringResource(R.string.phone_number)
                        )
                    }
                }
                Row() {
                    Column(
                        modifier = Modifier
                            .weight(0.31f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.End,

                        ) {
                        Icon(
                            myAppIcons.Share,
                            tint = MaterialTheme.colors.secondaryVariant,
                            contentDescription = "Share Icon"
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.69f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = stringResource(R.string.account)
                        )
                    }
                }
                Row() {
                    Column(
                        modifier = Modifier
                            .weight(0.31f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.End,

                        ) {
                        Icon(
                            myAppIcons.Email,
                            tint = MaterialTheme.colors.secondaryVariant,
                            contentDescription = "Email Icon"
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.69f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = stringResource(R.string.email)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardTheme {
        BussinessCardApp()
    }
}