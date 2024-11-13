package net.onemedics.exercise_player.presentation.state

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout


@OptIn(UnstableApi::class)
sealed class ExoResizeMode(val type: Int) {
    object Fit:  ExoResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)
    object FixedWidth: ExoResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH)
    object FixedHeight: ExoResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT)
    object Fill: ExoResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
    object Zoom: ExoResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)

    companion object {
        fun from(resizeMode: ExoResizeMode): Int {
            return resizeMode.type
        }
    }
}