package net.onemedics.exercise_player.presentation.extensions

import com.google.gson.Gson

inline fun <reified T> String.fromJson(): T {
    val gson = Gson()
    return gson.fromJson(this, T::class.java)
}

fun Any.toJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}