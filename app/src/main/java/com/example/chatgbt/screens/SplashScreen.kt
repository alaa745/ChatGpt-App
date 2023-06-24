package com.example.chatgbt.screens

import android.window.SplashScreen
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatgbt.R
import com.example.chatgbt.destinations.Home
import com.example.chatgbt.destinations.Splash
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController){
    SplashScreenContent(navHostController = navHostController)
}

@Composable
fun AnimatedLogo() {

    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier
            .size(200.dp)
//            .rotate(rotationAngle)
    )
}

@Composable
fun SplashScreenContent(navHostController: NavHostController){
    Box(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            AnimatedLogo()
        }
    }
    LaunchedEffect(true){
        delay(4000)
        navHostController.navigate(Home.route){
            popUpTo(Splash.route){
                inclusive = true
            }
        }
    }
}