package com.first.c0tt0n.myalarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.cancelAlarm
import kotlinx.android.synthetic.main.activity_main.setAlarm
import java.util.Calendar

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setAlarm.setOnClickListener {
      val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        add(Calendar.SECOND, 5)
      }
      setAlarmManager(calendar)
    }

    cancelAlarm.setOnClickListener {
      cancelAlarmManager()
    }
  }

  private fun setAlarmManager(calendar: Calendar) {
    val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val pending = PendingIntent.getBroadcast(
        this,
        0,
        Intent(this, AlarmBroadcastReceiver::class.java),
        0
    )

    when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
        val info = AlarmManager.AlarmClockInfo(calendar.timeInMillis, null)
        am.setAlarmClock(info, pending)
      }
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
        am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending)
      }
      else -> am.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending)
    }
  }

  private fun cancelAlarmManager() {
    val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val pending = PendingIntent.getBroadcast(
        this,
        0,
        Intent(this, AlarmBroadcastReceiver::class.java),
        0
    )
    am.cancel(pending)
  }
}
