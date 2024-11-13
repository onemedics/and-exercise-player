package net.onemedics.exercise_player.data.datasource

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import net.onemedics.exercise_player.data.cache.ExoplayerCacheSingleton

@OptIn(UnstableApi::class)
fun createCachedMediaSource(context: Context, uri: String): ProgressiveMediaSource {
    val simpleCache = ExoplayerCacheSingleton.getInstance(context)

    val httpDataSourceFactory = DefaultHttpDataSource.Factory()
        .setUserAgent(context.applicationInfo.name)

    val dataSourceFactory = DefaultDataSource.Factory(context, httpDataSourceFactory)

    val cacheDataSourceFactory = CacheDataSource.Factory()
        .setCache(simpleCache)
        .setUpstreamDataSourceFactory(dataSourceFactory)
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

    return ProgressiveMediaSource.Factory(cacheDataSourceFactory)
        .createMediaSource(MediaItem.fromUri(uri))
}