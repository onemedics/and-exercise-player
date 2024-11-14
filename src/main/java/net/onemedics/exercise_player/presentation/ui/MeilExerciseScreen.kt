package net.onemedics.exercise_player.presentation.ui


import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import net.onemedics.exercise_player.presentation.composables.ExerciseBottomSheetScaffoldView
import net.onemedics.exercise_player.presentation.composables.ExerciseExoVideoView
import net.onemedics.exercise_player.presentation.viewmodel.MeilExerciseViewModel

/**
 * 운동 화면 (Exercise Screen)
 *
 * 이 화면은 사용자가 운동 영상을 재생하고, 운동 진행 상황을 확인할 수 있도록 제공합니다.
 *
 * 주요 기능:
 * - ExoPlayer를 이용한 운동 영상 재생
 * - 운동의 시작/종료 시간 클리핑 기능
 *
 * 특이 사항:
 * - ExoPlayer를 사용하여 동영상 재생과 클리핑 기능을 처리합니다.
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MeilExerciseScreen(
//    exerciseJson: String,
    viewModel: MeilExerciseViewModel = hiltViewModel()
) {

    ExerciseBottomSheetScaffoldView(
        topView = {
            ExerciseExoVideoView(modifier = Modifier,"https://hls-test.onemedics.net/264_18/1_80BPM_sound_on_18.m3u8", isPlaying = true, isLooping = false)
        },
        bottomView = {

        }
    )

//    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        ExerciseExoVideoView(modifier = Modifier,"https://hls-test.onemedics.net/264_18/1_80BPM_sound_on_18.m3u8", isPlaying = true, isLooping = false)
//
//        //프로그레스 인디케이터
//        ArcifyCircleIndicator(
//            modifier = Modifier.size(50.dp),
//            progressState = ArcifyProgressState.Auto(),
//            color = MaterialTheme.colorScheme.primary,
//            backgroundColor = Color.Gray.copy(alpha = 0.2f),
//            strokeCap = StrokeCap.Butt,
//            animationSpec = tween(
//                durationMillis = 2500,
//                easing = LinearEasing
//            )
//        )
//
//        ExerciseControlView {
//
//        }
//    }
}

