package com.first.c0tt0n.myalarmclock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
import android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
import android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
import kotlinx.android.synthetic.main.activity_main.cancelAlarm
import kotlinx.android.synthetic.main.activity_main.dateText
import kotlinx.android.synthetic.main.activity_main.setAlarm
import kotlinx.android.synthetic.main.activity_main.timeText
import org.jetbrains.anko.toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity(), SimpleAlertDialog.OnClickListener, DatePickerFragment.OnDateSelectedListener, TimePickerFragment.OnTimeSelectedListener {

  @Suppress("DEPRECATION")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (intent?.getBooleanExtra("onReceive", false) == true) {
      when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
          window.addFlags(FLAG_TURN_SCREEN_ON or FLAG_SHOW_WHEN_LOCKED)
        }
        else -> {
          window.addFlags(FLAG_TURN_SCREEN_ON or FLAG_SHOW_WHEN_LOCKED or FLAG_DISMISS_KEYGUARD)
        }
      }
      SimpleAlertDialog().show(supportFragmentManager, "alert_dialog")
    }

    setContentView(R.layout.activity_main)

    setAlarm.setOnClickListener {
      val date = "${dateText.text} ${timeText.text}".toDate()
      when {
        date != null -> {
          val calendar = Calendar.getInstance().apply {
            time = date
          }
          setAlarmManager(calendar)
          toast("アラームをセットしました")

        }
        else -> toast("日付の形式が正しくありません")
      }
    }

    cancelAlarm.setOnClickListener {
      cancelAlarmManager()
    }

    dateText.setOnClickListener {
      DatePickerFragment().show(supportFragmentManager, "date_dialog")
    }

    timeText.setOnClickListener {
      TimePickerFragment().show(supportFragmentManager, "time_dialog")
    }
  }

  // [SimpleAlertDialog.OnClickListener]
  override fun onPositiveClick() {
    finish()
  }

  override fun onNegativeClick() {
    val calendar = Calendar.getInstance().apply {
      timeInMillis = System.currentTimeMillis()
      add(Calendar.MINUTE, 5)
    }
    setAlarmManager(calendar)
    finish()
  }

  // [DatePickerFragment.OnDateSelectedListener]
  override fun onSelected(year: Int, month: Int, date: Int) {
    val calendar = Calendar.getInstance().apply {
      set(year, month, date)
    }
    dateText.text = DateFormat.format("yyyy/MM/dd", calendar)
  }

  // [TimePickerFragment.OnTimeSelectedListener]
  override fun onSelected(hourOfDay: Int, minute: Int) {
    timeText.text = "%1$02d:%2$02d".format(hourOfDay, minute)
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

  @SuppressLint("SimpleDateFormat")
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
