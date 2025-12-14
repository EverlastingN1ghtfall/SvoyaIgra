package org.example.svoyaigra

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() {
    println("Хотите создать новую игру? (y/n)")
    val scanner = java.util.Scanner(System.`in`)
    val createNew = scanner.next().lowercase() == "y"

    val game: Game = if (createNew) {
        createGame()
    } else {
        println("Введите имя файла для загрузки (например: svoya_igra_my_game.json):")
        val filename = scanner.next()
        loadGameFromJSON(filename) ?: run {
            println("Не удалось загрузить файл. Создаём пустую игру.")
            Game("Пустая игра", listOf())
        }
    }

    application {
        val sizeMult = 300
        Window(
            onCloseRequest = ::exitApplication,
            title = "Своя Игра - ${game.name}",
            resizable = false,
            state = WindowState(size = DpSize((4 * sizeMult).dp, (3 * sizeMult).dp))
        ) {
            QuestionSelectionScreen(game)
        }
    }
}
