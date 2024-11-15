package net.onemedics.exercise_player.presentation.composables

import android.os.Handler
import android.os.Looper
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import net.onemedics.exercise_player.data.cache.ExoplayerCacheSingleton
import net.onemedics.exercise_player.presentation.events.ExoPlayerEvents
import net.onemedics.exercise_player.presentation.extensions.setMediaType
import net.onemedics.exercise_player.presentation.extensions.setMediaTypeList
import net.onemedics.exercise_player.presentation.state.ExoResizeMode

@OptIn(UnstableApi::class)
@Composable
internal fun <T> ExerciseExoVideoView(
    modifier: Modifier = Modifier,
    url: T,
    isPlaying: Boolean = false,
    videoResizeMode: ExoResizeMode = ExoResizeMode.Fit,
    showControls: Boolean = false,
    onExoPlayerEvents: ExoPlayerEvents? = null
) {

    val context = LocalContext.current
    val exoResizeMode = ExoResizeMode.from(resizeMode = videoResizeMode)

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            this.setMediaType(context, url)
            prepare()
        }
    }


//    val handler = Handler(Looper.getMainLooper())
//    val updateProgressAction = object : Runnable {
//        override fun run() {
//            val currentPosition = exoPlayer.currentPosition
//            val duration = exoPlayer.duration
//
//            if (duration != C.TIME_UNSET) {
//                val currentSeconds = currentPosition / 1000
//                val totalSeconds = duration / 1000
//                //todo 종료 시간은 영상마다 바뀔 수 있음
//                if (currentSeconds >= 60) {
//                    exoPlayer.seekTo(3_000L)
//                }
//            }
//
//            handler.postDelayed(this, 1000) // 1초마다 업데이트
//        }
//    }

    when (isPlaying) {
        true -> {
            onExoPlayerEvents?.onPlayStart?.invoke()
            exoPlayer.play()
        }

        false -> {
            exoPlayer.pause()
        }
    }

    DisposableEffect(key1 = exoPlayer) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_ENDED -> onExoPlayerEvents?.onPlayEnd?.invoke()
                    Player.STATE_READY -> {}
                }
            }
        })

        onDispose {
//            handler.removeCallbacks(updateProgressAction)
            ExoplayerCacheSingleton.release()
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                this.resizeMode = exoResizeMode
                if (showControls) hideController()
                useController = showControls

            }
        }
    )
}

@OptIn(UnstableApi::class)
@Composable
internal inline fun <reified T> ExerciseExoVideoListView(
    modifier: Modifier = Modifier,
    url: List<T>,
    isPlaying: Boolean = false,
    videoResizeMode: ExoResizeMode = ExoResizeMode.Fit,
    showControls: Boolean = false,
    onExoPlayerEvents: ExoPlayerEvents? = null
) {
    val context = LocalContext.current
    val exoResizeMode = ExoResizeMode.from(resizeMode = videoResizeMode)

    var currentIndex by remember { mutableIntStateOf(0) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            this.setMediaTypeList(context, url)
            prepare()
        }
    }

    val handler = Handler(Looper.getMainLooper())
    val updateProgressAction = object : Runnable {
        override fun run() {
            val currentPosition = exoPlayer.currentPosition
            val duration = exoPlayer.duration

            if (duration != C.TIME_UNSET && currentIndex <= url.lastIndex - 1) {
                val currentSeconds = currentPosition / 1000
                val totalSeconds = duration / 1000
                //todo 종료 시간은 영상마다 바뀔 수 있음
                if (currentSeconds >= 60) {
                    exoPlayer.seekTo(3_000L)
                }
            }

            handler.postDelayed(this, 1000) // 1초마다 업데이트
        }
    }

    when (isPlaying) {
        true -> {
            onExoPlayerEvents?.onPlayStart?.invoke()
            if (currentIndex >= 1)
                exoPlayer.seekTo(3_000L)  // 시작 시간으로 이동 (밀리초 단위)
            exoPlayer.play()
        }

        false -> {
            exoPlayer.pause()
        }
    }

    DisposableEffect(key1 = exoPlayer) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (currentIndex >= 1) {
                    if (playbackState == Player.STATE_READY) handler.post(updateProgressAction)
                    else handler.removeCallbacks(updateProgressAction) // 중단 시 업데이트 제거
                }

                when (playbackState) {
                    Player.STATE_ENDED -> onExoPlayerEvents?.onPlayEnd?.invoke()
                    Player.STATE_READY -> {}
                }
            }
        })

        exoPlayer.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                currentIndex = exoPlayer.currentMediaItemIndex
            }
        })

        onDispose {
            handler.removeCallbacks(updateProgressAction)
            ExoplayerCacheSingleton.release()
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                this.resizeMode = exoResizeMode
                if (showControls) hideController()
                useController = showControls

            }
        }
    )
}
