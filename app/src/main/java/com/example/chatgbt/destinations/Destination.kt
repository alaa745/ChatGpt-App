package com.example.chatgbt.destinations

interface Destination {
    val route: String
}

object Splash: Destination{
    override val route = "Splash"
}

object Home: Destination{
    override val route = "Home"
}