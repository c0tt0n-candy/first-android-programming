package com.first.c0tt0n.mysize

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_height.height
import kotlinx.android.synthetic.main.activity_height.radioGroup
import kotlinx.android.synthetic.main.activity_height.seekBar
import kotlinx.android.synthetic.main.activity_height.spinner

class HeightActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_height)

    PreferenceManager.getDefaultSharedPreferences(this).apply {
      val heightVal = getInt("HEIGHT", 160)
      height.text = heightVal.toString()
      seekBar.progress = heightVal
    }

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent is Spinner) {
          val item = parent.selectedItem.toString()
          if (item.isNotEmpty()) height.text = item
        }
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
        // need not implement
      }
    }

    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        height.text = progress.toString()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // need not implement
      }

      override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // need not implement
      }
    })

    radioGroup.setOnCheckedChangeListener { _, checkedId ->
      height.text = findViewById<RadioButton>(checkedId).text
    }
  }

  override fun onPause() {
    super.onPause()
    PreferenceManager.getDefaultSharedPreferences(this).edit()
        .putInt("HEIGHT", height.text.toString().toInt())
        .apply()
  }
}
