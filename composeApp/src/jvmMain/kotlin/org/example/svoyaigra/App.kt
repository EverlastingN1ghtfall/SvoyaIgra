package org.example.svoyaigra

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.max

@Composable
fun SimpleBackgroundScreen() {
    val darkBlue = Color(0xFF3129D6)
    val lightBlue = Color(0xFF82B1FF)
    val darkGray = Color(0xFF2E2E2E)
    val lightGray = Color(0xFF595959)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                val center = Offset(size.width / 2f, (size.height - 225f) / 2f)
                val radius = max(size.width, size.height) * 0.3f
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(lightBlue, darkBlue),
                        center = center,
                        radius = radius
                    )
                )
                val rectHeight = 225f
                val top = size.height - rectHeight
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(darkGray, lightGray),
                        startY = top,
                        endY = size.height
                    ),
                    topLeft = Offset(0f, top),
                    size = androidx.compose.ui.geometry.Size(size.width, rectHeight)
                )
                drawLine(
                    color = Color.White,
                    strokeWidth = 5f,
                    start = Offset(0f, top),
                    end = Offset(size.width, top)
                )
            }
    )
}

@Composable
fun QuestionLineGrid(rows: Int = 6, cols: Int = 5) {
    Box(modifier = Modifier.fillMaxSize().drawBehind {
        val rectHeight = 225f
        val verticalMargin = (size.height - rectHeight) / rows
        val horizontalMargin = size.width / (cols + 1)

        for (i in 1 until rows) {
            drawLine(
                color = Color.White,
                strokeWidth = 2f,
                start = Offset(0f, verticalMargin * i),
                end = Offset(size.width, verticalMargin * i)
            )
        }
        for (i in 1..cols) {
            drawLine(
                color = Color.White,
                strokeWidth = 2f,
                start = Offset(horizontalMargin * i, 0f),
                end = Offset(horizontalMargin * i, size.height - rectHeight)
            )
        }
    })
}

@Composable
fun ButtonGridWithLabels(
    themes: List<Theme>,
    answered: List<Boolean>,
    onClick: (row: Int, col: Int, index: Int) -> Unit
) {
    val rows = themes.size
    val cols = 5
    val buttonWidth = 148.dp
    val buttonHeight = 106.dp
    val labelWidth = 445.dp

    Column(modifier = Modifier.fillMaxSize().padding(bottom = 225.dp)) {
        for (r in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth().height(buttonHeight),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = themes.getOrNull(r)?.name ?: "",
                    modifier = Modifier.width(labelWidth).padding(start = (labelWidth / 2) - 20.dp),
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )

                for (c in 0 until cols) {
                    val index = r * cols + c
                    val question = themes.getOrNull(r)?.questions?.getOrNull(c)
                    if (answered.getOrNull(index) == true || question == null) {
                        Spacer(
                            modifier = Modifier.width(buttonWidth).height(buttonHeight)
                        )
                    } else {
                        Button(
                            onClick = { onClick(r, c, index) },
                            modifier = Modifier.width(buttonWidth).height(buttonHeight),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("${question.points}", fontSize = 30.sp, fontFamily = FontFamily.Serif)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RenderQuestion(question: Question?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = question?.text ?: "Здесь будет вопрос",
            fontSize = 36.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Black,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun TeamScoreBar(
    modifier: Modifier = Modifier,
    teamNames: List<String> = listOf("Команда 1", "Команда 2", "Команда 3"),
    teamScores: List<Int> = listOf(0, 0, 0),
    onScoreIncrease: (teamIndex: Int) -> Unit = {},
    onScoreDecrease: (teamIndex: Int) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxWidth().height(225.dp), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until 3) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = teamNames.getOrNull(i) ?: "",
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(60.dp)
                            .border(width = 4.dp, color = Color.White, shape = RoundedCornerShape(0.dp))
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        val button = event.buttons
                                        if (button.isPrimaryPressed) {
                                            onScoreIncrease(i)
                                        } else if (button.isSecondaryPressed) {
                                            onScoreDecrease(i)
                                        }
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${teamScores.getOrNull(i) ?: 0}",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.Yellow
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionSelectionScreen(game: Game) {
    val rows = 6
    val cols = 5
    val totalQuestions = rows * cols

    var showQuestion by remember { mutableStateOf(false) }
    var currentQuestion by remember { mutableStateOf<Question?>(null) }
    var answered by remember { mutableStateOf(List(totalQuestions) { false }) }
    var currentQuestionIndex by remember { mutableStateOf(-1) }
    var teamScores by remember { mutableStateOf(listOf(0, 0, 0)) }

    Box(modifier = Modifier.fillMaxSize()) {

        // Фон
        SimpleBackgroundScreen()

        // Сетка с линиями и кнопками вопросов
        if (!showQuestion) {
            Column(modifier = Modifier.fillMaxSize().padding(bottom = 225.dp)) {
                game.rounds.firstOrNull()?.themes?.forEachIndexed { r, theme ->
                    Row(modifier = Modifier.fillMaxWidth().height(106.dp), verticalAlignment = Alignment.CenterVertically) {
                        // Тема
                        Text(
                            text = theme.name,
                            modifier = Modifier.width(445.dp).padding(start = 16.dp),
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.Black
                        )

                        // Вопросы
                        theme.questions.forEachIndexed { c, question ->
                            val index = r * cols + c
                            if (answered.getOrNull(index) == true) {
                                Spacer(modifier = Modifier.width(148.dp).height(106.dp))
                            } else {
                                Button(
                                    onClick = {
                                        currentQuestion = question
                                        currentQuestionIndex = index
                                        showQuestion = true
                                        answered = answered.toMutableList().also { it[index] = true }
                                    },
                                    modifier = Modifier.width(148.dp).height(106.dp),
                                    shape = RoundedCornerShape(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    ),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${question.points}",
                                            fontSize = 30.sp,
                                            fontFamily = FontFamily.Serif,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Линии поверх кнопок
            QuestionLineGridDraw()
        }

        // Экран вопроса
        if (showQuestion && currentQuestion != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                RenderQuestion(currentQuestion)

                // Кнопка "Назад"
                Button(
                    onClick = { showQuestion = false },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .width(200.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("Назад", fontSize = 24.sp)
                }
            }
        }

        // Панель очков команд
        TeamScoreBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            teamScores = teamScores,
            onScoreIncrease = { i -> teamScores = teamScores.toMutableList().also { it[i] += 100 } },
            onScoreDecrease = { i -> teamScores = teamScores.toMutableList().also { it[i] -= 100 } }
        )
    }
}

@Composable
fun QuestionLineGridDraw() {
    val rows = 6
    val cols = 5
    val bottomPanelHeight = 225.dp
    val leftMargin = 445.dp // ширина колонки с названиями тем

    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            val density = this@drawBehind
            val rectHeight = bottomPanelHeight.toPx()
            val availableHeight = size.height - rectHeight

            val rowHeight = availableHeight / rows

            // Горизонтальные линии
            for (i in 1 until rows) {
                val y = rowHeight * i
                drawLine(
                    color = Color.White,
                    strokeWidth = 3f,
                    start = Offset(0f, y),
                    end = Offset(size.width, y)
                )
            }

            // Вертикальные линии
            val buttonWidthPx = 148.dp.toPx() // теперь внутри drawBehind корректно
            for (i in 0..cols) { // 6 линий
                val x = leftMargin.toPx() + buttonWidthPx * i
                drawLine(
                    color = Color.White,
                    strokeWidth = 3f,
                    start = Offset(x, 0f),
                    end = Offset(x, availableHeight)
                )
            }
        }
    )
}
