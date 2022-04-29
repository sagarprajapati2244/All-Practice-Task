package com.example.pictureinpicturemode

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Rational
import android.view.View
import android.widget.MediaController
import android.widget.Toolbar
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.pictureinpicturemode.databinding.ActivityPipBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

@RequiresApi(api = Build.VERSION_CODES.O)
class PipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPipBinding
    var videoView:VideoView? = null
    var pictureInPicker = PictureInPictureParams.Builder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pip)
        videoView = findViewById(R.id.videoView)
        setSupportActionBar(binding.toolbar)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        val videoUrl = intent.getStringExtra("video")
        videoView!!.setMediaController(mediaController)
        videoView!!.setVideoURI(Uri.parse(videoUrl))
        videoView!!.requestFocus()
        videoView!!.start()
        binding.fab.setOnClickListener {
                startPictureInPictureMode()
        videoView!!.setOnPreparedListener {
            it.setOnVideoSizeChangedListener { mediaPlayer, i, i2 ->
                videoView!!.setMediaController(mediaController)
                mediaController.setAnchorView(videoView)
            }
        }
        }

    }

    private fun startPictureInPictureMode() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            val aspectRatio = Rational(videoView!!.width,videoView!!.height)
            pictureInPicker.setAspectRatio(aspectRatio).build()
            enterPictureInPictureMode(pictureInPicker.build())
        }

    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (!isInPictureInPictureMode)
        {
            val aspectRation = Rational(videoView!!.width,videoView!!.height)
            pictureInPicker.setAspectRatio(aspectRation).build()
            enterPictureInPictureMode(pictureInPicker.build())
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode)
        {
            binding.fab.visibility = View.GONE
            binding.toolbar.visibility = View.GONE
        }
        else
        {
            binding.fab.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        updateView(intent)
    }

    private fun updateView(intent: Intent?) {
        val videoUrl = intent?.getStringExtra("video")
        videoView!!.setVideoURI(Uri.parse(videoUrl))
        videoView!!.requestFocus()
    }
}