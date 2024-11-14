package net.onemedics.exercise_player.presentation.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/**
 * 	1.	상태 관리:
 * 	•	expanded: 상단 뷰가 최대 크기를 차지하는지 여부를 나타내는 상태.
 * 	•	dragOffset: 드래그로 인해 변경된 높이를 추적하는 상태.
 *
 * 		2.	뷰 크기 계산:
 * 	•	topViewHeight: 상단 뷰의 높이를 결정합니다.
 * 	•	expanded가 true일 때 최대 높이(maxTopViewHeight)를 사용.
 * 	•	expanded가 false일 때 드래그 오프셋에 따라 줄어든 높이를 계산하며, 최소 높이(minTopViewHeight) 이상이어야 합니다.
 * 	•	bottomViewHeight: 하단 뷰의 높이를 결정합니다.
 * 	•	expanded가 true일 때 최소 높이(minBottomViewHeight)를 사용.
 * 	•	expanded가 false일 때 드래그 오프셋에 따라 확장된 높이를 계산하며, 최대 높이(maxBottomViewHeight) 이하로 제한됩니다.
 */


@Composable
fun ExerciseBottomSheetScaffoldView(
    topView: @Composable () -> Unit,
    bottomView: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(true) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val bottomViewPeekHeight = 40.dp // 하단 뷰가 항상 보이는 높이
    val maxPeekHeight = 250.dp // 하단 뷰 최대 피크 높이

    val minTopViewHeight = 100.dp
    val maxTopViewHeight = screenHeight - bottomViewPeekHeight // 하단 뷰가 조금 보이도록 설정
    val minBottomViewHeight = bottomViewPeekHeight
    val maxBottomViewHeight = maxPeekHeight

    var dragOffset by remember { mutableStateOf(0f) }

    val topViewHeight by animateDpAsState(
        targetValue = if (expanded) maxTopViewHeight else (maxTopViewHeight - dragOffset.dp).coerceAtLeast(
            minTopViewHeight
        )
    )

    val bottomViewHeight by animateDpAsState(
        targetValue = if (expanded) minBottomViewHeight else (minBottomViewHeight + dragOffset.dp).coerceIn(
            minBottomViewHeight,
            maxBottomViewHeight
        )
    )

    Column(
        modifier = Modifier.fillMaxSize().systemBarsPadding()
    ) {
        // 상단 뷰
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topViewHeight)
                .background(Color.Blue)
        ) {
            topView()
        }

        // 하단 뷰
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomViewHeight)
                .background(Color.Red)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        dragOffset = (dragOffset - delta).coerceIn(
                            0f,
                            (maxPeekHeight.value - bottomViewPeekHeight.value)
                        )
                    },
                    onDragStarted = { expanded = false },
                    onDragStopped = {
                        val midPoint = (maxBottomViewHeight - minBottomViewHeight).value / 2
                        if (dragOffset < midPoint) {
                            expanded = true
                            dragOffset = 0f
                        } else {
                            expanded = false
                            dragOffset = (maxBottomViewHeight - minBottomViewHeight).value
                        }
                    }
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .width(40.dp) // 바의 폭
                        .height(4.dp) // 바의 높이
                        .background(Color.Gray, shape = RoundedCornerShape(2.dp))
                        .padding(top = 8.dp) // 바 위치를 상단에서 약간 아래로 이동
                )

                bottomView()
            }
        }
    }
}