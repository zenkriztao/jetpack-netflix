package com.zenkriztao.netflix

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.zenkriztao.netflix.di.initializeKoin
import com.zenkriztao.netflix.ui.navigation.Screen
import jetflix.composeapp.generated.resources.Res
import jetflix.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

fun main() = application {
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
    ) {
        JetflixApp(startScreen = Screen.Splash)
    }
}
