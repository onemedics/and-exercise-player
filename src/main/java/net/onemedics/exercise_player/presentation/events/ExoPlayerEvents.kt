package net.onemedics.exercise_player.presentation.events

data class ExoPlayerEvents(
    val onPlayStart: (() -> Unit)? = null,
    val onPlayEvent: ((Float) -> Unit)? = null,
    val onPlayEnd: (() -> Unit)? = null
)