package net.onemedics.exercise_player.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import net.onemedics.exercise_player.data.ExerciseData
import net.onemedics.exercise_player.presentation.composables.ExerciseExoVideoView
import net.onemedics.exercise_player.presentation.extensions.fromJson
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

@Composable
fun MeilExerciseScreen(
//    exerciseJson: String,
    viewModel: MeilExerciseViewModel = hiltViewModel()
) {
//    val mediaList = exerciseJson.fromJson<List<ExerciseData>>()

    ExerciseExoVideoView(modifier = Modifier,"https://hls-test.onemedics.net/264_18/1_80BPM_sound_on_18.m3u8", isPlaying = true, isLooping = true)
}