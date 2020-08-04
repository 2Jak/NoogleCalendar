package com.jftech.nooglecalendar.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import java.time.LocalDateTime
import java.time.LocalTime

class DayView(context: Context, attrs: AttributeSet?) : View(context, attrs)
{
    private val backgroundPaint: Paint = Paint()
    private val todayLinePaint: Paint = Paint()
    private val currentTime = LocalTime.now()
    private var isToday = false

    init
    {
        backgroundPaint.color = Color.argb(255, 209,179,255)
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = resources.displayMetrics.density * 1f
        todayLinePaint.color = Color.argb(255, 0,255,75)
        todayLinePaint.style = Paint.Style.FILL_AND_STROKE
        todayLinePaint.strokeWidth = resources.displayMetrics.density * 2f
    }

    fun InitWith(date: LocalDateTime)
    {
        isToday = CalendarViewModel.IsTodayFor(date)
    }

    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)
        drawBackground(canvas)
    }

    private fun drawBackground(canvas: Canvas)
    {
        var yPosition: Float = 50f
        drawVerticalLine(canvas)
        if (isToday)
            drawNowLine(canvas)
        for (hour in 0..23)
        {
            drawHorizontalLine(canvas, yPosition)
            yPosition += 100
        }
    }

    private fun drawHorizontalLine(canvas: Canvas, yPosition: Float)
    {
        canvas.drawLine(0f, yPosition, this.width.toFloat(), yPosition, backgroundPaint)
    }

    private fun drawVerticalLine(canvas: Canvas)
    {
        canvas.drawLine(0f, 0.0f, 0f, this.height.toFloat(), backgroundPaint)
    }

    private fun drawNowLine(canvas: Canvas)
    {
        val currentPosition = ((currentTime.hour.toFloat() + (currentTime.minute.toFloat() / 60f)) * 100f) + 50f
        canvas.drawLine(0f, currentPosition, this.width.toFloat(), currentPosition, todayLinePaint)
        canvas.drawOval(0f, currentPosition - 20f, 40f, currentPosition + 20, todayLinePaint)
    }
}