package org.example.svoyaigra

import java.io.File


data class Game(
    val name: String,
    val rounds: List<Round>
) {
    fun toMap(): Map<String, Any> = mapOf(
        "name" to name,
        "rounds" to rounds.map { it.toMap() }
    )
}

data class Round(
    val name: String,
    val themes: List<Theme>
) {
    fun toMap(): Map<String, Any> = mapOf(
        "name" to name,
        "themes" to themes.map { it.toMap() }
    )
}

data class Theme(
    val name: String,
    val questions: List<Question>
) {
    fun toMap(): Map<String, Any> = mapOf(
        "name" to name,
        "questions" to questions.map { it.toMap() }
    )
}

data class Question(
    val text: String,
    val answer: String,
    val points: Int
) {
    fun toMap(): Map<String, Any> = mapOf(
        "text" to text,
        "answer" to answer,
        "points" to points
    )
}


fun saveGameToJSON(game: Game, filename: String) {
    val jsonString = mapToJsonString(game.toMap())
    File(filename).writeText(jsonString)
}

fun mapToJsonString(map: Map<String, Any>): String {
    val json = StringBuilder()
    json.append("{\n")

    map.entries.forEachIndexed { index, (key, value) ->
        json.append("  \"$key\": ")
        when (value) {
            is String -> json.append("\"${escapeString(value)}\"")
            is Number, is Boolean -> json.append(value.toString())
            is List<*> -> json.append(listToJsonString(value))
            is Map<*, *> -> json.append(mapToJsonString(value as Map<String, Any>))
            else -> json.append("\"${value.toString()}\"")
        }
        if (index < map.size - 1) json.append(",")
        json.append("\n")
    }

    json.append("}")
    return json.toString()
}

fun listToJsonString(list: List<*>): String {
    val json = StringBuilder()
    json.append("[\n")

    list.forEachIndexed { index, item ->
        when (item) {
            is Map<*, *> -> json.append(mapToJsonString(item as Map<String, Any>))
            is String -> json.append("  \"${escapeString(item)}\"")
            else -> json.append("  ${item.toString()}")
        }
        if (index < list.size - 1) json.append(",")
        json.append("\n")
    }

    json.append("]")
    return json.toString()
}

fun escapeString(str: String): String {
    return str.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
}


//fun main() {
//    val questions1 = listOf(
//        Question("Вопрос 1.1", "Ответ 1.1", 100),
//        Question("Вопрос 1.2", "Ответ 1.2", 200),
//        Question("Вопрос 1.3", "Ответ 1.3", 300),
//        Question("Вопрос 1.4", "Ответ 1.4", 400),
//        Question("Вопрос 1.5", "Ответ 1.5", 500)
//    )
//    val questions2 = listOf(
//        Question("Вопрос 2.1", "Ответ 2.1", 100),
//        Question("Вопрос 2.2", "Ответ 2.2", 200),
//        Question("Вопрос 2.3", "Ответ 2.3", 300),
//        Question("Вопрос 2.4", "Ответ 2.4", 400),
//        Question("Вопрос 2.5", "Ответ 2.5", 500)
//    )
//    val questions3 = listOf(
//        Question("Вопрос 3.1", "Ответ 3.1", 100),
//        Question("Вопрос 3.2", "Ответ 3.2", 200),
//        Question("Вопрос 3.3", "Ответ 3.3", 300),
//        Question("Вопрос 3.4", "Ответ 3.4", 400),
//        Question("Вопрос 3.5", "Ответ 3.5", 500)
//    )
//    val questions4 = listOf(
//        Question("Вопрос 4.1", "Ответ 4.1", 100),
//        Question("Вопрос 4.2", "Ответ 4.2", 200),
//        Question("Вопрос 4.3", "Ответ 4.3", 300),
//        Question("Вопрос 4.4", "Ответ 4.4", 400),
//        Question("Вопрос 4.5", "Ответ 4.5", 500)
//    )
//    val questions5 = listOf(
//        Question("Вопрос 5.1", "Ответ 5.1", 100),
//        Question("Вопрос 5.2", "Ответ 5.2", 200),
//        Question("Вопрос 5.3", "Ответ 5.3", 300),
//        Question("Вопрос 5.4", "Ответ 5.4", 400),
//        Question("Вопрос 5.5", "Ответ 5.5", 500)
//    )
//    val themes1 = listOf(
//        Theme(name = "Тема 1", questions = questions1),
//        Theme(name = "Тема 2", questions = questions2),
//        Theme(name = "Тема 3", questions = questions3),
//        Theme(name = "Тема 4", questions = questions4),
//        Theme(name = "Тема 5", questions = questions5)
//    )
//
//    val game = Game(name = "СвояИгра", rounds = listOf(Round(name = "Раунд 1", themes = themes1)))
//
//    saveGameToJSON(game, "game.json")
//    println("Файл сохранен")
//}

fun loadGameFromJSON(filename: String): Game? {
    return try {
        val jsonString = File(filename).readText()
        parseGameFromJson(jsonString)
    } catch (e: Exception) {
        println("Ошибка при загрузке файла: ${e.message}")
        null
    }
}

fun parseGameFromJson(jsonString: String): Game {
    val gameMap = parseJsonObject(jsonString)
    return mapToGame(gameMap)
}

fun parseJsonObject(jsonString: String): Map<String, Any> {
    val result = mutableMapOf<String, Any>()
    var content = jsonString.trim()

    if (content.startsWith("{") && content.endsWith("}")) {
        content = content.substring(1, content.length - 1).trim()
    }

    var index = 0
    while (index < content.length) {
        if (content[index] == '"') {
            val keyEnd = findStringEnd(content, index)
            val key = content.substring(index + 1, keyEnd)

            index = keyEnd + 1
            while (index < content.length && content[index] != ':') {
                index++
            }
            index++

            while (index < content.length && content[index].isWhitespace()) {
                index++
            }

            val (value, newIndex) = parseJsonValue(content, index)
            result[key] = value
            index = newIndex

            while (index < content.length &&
                (content[index].isWhitespace() || content[index] == ',')) {
                index++
            }
        } else {
            index++
        }
    }

    return result
}

fun parseJsonValue(content: String, startIndex: Int): Pair<Any, Int> {
    var index = startIndex

    when {
        content[index] == '"' -> {
            val stringEnd = findStringEnd(content, index)
            val value = content.substring(index + 1, stringEnd)
            return Pair(unescapeString(value), stringEnd + 1)
        }
        content[index] == '{' -> {
            val objectEnd = findMatchingBrace(content, index, '{', '}')
            val objContent = content.substring(index, objectEnd + 1)
            val value = parseJsonObject(objContent)
            return Pair(value, objectEnd + 1)
        }
        content[index] == '[' -> {
            val arrayEnd = findMatchingBrace(content, index, '[', ']')
            val arrayContent = content.substring(index, arrayEnd + 1)
            val value = parseJsonArray(arrayContent)
            return Pair(value, arrayEnd + 1)
        }
        else -> {
            val valueEnd = findValueEnd(content, index)
            val valueStr = content.substring(index, valueEnd).trim()
            val value = when {
                valueStr == "true" -> true
                valueStr == "false" -> false
                valueStr.toIntOrNull() != null -> valueStr.toInt()
                valueStr.toDoubleOrNull() != null -> valueStr.toDouble()
                else -> valueStr
            }
            return Pair(value, valueEnd)
        }
    }
}

fun parseJsonArray(jsonString: String): List<Any> {
    val result = mutableListOf<Any>()
    var content = jsonString.trim()

    if (content.startsWith("[") && content.endsWith("]")) {
        content = content.substring(1, content.length - 1).trim()
    }

    var index = 0
    while (index < content.length) {
        if (!content[index].isWhitespace() && content[index] != ',') {
            val (value, newIndex) = parseJsonValue(content, index)
            result.add(value)
            index = newIndex
        } else {
            index++
        }

        while (index < content.length &&
            (content[index].isWhitespace() || content[index] == ',')) {
            index++
        }
    }

    return result
}

fun findStringEnd(content: String, start: Int): Int {
    var index = start + 1
    while (index < content.length) {
        if (content[index] == '"' && content[index - 1] != '\\') {
            return index
        }
        index++
    }
    return content.length
}

fun findMatchingBrace(content: String, start: Int, openBrace: Char, closeBrace: Char): Int {
    var count = 1
    var index = start + 1

    while (index < content.length && count > 0) {
        when (content[index]) {
            openBrace -> count++
            closeBrace -> count--
            '"' -> index = findStringEnd(content, index)
        }
        index++
    }

    return index - 1
}

fun findValueEnd(content: String, start: Int): Int {
    var index = start
    while (index < content.length) {
        when (content[index]) {
            ',', '}', ']' -> return index
        }
        index++
    }
    return content.length
}

fun unescapeString(str: String): String {
    return str.replace("\\\"", "\"")
        .replace("\\\\", "\\")
        .replace("\\n", "\n")
        .replace("\\r", "\r")
        .replace("\\t", "\t")
}

fun mapToGame(map: Map<String, Any>): Game {
    return Game(
        name = map["name"] as? String ?: "",
        rounds = (map["rounds"] as? List<*>)?.map { roundMap ->
            mapToRound(roundMap as Map<String, Any>)
        } ?: emptyList()
    )
}

fun mapToRound(map: Map<String, Any>): Round {
    return Round(
        name = map["name"] as? String ?: "",
        themes = (map["themes"] as? List<*>)?.map { themeMap ->
            mapToTheme(themeMap as Map<String, Any>)
        } ?: emptyList()
    )
}

fun mapToTheme(map: Map<String, Any>): Theme {
    return Theme(
        name = map["name"] as? String ?: "",
        questions = (map["questions"] as? List<*>)?.map { questionMap ->
            mapToQuestion(questionMap as Map<String, Any>)
        } ?: emptyList()
    )
}

fun mapToQuestion(map: Map<String, Any>): Question {
    return Question(
        text = map["text"] as? String ?: "",
        answer = map["answer"] as? String ?: "",
        points = (map["points"] as? Int) ?: (map["points"] as? Double)?.toInt() ?: 0
    )
}


//fun main() {
//    val loadedGame = loadGameFromJSON("game.json")
//
//    if (loadedGame != null) {
//        println("Название: ${loadedGame.name}")
//        println("Количество раундов: ${loadedGame.rounds.size}")
//
//        loadedGame.rounds.forEachIndexed { roundIndex, round ->
//            println("\nРаунд ${roundIndex + 1}: ${round.name}")
//            round.themes.forEachIndexed { themeIndex, theme ->
//                println("  Тема ${themeIndex + 1}: ${theme.name}")
//                theme.questions.forEachIndexed { questionIndex, question ->
//                    println("    Вопрос ${questionIndex + 1}: ${question.text}")
//                    println("    Ответ: ${question.answer}")
//                    println("    Баллы: ${question.points}")
//                }
//            }
//        }
//    } else {
//        println("Не удалось загрузить игру")
//    }
//}