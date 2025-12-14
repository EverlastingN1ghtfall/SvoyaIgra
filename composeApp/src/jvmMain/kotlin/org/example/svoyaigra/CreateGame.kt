package org.example.svoyaigra

import java.util.Scanner

fun createGame(): Game {
    println("Создание \"Своей игры\"\n\nВ \"Своей игре\" 6 тем, в каждой из которых 5 впоросов разной сложности")
    val scanner: Scanner = Scanner(System.`in`)
    println("\nВведите название игры:")
    val name = scanner.next()
    var theme: String
    var question: String
    var answer: String

    val themes = mutableListOf<Theme>()

    for (i in 1..6) {
        println("\nВведите название темы $i:")
        val themeName = scanner.next()
        val questions = mutableListOf<Question>()
        for (j in 1..5) {
            val points = j * 100
            println("\nВведите вопрос $j темы \"$themeName\" стоимостью $points:")
            val questionText = scanner.next()
            println("Введите ответ на вопрос $j:")
            val answerText = scanner.next()
            questions.add(Question(text = questionText, answer = answerText, points = points))
        }
        themes.add(Theme(name = themeName, questions = questions))
    }

    val round = Round(name="Раунд 1", themes=themes)
    val game = Game(name=name, rounds=listOf(round))


    saveGameToJSON(game, "своя_игра_${name.lowercase().replace(" ", "_")}.json")
    return game
}
