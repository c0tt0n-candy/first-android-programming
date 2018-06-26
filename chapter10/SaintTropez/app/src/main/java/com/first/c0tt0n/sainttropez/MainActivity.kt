package com.first.c0tt0n.sainttropez

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.imageView

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    registerForContextMenu(imageView)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    val image = when (item?.itemId) {
      R.id.top -> R.drawable.toppage
      R.id.lunch01 -> R.drawable.lunch01
      R.id.lunch02 -> R.drawable.lunch02
      R.id.dinner01 -> R.drawable.dinner01
      R.id.dinner02 -> R.drawable.dinner02
      else -> null
    }

    image?.let {
      imageView.setImageResource(it)
      return true
    } ?: return super.onOptionsItemSelected(item)
  }

  override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context, menu)
  }

  override fun onContextItemSelected(item: MenuItem?): Boolean {
    val intent = when (item?.itemId) {
      R.id.sms -> {
        val number = "999-9999-9999"
        Intent(Intent.ACTION_VIEW).apply {
          data = Uri.parse("sms:$number")
        }
      }
      R.id.mail -> {
        val email = "nobody@example.com"
        val subject = "予約問い合わせ"
        val text = "以下の通り予約希望します。"
        Intent(Intent.ACTION_SENDTO).apply {
          data = Uri.parse("mailto:")
          putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
          putExtra(Intent.EXTRA_SUBJECT, subject)
          putExtra(Intent.EXTRA_TEXT, text)
        }
      }
      R.id.share -> {
        val text = "美味しいレストランを紹介します。"
        Intent(Intent.ACTION_SEND).apply {
          type = "text/plain"
          putExtra(Intent.EXTRA_TEXT, text)
        }
      }
      R.id.browse -> {
        val url = "http://www.yahoo.co.jp/"
        Intent(Intent.ACTION_VIEW).apply {
          data = Uri.parse(url)
        }
      }
      else -> null
    }

    intent?.let {
      if (it.resolveActivity(packageManager) != null) {
        when (item?.itemId) {
          R.id.share -> startActivity(Intent.createChooser(it, null))
          else -> startActivity(it)
        }
      }
      return true
    } ?: return super.onContextItemSelected(item)
  }
}
