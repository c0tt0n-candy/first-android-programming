package com.first.c0tt0n.myscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_schedule_edit.dateEdit
import kotlinx.android.synthetic.main.activity_schedule_edit.delete
import kotlinx.android.synthetic.main.activity_schedule_edit.detailEdit
import kotlinx.android.synthetic.main.activity_schedule_edit.save
import kotlinx.android.synthetic.main.activity_schedule_edit.titleEdit
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class ScheduleEditActivity : AppCompatActivity() {

  private lateinit var realm: Realm

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_schedule_edit)
    realm = Realm.getDefaultInstance()

    val scheduleId = intent?.getLongExtra("schedule_id", -1L)
    if (scheduleId != -1L) {
      val schedule = realm.where<Schedule>().equalTo("id", scheduleId).findFirst()
      dateEdit.setText(DateFormat.format("yyyy/MM/dd", schedule?.date))
      titleEdit.setText(schedule?.title)
      detailEdit.setText(schedule?.detail)
      delete.visibility = View.VISIBLE
    } else {
      delete.visibility = View.INVISIBLE
    }

    save.setOnClickListener {
      when (scheduleId) {
        -1L -> {
          realm.executeTransaction {
            val maxId = realm.where<Schedule>().max("id")
            val nextId = (maxId?.toLong() ?: 0) + 1
            val schedule = realm.createObject<Schedule>(nextId)
            dateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
              schedule.date = it
            }
            schedule.title = titleEdit.text.toString()
            schedule.detail = detailEdit.text.toString()
          }

          alert("追加しました") {
            yesButton { finish() }
          }.show()
        }
        else -> {
          realm.executeTransaction {
            val schedule = realm.where<Schedule>().equalTo("id", scheduleId).findFirst()
            dateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
              schedule?.date = it
            }
            schedule?.run {
              title = titleEdit.text.toString()
              detail = detailEdit.text.toString()
            }
          }

          alert("修正しました") {
            yesButton { finish() }
          }.show()
        }
      }
    }

    delete.setOnClickListener {
      realm.executeTransaction {
        realm.where<Schedule>().equalTo("id", scheduleId)?.findFirst()?.deleteFromRealm()
      }

      alert("削除しました") {
        yesButton { finish() }
      }.show()
    }
  }


  private fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm"): Date? {
    val sdFormat = try {
      SimpleDateFormat(pattern)
    } catch (e: IllegalArgumentException) {
      null
    }
    return sdFormat?.let {
      try {
        it.parse(this)
      } catch (e: ParseException) {
        null
      }
    }
  }
}
