package net.onemedics.exercise_player.presentation.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp


@Composable
fun OscillatingCircleAnimation(
    color: Color,
    size: Dp,
    startSize: Float,
    endSize: Float,
    duration: Int
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val circleSize by infiniteTransition.animateFloat(
        initialValue = startSize,
        targetValue = endSize,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse // 역방향으로 재생
        ),
        label = ""
    )

    Canvas(modifier = Modifier.size(size)) {
        drawCircle(
            color = color,
            radius = circleSize
        )
    }
}