package com.example.chatgbt

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatgbt.destinations.Home
import com.example.chatgbt.destinations.Splash
import com.example.chatgbt.models.UserMessage
import com.example.chatgbt.screens.HomeScreen
import com.example.chatgbt.screens.SplashScreen
import com.example.chatgbt.ui.theme.ChatGbtTheme
import com.example.chatgbt.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatGbtTheme {
                // A surface container using the 'background' color from the theme
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    MyNavigation()
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Splash.route){
        composable(Splash.route){
            SplashScreen(navController)
        }
        composable(Home.route){
            HomeScreen()
        }
    }
}



//        LaunchedEffect(userMessage.message) {
//            scope.launch {
//                for (i in userMessage.message.indices) {
//                    delay(60) // Adjust typing speed as desired
//                    typedText = userMessage.message.substring(0, i + 1)
//                }
//            }
//        }
//        Card(
//            shape = RoundedCornerShape(16.dp),
//            modifier = Modifier
//                .padding(
//                    vertical = 10.dp, horizontal = if (userMessage.isUser) 10.dp else 20.dp
//                )
//                .width(if (userMessage.isUser) 320.dp else 340.dp),
//            colors = if (userMessage.isUser) CardDefaults.cardColors(
//                containerColor = Color.White
//            ) else CardDefaults.cardColors(
//                containerColor = Color.Gray
//            )
//        ) {
//            Text(
//                text = if (userMessage.isUser) userMessage.message else typedText,
//                textAlign = if (userMessage.isUser) TextAlign.Start else TextAlign.End,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                color = Color.Black,
//                style = TextStyle(
//                    fontWeight = FontWeight.Bold, fontSize = 15.sp
//                )
//            )
//        }
//    }

//    Card(
//        shape = RoundedCornerShape(16.dp),
//        modifier = Modifier
//            .padding(
//                vertical = 10.dp, horizontal = if (userMessage.isUser) 10.dp else 20.dp
//            )
//            .width(if (userMessage.isUser) 320.dp else 340.dp),
//        colors = if (userMessage.isUser) CardDefaults.cardColors(
//            containerColor = Color.White
//        ) else CardDefaults.cardColors(
//            containerColor = Color.Gray
//        )
//    ) {
//        Text(
//            text = if (userMessage.isUser) userMessage.message else typedText,
//            textAlign = if (userMessage.isUser) TextAlign.Start else TextAlign.End,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            color = Color.Black,
//            style = TextStyle(
//                fontWeight = FontWeight.Bold, fontSize = 15.sp
//            )
//        )
//    }

