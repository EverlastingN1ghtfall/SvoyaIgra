package org.example.svoyaigra

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    val sizeMult = 300
    Window(
        onCloseRequest = ::exitApplication,
        title = "Своя Игра",
        state = WindowState(size = DpSize((4 * sizeMult).dp, (3 * sizeMult).dp))
    ) {
        QuestionSelectionScreen()
    }
}