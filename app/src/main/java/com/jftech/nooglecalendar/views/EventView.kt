package com.jftech.nooglecalendar.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.EventViewModel

class EventView(context: Context?, attrs: AttributeSet?) : View(context, attrs)
{
    private val backgroundPaint: Paint = Paint()
    private val titlePaint: Paint = Paint()
    private var eventTitle: String? = null
    lateinit var EventID: String

    init
    {
        backgroundPaint.apply {
            color = Color.argb(210,0,100,255)
            style = Paint.Style.FILL_AND_STROKE
        }
        titlePaint.apply {
            color = Color.WHITE
            style = Paint.Style.FILL_AND_STROKE
            textSize = 35f
        }
        this.setOnClickListener {
            EventViewModel.OnEventClick(EventViewModel.GetEventWithId(EventID))
        }
        this.setOnLongClickListener {
            //Returns Boolean
            EventViewModel.OnEventLongClick(EventViewModel.GetEventWithId(EventID))
        }
    }

    fun InitWith(event: Event)
    {
        backgroundPaint.apply {
            val colorToUpdate = Color.valueOf(event.EventColor)
            color = Color.valueOf(colorToUpdate.red(), colorToUpdate.green(), colorToUpdate.blue(), 0.60f).toArgb()
        }
        this.eventTitle = event.Title
        this.EventID = event.id
    }

    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)
        drawBackground(canvas)
    }

    private fun drawBackground(canvas: Canvas)
    {
        canvas.drawRoundRect(0f,0f, width.toFloat(), height.toFloat(),15f,15f, backgroundPaint)
        canvas.drawText(this.eventTitle ?: "Please Use InitWith(event) before adding an Event View", this.width.toFloat() * (1f/10f), (this.height.toFloat() * (1f/2f) + 17.5f), titlePaint)
    }
}