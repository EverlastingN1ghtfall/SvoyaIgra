package org.example.svoyaigra

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlin.math.max


//fun App() {
//    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
//    }
//}

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
            // Контент
        }
    }
}