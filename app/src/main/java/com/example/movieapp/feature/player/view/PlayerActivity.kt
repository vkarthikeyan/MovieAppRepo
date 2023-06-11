package com.example.movieapp.feature.player.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityFullscreenPlayerBinding
import androidx.media3.common.MediaItem as MovieAppMediaItem

/**
 * A fullscreen activity to play audio or video streams.
 */
class PlayerActivity : AppCompatActivity() {

    private lateinit var TAG: String
    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityFullscreenPlayerBinding.inflate(layoutInflater)
    }
    private var player: ExoPlayer? = null

    private var isPlayerReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L
    private val playbackStateListener: Player.Listener = playbackStateListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        TAG = PlayerActivity::class.java.name
        supportActionBar?.hide()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            viewBinding.videoView.player = exoPlayer
            val mediaItem = MovieAppMediaItem.Builder().setUri(getString(R.string.media_url_dash))
                .setMimeType(MimeTypes.APPLICATION_MPD)
                .build()

            exoPlayer.apply {
                setMediaItems(listOf(mediaItem), mediaItemIndex, playbackPosition)
                playWhenReady = isPlayerReady
                addListener(playbackStateListener)
                prepare()
            }

        }
    }
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) releasePlayer()
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            exoPlayer.apply {
                playbackPosition = exoPlayer.currentPosition
                mediaItemIndex = exoPlayer.currentMediaItemIndex
                playWhenReady = exoPlayer.playWhenReady
                exoPlayer.removeListener(playbackStateListener)
                exoPlayer.release()
            }
        }
        player = null
    }

    private fun playbackStateListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }

            Log.d(TAG, "changed state to $stateString")
        }
    }
}