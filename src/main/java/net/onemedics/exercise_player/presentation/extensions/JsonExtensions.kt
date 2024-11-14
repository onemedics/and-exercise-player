package net.onemedics.exercise_player.presentation.extensions

import com.google.gson.Gson

internal inline fun <reified T> String.fromJson(): T {
    val gson = Gson()
    return gson.fromJson(this, T::class.java)
}

internal fun Any.toJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}