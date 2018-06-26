package com.first.c0tt0n.myslideshow

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.imageSwitcher
import kotlinx.android.synthetic.main.activity_main.nextButton
import kotlinx.android.synthetic.main.activity_main.prevButton
import kotlinx.android.synthetic.main.activity_main.slideshowButton
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

  private val images = listOf(
      R.drawable.slide00, R.drawable.slide01,
      R.drawable.slide02, R.drawable.slide03,
      R.drawable.slide04, R.drawable.slide05,
      R.drawable.slide06, R.drawable.slide07,
      R.drawable.slide08, R.drawable.slide09
  )
  private var position = 0
  private var isSlideshow = false
  private val handler = Handler()

  private lateinit var player: MediaPlayer

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    imageSwitcher.apply {
      setFactory {
        ImageView(this@MainActivity)
      }
      setImageResource(images[0])
    }

    prevButton.setOnClickListener {
      imageSwitcher.apply {
        setInAnimation(this@MainActivity, android.R.anim.fade_in)
        setOutAnimation(this@MainActivity, android.R.anim.fade_out)
      }
      movePosition(-1)
    }

    nextButton.setOnClickListener {
      imageSwitcher.apply {
        setInAnimation(this@MainActivity, android.R.anim.slide_in_left)
        setOutAnimation(this@MainActivity, android.R.anim.slide_out_right)
      }
      movePosition(1)
    }

    slideshowButton.setOnClickListener {
      isSlideshow = !isSlideshow

      when (isSlideshow) {
        true -> player.start()
        false -> player.apply {
          pause()
          seekTo(0)
        }
      }
    }

    player = MediaPlayer.create(this, R.raw.getdown).apply {
      isLooping = true
    }

    timer(period = 5000) {
      handler.post {
        if (isSlideshow) movePosition(1)
      }
    }
  }

  private fun movePosition(move: Int) {
    position = when {
      position + move >= images.size -> 0
      position + move < 0 -> images.size - 1
      else -> position + move
    }
    imageSwitcher.setImageResource(images[position])
  }
}
