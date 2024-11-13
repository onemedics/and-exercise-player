package net.onemedics.exercise_player.data

data class ExerciseData(
    val id: String,
    val exerciseCode: String,
    val exerciseName: String,
    val url: String,
    val thumbnail: String,
    val sec: Int,
    val exerciseReps: Int,
    val exerciseTimeInSeconds: Int,
    val exerciseEquipment: String
)
