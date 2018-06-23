package com.first.c0tt0n.accball

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import kotlinx.android.synthetic.main.activity_main.surfaceView

class MainActivity : AppCompatActivity(), SensorEventListener, SurfaceHolder.Callback {

  private val radius = 50.0f
  private val coef = 1000.0f
  private var ballX: Float = 0f
  private var ballY: Float = 0f
  private var vx: Float = 0f
  private var vy: Float = 0f
  private var time: Long = 0L

  private var surfaceWidth: Int = 0
  private var surfaceHeight: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    setContentView(R.layout.activity_main)

    surfaceView.holder.addCallback(this)
  }

  // [SensorEventListener]
  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    // need not implement
  }

  override fun onSensorChanged(event: SensorEvent?) {
    event ?: return

    if (time == 0L) time = System.currentTimeMillis()
    if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
      val x = -event.values[0]
      val y = event.values[1]

      var t = (System.currentTimeMillis() - time).toFloat()
      time = System.currentTimeMillis()
      t /= 1000.0f

      val dx = vx * t + x * t * t / 2.0f
      val dy = vy * t + y * t * t / 2.0f
      ballX += dx * coef
      ballY += dy * coef
      vx += x * t
      vy += y * t

      if (ballX - radius < 0 && vx < 0) {
        vx = -vx / 1.5f
        ballX = radius
      } else if (ballX + radius > surfaceWidth && vx > 0) {
        vx = -vx / 1.5f
        ballX = surfaceWidth - radius
      }
      if (ballY - radius < 0 && vy < 0) {
        vy = -vy / 1.5f
        ballY = radius
      } else if (ballY + radius > surfaceHeight && vy > 0) {
        vy = -vy / 1.5f
        ballY = surfaceHeight - radius
      }

      drawCanvas()
    }
  }

  // [SurfaceHolder.Callback]
  override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    surfaceWidth = width
    surfaceHeight = height
    ballX = (width / 2).toFloat()
    ballY = (height / 2).toFloat()
  }

  override fun surfaceDestroyed(holder: SurfaceHolder?) {
    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    sensorManager.unregisterListener(this)
  }

  override fun surfaceCreated(holder: SurfaceHolder?) {
    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME)
  }

  private fun drawCanvas() {
    val canvas = surfaceView.holder.lockCanvas()
    canvas.apply {
      drawColor(Color.YELLOW)
      drawCircle(ballX, ballY, radius, Paint().apply {
        color = Color.MAGENTA
      })
    }
    surfaceView.holder.unlockCanvasAndPost(canvas)
  }
}