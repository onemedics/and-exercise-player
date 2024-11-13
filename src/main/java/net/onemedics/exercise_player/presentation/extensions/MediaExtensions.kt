package net.onemedics.exercise_player.presentation.extensions

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import net.onemedics.exercise_player.data.cache.ExoplayerCacheSingleton
import net.onemedics.exercise_player.data.datasource.createCachedMediaSource
import net.onemedics.exercise_player.data.network.createCloudFrontHeaders

@OptIn(UnstableApi::class)
fun<T> ExoPlayer.setMediaType(context: Context, url: T) {
    when (url) {
        is String -> {
            if (url.contains(".m3u8")) {
                val httpDataSourceFactory = DefaultHttpDataSource.Factory().apply {
                    setDefaultRequestProperties(createCloudFrontHeaders())
                }
                val simpleCache = ExoplayerCacheSingleton.getInstance(context)

                val dataSourceFactory =  CacheDataSource.Factory()
                    .setCache(simpleCache)
                    .setUpstreamDataSourceFactory(httpDataSourceFactory)

                val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(url))
                setMediaSource(hlsMediaSource)
            } else {
                val mediaSource = createCachedMediaSource(context, url)
                setMediaSource(mediaSource)
            }
        }

        is Int -> {
            setMediaItem(MediaItem.fromUri("android.resource://${context.packageName}/$url"))
        }

        is Uri -> {
            setMediaItem(MediaItem.fromUri(url))
        }
    }
}
