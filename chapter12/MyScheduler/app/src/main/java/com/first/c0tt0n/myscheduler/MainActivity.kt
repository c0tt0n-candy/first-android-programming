package com.first.c0tt0n.myscheduler

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.listView

class MainActivity : AppCompatActivity() {

  private lateinit var realm: Realm

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    realm = Realm.getDefaultInstance()
    val schedules = realm.where<Schedule>().findAll()
    listView.adapter = ScheduleAdapter(schedules)

    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    realm.close()
  }
}
