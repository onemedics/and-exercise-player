package net.onemedics.exercise_player.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Dialog

@Composable
fun BaseDialog(
    title: String,
    content: @Composable () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier, // 위치 조정을 위한 Modifier
    confirmText: String = "OK",
    dismissText: String = "Cancel"
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .offset { IntOffset(0, 200) } // 위치 설정: Y축 200dp 아래로 이동
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = title)
                    Spacer(modifier = Modifier.height(8.dp))

                    content()

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(text = dismissText)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = onConfirm) {
                            Text(text = confirmText)
                        }
                    }
                }
            }
        }
    }
}