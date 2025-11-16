package org.example.svoyaigra

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
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

import kotlin.math.max


fun changeScreen(questionRow: Int, questionCol: Int) {
    // TODO: Логика изменения экрана на вопрос с заданным идентификатором
}

@Composable
fun generateButtonGrid(verticalMargin: Float, horizontalMargin: Float) {
    Button(
        onClick = { changeScreen(0, 0) },
        modifier = Modifier
            .size(width = horizontalMargin.dp, height = verticalMargin.dp)
            .offset(x = horizontalMargin.dp, y = verticalMargin.dp)

    ) {
        Text("100")
    }
    Button(onClick = { changeScreen(0, 0) }) {
        Text("200")
    }
}


@Composable
fun ButtonGrid5x6(
    modifier: Modifier = Modifier.fillMaxSize(),
    rows: Int = 6,
    cols: Int = 5,
    offsetX: Dp = 225.dp,
    offsetY: Dp = 0.dp,
    buttonWidth: Dp? = null,
    buttonHeight: Dp? = null,
    onClick: (index: Int, row: Int, col: Int) -> Unit = { _, _, _ -> }
) {
    Column(
        modifier = modifier.padding(0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        for (r in 0 until rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                for (c in 0 until cols) {
                    val baseModifier = if (buttonWidth != null && buttonHeight != null) {
                        Modifier.size(buttonWidth, buttonHeight)
                    } else {
                        Modifier.weight(1f)
                    }

                    CompositionLocalProvider() {
                        Button(
                            onClick = { onClick(r * cols + c, r, c) },
                            modifier = baseModifier
                                .offset(x = offsetX, y = -offsetY),
                            interactionSource = remember { MutableInteractionSource() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
                        ) {
                            Text("${100 * (c + 1)}")
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun QuestionScreen() {
    MaterialTheme {
        val darkBlue = Color(0xFF3129D6)   // тёмно-синий
        val lightBlue = Color(0xFF82B1FF)  // светло-синий
        val darkGray = Color(0xFF2E2E2E)   // тёмно-серый
        val lightGray = Color(0xFF595959)  // светло-серый

        Column(
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

                }
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonGrid5x6(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 225.dp),
                rows = 6,
                cols = 5,
                offsetX = (1200 * 3 / 8).dp - 9.dp,
                buttonWidth = (1200 / 8).dp - 1.dp,
                buttonHeight = (900 / 6).dp + 1.dp,
                onClick = { index, row, col ->
                    changeScreen(row, col)
                }
            )
        }
    }
}