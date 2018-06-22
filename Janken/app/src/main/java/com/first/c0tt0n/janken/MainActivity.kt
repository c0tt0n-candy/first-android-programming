package com.first.c0tt0n.janken

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.choki
import kotlinx.android.synthetic.main.activity_main.gu
import kotlinx.android.synthetic.main.activity_main.pa

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    gu.setOnClickListener { onJankenButtonTapped(it) }
    choki.setOnClickListener { onJankenButtonTapped(it) }
    pa.setOnClickListener { onJankenButtonTapped(it) }
  }

  fun onJankenButtonTapped(view: View?) {
    // TODO: start ResultActivity
  }
}
