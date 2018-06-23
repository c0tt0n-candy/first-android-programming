package com.first.c0tt0n.mysize

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.heightButton
import kotlinx.android.synthetic.main.activity_main.inseam
import kotlinx.android.synthetic.main.activity_main.neck
import kotlinx.android.synthetic.main.activity_main.save
import kotlinx.android.synthetic.main.activity_main.sleeve
import kotlinx.android.synthetic.main.activity_main.waist

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    PreferenceManager.getDefaultSharedPreferences(this).apply {
      neck.setText(getString("NECK", ""))
      sleeve.setText(getString("SLEEVE", ""))
      waist.setText(getString("WAIST", ""))
      inseam.setText(getString("INSEAM", ""))
    }

    save.setOnClickListener { onSaveTapped() }

    heightButton.setOnClickListener {
      startActivity(Intent(this@MainActivity, HeightActivity::class.java))
    }
  }

  private fun onSaveTapped() {
    PreferenceManager.getDefaultSharedPreferences(this).apply {
      edit().putString("NECK", neck.text.toString())
          .putString("SLEEVE", sleeve.text.toString())
          .putString("WAIST", waist.text.toString())
          .putString("INSEAM", inseam.text.toString())
          .apply()
    }
  }
}
