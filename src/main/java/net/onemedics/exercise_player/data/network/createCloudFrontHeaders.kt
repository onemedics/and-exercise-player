package net.onemedics.exercise_player.data.network

internal fun createCloudFrontHeaders(): Map<String, String> {
    val policyValue = "eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cHM6Ly9obHMtdGVzdC5vbmVtZWRpY3MubmV0LyoiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE3MzMwMTA4NDB9fX1dfQ__"
    val signatureValue = "gs9N4hP4DDaKQnFPQdI0rOBrFljwpNWL6YxI6QqNovoQ3iQUhZXQY1rSUqqhR3uYlHNr2wVa7ZeAqOs-brQykTCZy6d3T2wW5Xl7cVqRXnCcpvDiLw3jMa5QJnDj9kUM0zUEwJOETuq3PZRHaosaa2RpERjJhUjyAd4Is8bLowMhwMlRUfXKxcfHLLF9h~9mqJCapu0SZd0qs4Tvbxcx8Vc1mLParAs2icPKn19vfjvqq~7YwH8Ox8tirDwdr6NrmmpBK7QBFn1ZwcKpuuhFQUizku0D~j1EW2qRSoSBqz3iZOXtIA3Bx9DLiApDBlFc0gQfXvPpugLww3W44lGwbg__"
    val keyPairValue = "KP5CUWTT42Y4L"

    return mapOf(
        "Cookie" to "CloudFront-Policy=${policyValue}; CloudFront-Signature=${signatureValue}; CloudFront-Key-Pair-Id=${keyPairValue}"
    )
}