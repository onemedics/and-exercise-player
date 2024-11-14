package net.onemedics.exercise_player.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MeilExerciseCountView(count: Int, sec: Int, exerciseCount: Int, exerciseTotalCount: Int) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text("${sec}초/${count}회")
        Spacer(Modifier.weight(1f))
        Text("${exerciseCount}/${exerciseTotalCount}")
    }
}