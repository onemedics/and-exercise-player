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
import androidx.media3.exoplayer.source.ClippingMediaSource
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import nah.prayer.library.Nlog
import net.onemedics.exercise_player.data.cache.ExoplayerCacheSingleton
import net.onemedics.exercise_player.data.datasource.createCachedMediaSource
import net.onemedics.exercise_player.data.network.createCloudFrontHeaders

@OptIn(UnstableApi::class)
internal fun<T> ExoPlayer.setMediaType(context: Context, url: T) {
    when (url) {
        is String -> {
            if (url.isHlsUrl()) {
                val httpDataSourceFactory = DefaultHttpDataSource.Factory().apply {
                    setDefaultRequestProperties(createCloudFrontHeaders())
                }
                val simpleCache = ExoplayerCacheSingleton.getInstance(context)

                val dataSourceFactory =  CacheDataSource.Factory()
                    .setCache(simpleCache)
                    .setUpstreamDataSourceFactory(httpDataSourceFactory)

                val mediaItem = MediaItem.fromUri(url)

                val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

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

@OptIn(UnstableApi::class)
internal inline fun<reified T> ExoPlayer.setMediaTypeList(context: Context, url: List<T>) {
    when (T::class) {
        String::class -> {
            val concatenatingMediaSource = ConcatenatingMediaSource()

            url.forEach { mediaUrl ->
                mediaUrl as String

                if (mediaUrl.isHlsUrl()) {
                    val httpDataSourceFactory = DefaultHttpDataSource.Factory().apply {
                        setDefaultRequestProperties(createCloudFrontHeaders())
                    }
                    val simpleCache = ExoplayerCacheSingleton.getInstance(context)

                    val dataSourceFactory =  CacheDataSource.Factory()
                        .setCache(simpleCache)
                        .setUpstreamDataSourceFactory(httpDataSourceFactory)

                    val mediaItem = MediaItem.fromUri(mediaUrl)

                    val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

                    concatenatingMediaSource.addMediaSource(hlsMediaSource)
                    setMediaSource(concatenatingMediaSource)
                } else {
                    val mediaSource = createCachedMediaSource(context, mediaUrl)
                    concatenatingMediaSource.addMediaSource(mediaSource)
                    setMediaSource(mediaSource)
                }
            }
        }

        Int::class -> {
            val mediaItems = url.map { mediaUrl ->
                mediaUrl as Int
                MediaItem.fromUri("android.resource://${context.packageName}/$mediaUrl")
            }
            setMediaItems(mediaItems)
        }

        Uri::class -> {
            val mediaItems = url.map { mediaUrl ->
                mediaUrl as Uri
                MediaItem.fromUri(mediaUrl)
            }
            setMediaItems(mediaItems)
        }
    }
}

//ignoreCase 대문자 소문자를 무시하고 문자열 비교
fun String.isHlsUrl(): Boolean {
    val uri = Uri.parse(this)
    val path = uri.lastPathSegment
    return path?.endsWith(".m3u8", ignoreCase = true) == true
}
