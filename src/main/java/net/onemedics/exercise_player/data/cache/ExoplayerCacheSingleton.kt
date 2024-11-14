package net.onemedics.exercise_player.data.cache

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File


@UnstableApi
internal object ExoplayerCacheSingleton {
    private var simpleCache: SimpleCache? = null

    fun getInstance(context: Context): SimpleCache {
        if (simpleCache == null) {
            val cacheDir = File(context.cacheDir, "meli_exercise")
            val cacheSize: Long = 100 * 1024 * 1024 // 100MB 캐시
            val cacheEvictor = LeastRecentlyUsedCacheEvictor(cacheSize)
            val databaseProvider = CustomDatabaseProvider(context)
            simpleCache = SimpleCache(cacheDir, cacheEvictor, databaseProvider)
        }
        return simpleCache!!
    }

    fun release() {
        simpleCache?.release()
        simpleCache = null
    }
}