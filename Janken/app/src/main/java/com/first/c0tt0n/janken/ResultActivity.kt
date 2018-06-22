package com.first.c0tt0n.janken

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.backButton
import kotlinx.android.synthetic.main.activity_result.comHandImage
import kotlinx.android.synthetic.main.activity_result.myHandImage
import kotlinx.android.synthetic.main.activity_result.resultLabel

class ResultActivity : AppCompatActivity() {

  val gu = 0
  val choki = 1
  val pa = 2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_result)

    val id = intent.getIntExtra("MY_HAND", 0)

    val myHand = when (id) {
      R.id.gu -> {
        myHandImage.setImageResource(R.drawable.gu)
        gu
      }
      R.id.choki -> {
        myHandImage.setImageResource(R.drawable.choki)
        choki
      }
      R.id.pa -> {
        myHandImage.setImageResource(R.drawable.pa)
        pa
      }
      else -> gu
    }

    val comHand = getHand()
    when (comHand) {
      gu -> comHandImage.setImageResource(R.drawable.com_gu)
      choki -> comHandImage.setImageResource(R.drawable.com_choki)
      pa -> comHandImage.setImageResource(R.drawable.com_pa)
    }

    val gameResult = (comHand - myHand + 3) % 3
    when (gameResult) {
      0 -> resultLabel.setText(R.string.result_draw)
      1 -> resultLabel.setText(R.string.result_win)
      2 -> resultLabel.setText(R.string.result_lose)
    }

    backButton.setOnClickListener { finish() }
  }

  private fun getHand(): Int {
    return (Math.random() * 3).toInt()
  }
}
