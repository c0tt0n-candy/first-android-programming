package com.first.c0tt0n.myalarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class AlarmBroadcastReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context?, intent: Intent?) {
    context?.run {
      startActivity(intentFor<MainActivity>("onReceive" to true).newTask())
    }
  }
}