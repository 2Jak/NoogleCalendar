package com.jftech.nooglecalendar.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DailyFragementHoursView(context: Context?, attrs: AttributeSet?) : View(context, attrs)
{
    private var lineX: Float = 0.0f
    private val backgroundPaint: Paint = Paint()
    private val timeLabelPaint: Paint = Paint()

    init
    {
        backgroundPaint.color = Color.argb(255, 209,179,255)
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = resources.displayMetrics.density * 1f
        timeLabelPaint.color = Color.argb(255, 179,128,255)
        timeLabelPaint.style = Paint.Style.FILL_AND_STROKE
        timeLabelPaint.textSize = 35f
    }
    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)
        lineX = this.width.toFloat() * (1.0f/7.0f)
        drawBackground(canvas)
    }

    private fun drawBackground(canvas: Canvas)
    {
        var yPosition: Float = 50f
        for (hour in 0..23)
        {
            drawHorizontalLine(canvas, yPosition)
            drawTimeLabel(canvas, hour, yPosition)
            yPosition += 100
        }
    }

    private fun drawHorizontalLine(canvas: Canvas, yPosition: Float)
    {
        canvas.drawLine(this.width.toFloat() * (6.0f/7.0f), yPosition, this.width.toFloat(), yPosition, backgroundPaint)
    }

    private fun drawTimeLabel(canvas: Canvas, hour: Int, yPosition: Float)
    {
        canvas.drawText(if (hour > 9) "$hour:00" else "0$hour:00", lineX, yPosition + (35f/3f), timeLabelPaint)
    }
}