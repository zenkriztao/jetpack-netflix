package com.zenkriztao.netflix

import androidx.compose.ui.window.ComposeUIViewController
import com.zenkriztao.netflix.ui.navigation.Screen

fun mainViewController() = ComposeUIViewController { JetflixApp(startScreen = Screen.Splash) }
