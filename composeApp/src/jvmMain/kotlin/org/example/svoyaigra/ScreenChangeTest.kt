package org.example.svoyaigra

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.platform.SystemFont
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import kotlin.math.max

@Composable
fun MainScreen() {
    var showQuestion by remember { mutableStateOf(false) }

    MaterialTheme {
        SimpleBackgroundScreen()
        if (showQuestion) {
            // Экран с вопросом и кнопкой "Назад"
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = { showQuestion = false },
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .width(200.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Назад", fontSize = 24.sp)
                }
            }
        } else {
            val topics = listOf("Тема 1", "Тема 2", "Тема 3", "Тема 4", "Тема 5", "Тема 6")
            ButtonGridWithLabels(
                topics = topics,
                onClick = { _, _ -> showQuestion = true }
            )
        }
    }
}

