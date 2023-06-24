package com.example.chatgbt.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.chatgbt.MyNavigation
import com.example.chatgbt.models.UserMessage
import com.example.chatgbt.viewmodel.ApiViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<ApiViewModel>()
    val isLoading = remember {
        mutableStateOf(false)
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF141922),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(text = "Ask GPT-3.5" , color = Color.Black)
                }
            )
        }) { padding ->
        Modifier.padding(padding)
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 60.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                ChatScreenContent(viewModel, isLoading.value)
            }
            MessageField(viewModel, {
                isLoading.value = it
            })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(viewModel: ApiViewModel, isLoading: Boolean) {
    var userMessages = viewModel.messageList
    val lazyListState = rememberLazyListState()
    val chatResponse = viewModel.chatResponse.observeAsState()

    Log.d("isLoading", isLoading.toString())

    LaunchedEffect(chatResponse.value) {
        if (chatResponse.value != null) {
            lazyListState.animateScrollToItem(userMessages.size - 1)
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(vertical = 8.dp),
        state = lazyListState
    ) {
        if (!isLoading) {
            items(userMessages) { userMsg ->
                UserMessageCard(userMessage = userMsg , viewModel = viewModel)
            }
        } else {
            items(userMessages) { userMsg ->
                UserMessageCard(userMessage = userMsg, isLoading = true , viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageField(viewModel: ApiViewModel, isLoading: (Boolean) -> Unit) {
    val chatResponse = viewModel.chatResponse.observeAsState().value

    var userWords = remember {
        mutableStateOf("")
    }
    var userMessageContent = remember { mutableStateOf("") }
    val sendEnabled = remember {
        mutableStateOf(false)
    }

    Row(Modifier.fillMaxWidth()) {
        TextField(value = userMessageContent.value,
            shape = RoundedCornerShape(15.dp),
            onValueChange = {
                userMessageContent.value = it
                if (it.trim().isEmpty()) sendEnabled.value = false
                else sendEnabled.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFF848586),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF141922)
            ),
            placeholder = {
                Text(
                    text = "Ask GPT-3.5 anything",
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        val chatRequest = viewModel.getChatRequest(userMessageContent.value)
                        viewModel.addMessage(UserMessage(message = userMessageContent.value))
                        viewModel.sendApiRequest(chatRequest)
                        userMessageContent.value = ""
                        sendEnabled.value = false
                        isLoading(true)
                    },
                    enabled = sendEnabled.value
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = if (sendEnabled.value) Color(0xFF141922) else Color.LightGray// Content description for accessibility
                    )
                }
            })
    }
}


@Composable
fun UserMessageCard(userMessage: UserMessage, isLoading: Boolean = false, viewModel: ApiViewModel) {
    var typedText by remember { mutableStateOf("") }
//    var loadingText by remember { mutableStateOf("Loading") }
//    val load = viewModel.isLoading.observeAsState()
    val scope = rememberCoroutineScope()

//    var loading by remember {
//        mutableStateOf(load.value)
//    }

//        typedText = "Loading"
    LaunchedEffect(userMessage.message) {
        scope.launch {
            for (i in userMessage.message.indices) {
                delay(60) // Adjust typing speed as desired
                typedText = userMessage.message.substring(0, i + 1)
            }
        }
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(
                vertical = 10.dp, horizontal = if (userMessage.isUser) 10.dp else 20.dp
            )
            .width(if (userMessage.isUser) 320.dp else 340.dp),
        colors = if (userMessage.isUser) CardDefaults.cardColors(
            containerColor = Color.White
        ) else CardDefaults.cardColors(
            containerColor = Color.Gray
        )
    ) {
        Text(
            text = if (userMessage.isUser) userMessage.message else typedText,
            textAlign = if (userMessage.isUser) TextAlign.Start else TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Bold, fontSize = 15.sp
            )
        )
    }
//    viewModel.changeLoading(false)
}