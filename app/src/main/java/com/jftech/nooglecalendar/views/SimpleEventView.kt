package com.jftech.nooglecalendar.views

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.BlendModeColorFilterCompat
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class SimpleEventView(context: Context?) : ConstraintLayout(context)
{
    private lateinit var eventDateDayOfTheWeekTextView: TextView
    private lateinit var eventDateDayOfTheMonthTextView: TextView
    private lateinit var eventTitleTextView: TextView
    private lateinit var eventDurationTextView: TextView
    private lateinit var eventViewFrame: ConstraintLayout

    init
    {
        LayoutInflater.from(context).inflate(R.layout.simple_event_view, this)
    }

    fun InitWith(event: Event)
    {
        wireViews()
        colorForIsFutureEvent(CalendarViewModel.IsFuture(event.StartDate))
        addOnClickEvent(event)
        setParametersToViews(event)
    }

    private fun wireViews()
    {
        eventDateDayOfTheWeekTextView = findViewById(R.id.simple_event_view_dayoftheweek_textview)
        eventDateDayOfTheMonthTextView = findViewById(R.id.simple_event_view_dayofthemonth_textview)
        eventTitleTextView = findViewById(R.id.simple_event_view_event_title_textview)
        eventDurationTextView = findViewById(R.id.simple_event_view_duration_textview)
        eventViewFrame = findViewById(R.id.simple_event_view_event_frame)
    }

    private fun colorForIsFutureEvent(isFutureEvent: Boolean)
    {
        if (isFutureEvent)
        {
            eventDateDayOfTheMonthTextView.setTextColor(resources.getColor(R.color.activeDate))
            eventDateDayOfTheWeekTextView.setTextColor(resources.getColor(R.color.activeDate))
        }
        else
        {
            eventDateDayOfTheMonthTextView.setTextColor(resources.getColor(R.color.inactiveDate))
            eventDateDayOfTheWeekTextView.setTextColor(resources.getColor(R.color.inactiveDate))
        }
    }

    private fun setParametersToViews(event: Event)
    {
        eventDateDayOfTheWeekTextView.text = event.StartDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        eventDateDayOfTheMonthTextView.text = "${event.StartDate.dayOfMonth}"
        eventTitleTextView.text = event.Title
        eventDurationTextView.text = "${DateTimeFormatter.ofPattern("kk:mm").format(event.StartDate.toLocalTime())} - ${DateTimeFormatter.ofPattern("kk:mm").format(event.EndDate.toLocalTime())}"
        val colorToUpdate = Color.valueOf(event.EventColor)
        eventViewFrame.background.setColorFilter(Color.valueOf(colorToUpdate.red(), colorToUpdate.green(), colorToUpdate.blue(), 0.60f).toArgb(), PorterDuff.Mode.SRC_IN)
    }

    private fun addOnClickEvent(event: Event)
    {
        eventViewFrame.setOnClickListener { EventViewModel.OnEventClick(event) }
        eventViewFrame.setOnLongClickListener { EventViewModel.OnEventLongClick(event) }
    }
}