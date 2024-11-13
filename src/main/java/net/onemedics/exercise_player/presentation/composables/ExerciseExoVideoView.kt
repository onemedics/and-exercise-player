package net.onemedics.exercise_player.presentation.composables

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import net.onemedics.exercise_player.data.cache.ExoplayerCacheSingleton
import net.onemedics.exercise_player.presentation.events.ExoPlayerEvents
import net.onemedics.exercise_player.presentation.extensions.setMediaType
import net.onemedics.exercise_player.presentation.ui.ExoResizeMode

@OptIn(UnstableApi::class)
@Composable
fun <T> ExerciseExoVideoView(
    modifier: Modifier = Modifier,
    url: T,
    isPlaying: Boolean = false,
    videoResizeMode: ExoResizeMode = ExoResizeMode.Fit,
    showControls: Boolean = true,
    isLooping: Boolean = false,
    onExoPlayerEvents: ExoPlayerEvents? = null
    ) {

    val context = LocalContext.current
    val exoResizeMode = ExoResizeMode.from(resizeMode = videoResizeMode)




    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            this.setMediaType(context, url)
            repeatMode = if (isLooping) ExoPlayer.REPEAT_MODE_ONE else ExoPlayer.REPEAT_MODE_OFF
            prepare()
        }
    }

    when (isPlaying) {
        true -> {
            onExoPlayerEvents?.onPlayStart?.invoke()
            exoPlayer.play()
        }

        false -> {
            exoPlayer.pause()
        }
    }

    DisposableEffect(key1 = exoPlayer, key2 = isLooping) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_ENDED -> {
                        onExoPlayerEvents?.onPlayEnd?.invoke()
                    }
                    Player.STATE_READY -> {}
                }
            }
        })

        onDispose {
            ExoplayerCacheSingleton.release()
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
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