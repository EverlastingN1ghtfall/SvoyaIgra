package org.example.svoyaigra

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily

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
            .safeContentPadding()
    )
}


@Composable
fun QuestionLineGrid() {
    Box(modifier = Modifier.fillMaxSize().drawBehind {
        val rectHeight = 225f
        val verticalMargin = (size.height - rectHeight) / 6f
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(0f, verticalMargin * 1f),
            end = Offset(size.width, verticalMargin * 1f)
        )
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(0f, verticalMargin * 2f),
            end = Offset(size.width, verticalMargin * 2f)
        )
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(0f, verticalMargin * 3f),
            end = Offset(size.width, verticalMargin * 3f)
        )
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(0f, verticalMargin * 4f),
            end = Offset(size.width, verticalMargin * 4f)
        )
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(0f, verticalMargin * 5f),
            end = Offset(size.width, verticalMargin * 5f)
        )

        val horizontalMargin = size.width / 8f
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(horizontalMargin * 3f, 0f),
            end = Offset(horizontalMargin * 3f, size.height - rectHeight)
        )
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(horizontalMargin * 4f, 0f),
            end = Offset(horizontalMargin * 4f, size.height - rectHeight)
        )
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(horizontalMargin * 5f, 0f),
            end = Offset(horizontalMargin * 5f, size.height - rectHeight)
        )
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(horizontalMargin * 6f, 0f),
            end = Offset(horizontalMargin * 6f, size.height - rectHeight)
        )
        drawLine(
            color = Color.White,
            strokeWidth = 2f,
            start = Offset(horizontalMargin * 7f, 0f),
            end = Offset(horizontalMargin * 7f, size.height - rectHeight)
        )
    })
}


@Composable
fun ButtonGridWithLabels(
    topics: List<String>,
    rows: Int = 6,
    cols: Int = 5,
    buttonWidth: Dp = 148.dp,
    buttonHeight: Dp = 106.dp,
    labelWidth: Dp = 445.dp,
    answered: List<Boolean>,
    onClick: (row: Int, col: Int, index: Int) -> Unit = { _, _, _ -> }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 225.dp)
    ) {
        for (r in 0 until rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(buttonHeight),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = topics.getOrNull(r) ?: "",
                    modifier = Modifier
                        .width(labelWidth)
                        .padding(start = (labelWidth / 2) - 20.dp),
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )
                for (c in 0 until cols) {
                    val index = r * cols + c
                    if (answered.getOrNull(index) == true) {
                        // уже отвеченная клетка: делаем её невидимой и некликабельной
                        Spacer(
                            modifier = Modifier
                                .width(buttonWidth)
                                .height(buttonHeight)
                        )
                    } else {
                        Button(
                            onClick = { onClick(r, c, index) },
                            modifier = Modifier
                                .width(buttonWidth)
                                .height(buttonHeight),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "${100 * (c + 1)}",
                                fontSize = 30.sp,
                                fontFamily = FontFamily.Serif
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RenderQuestion() {
    Box(modifier = Modifier.fillMaxSize(), ) {
        Text(
            text = "Здесь будет вопрос", // PLACEHOLDER
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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(225.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until 3) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                            .border(
                                width = 4.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(0.dp)
                            )
                            .padding(4.dp)
                            .drawBehind { drawRect(Color.Black) }
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
@Preview
fun QuestionSelectionScreen() {
    MaterialTheme {
        var showQuestion by remember { mutableStateOf(false) }
        var teamScores by remember { mutableStateOf(listOf(0, 0, 0)) }
        val rows = 6
        val cols = 5
        var answered by remember { mutableStateOf(List(rows * cols) { false }) }

        Box(modifier = Modifier.fillMaxSize()){
            SimpleBackgroundScreen()
            if (showQuestion) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    RenderQuestion()
                    Button(
                        onClick = { showQuestion = false },
                        modifier = Modifier
                            .absoluteOffset(x = (-480).dp, y = (-240).dp)
                            .width(200.dp)
                            .height(60.dp)
                            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(0.dp)),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text("Назад", fontSize = 24.sp)
                    }
                }
            } else {
                QuestionLineGrid()
                val topics = listOf("Тема 1", "Тема 2", "Тема 3", "Тема 4", "Тема 5", "Тема 6")
                ButtonGridWithLabels(
                    topics = topics,
                    rows = rows,
                    cols = cols,
                    answered = answered,
                    onClick = { row, col, index ->
                        // показываем вопрос
                        showQuestion = true
                        // помечаем клетку как отвеченную
                        answered = answered.toMutableList().also { list ->
                            list[index] = true
                        }
                        // здесь же можно запомнить, за сколько очков был вопрос и кому их дать
                    }
                )
            }
            TeamScoreBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                teamScores = teamScores,
                onScoreIncrease = { index ->
                    teamScores = teamScores.toMutableList().also { list ->
                        list[index] = list[index] + 100
                    }
                },
                onScoreDecrease = { index ->
                    teamScores = teamScores.toMutableList().also { list ->
                        list[index] = list[index] - 100
                    }
                }
            )
        }
    }
}