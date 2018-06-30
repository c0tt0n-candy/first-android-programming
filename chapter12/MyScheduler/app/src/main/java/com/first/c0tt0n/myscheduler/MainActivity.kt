package com.first.c0tt0n.myscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.listView
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

  private lateinit var realm: Realm

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    realm = Realm.getDefaultInstance()
    val schedules = realm.where<Schedule>().findAll()

    listView.apply {
      adapter = ScheduleAdapter(schedules)
      setOnItemClickListener { parent, _, position, _ ->
        val schedule = parent.getItemAtPosition(position) as Schedule
        startActivity<ScheduleEditActivity>("schedule_id" to schedule.id)
      }
    }

    fab.setOnClickListener { _ ->
      startActivity<ScheduleEditActivity>()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    realm.close()
  }
}
