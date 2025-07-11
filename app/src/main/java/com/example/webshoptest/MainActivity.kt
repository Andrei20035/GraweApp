package com.example.webshoptest

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.webshoptest.ui.navigation.WebShopNavigation
import com.example.webshoptest.ui.theme.WebShopTestTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebShopTestTheme {
                WebShopAppUI()
            }
        }
    }
}

@HiltAndroidApp
class WebShopApp: Application()

@Composable
fun WebShopAppUI(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Surface(
        modifier = modifier,
    ) {
        WebShopNavigation(navController = navController)
    }
}