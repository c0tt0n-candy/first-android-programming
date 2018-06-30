package com.first.c0tt0n.myscheduler

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class ScheduleAdapter(data: OrderedRealmCollection<Schedule>?) : RealmBaseAdapter<Schedule>(data) {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view: View
    val holder: ViewHolder

    when (convertView) {
      null -> {
        view = LayoutInflater.from(parent?.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        holder = ViewHolder(view)
        view.tag = holder
      }
      else -> {
        view = convertView
        holder = view.tag as ViewHolder
      }
    }

    adapterData?.run {
      val schedule = get(position)
      holder.apply {
        date.text = DateFormat.format("yyyy/MM/dd", schedule.date)
        title.text = schedule.title
      }
    }

    return view
  }

  inner class ViewHolder(cell: View) {
    val date = cell.findViewById<TextView>(android.R.id.text1)
    val title = cell.findViewById<TextView>(android.R.id.text2)
  }
}