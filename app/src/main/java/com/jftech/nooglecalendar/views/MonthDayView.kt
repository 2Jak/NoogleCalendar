package com.jftech.nooglecalendar.views

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class MonthDayView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, private var isTableItem: Boolean = false): ConstraintLayout(context, attrs, defStyleAttr)
{
    constructor(context: Context, isTableItem: Boolean): this(context, null, R.style.AppTheme, isTableItem)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, R.style.AppTheme)
    private var viewFrame: ConstraintLayout? = null
    private var monthDayTextViewFrame: RelativeLayout? = null
    private var monthDayTextView: TextView? = null
    private var eventsContainer: LinearLayout? = null
    private var isToday: Boolean = false
        set(value)
        {
            if (value)
                MakeToday()
            field = value
        }
    private var isThisMonth: Boolean = false
        set(value)
        {
            if (value)
                monthDayTextView!!.setTextColor(resources.getColor(R.color.activeDate))
            else
                monthDayTextView!!.setTextColor(resources.getColor(R.color.inactiveDate))
            field = value
        }
    lateinit var Date: LocalDateTime
        private set

    init
    {
        LayoutInflater.from(context).inflate(R.layout.month_table_item_layout, this)
    }

    fun InitWith(date: LocalDateTime)
    {
        wireViews()
        this.Date = date
        if (!isTableItem)
        {
            viewFrame!!.setBackgroundResource(0)

        }
        else
        {
            for (event in EventViewModel.GetEventsForDate(date))
            {
                var drawable = context.getDrawable(R.drawable.daily_day_title_view_background) as GradientDrawable
                drawable.mutate()
                drawable.setColor(event.EventColor)
                var view = View(context)
                view.apply {
                    layoutParams = LayoutParams(25,25)
                    background = drawable
                }
                eventsContainer!!.addView(view)
            }
        }
        monthDayTextView!!.text = date.dayOfMonth.toString()
        isThisMonth = CalendarViewModel.IsCurrentMonthFor(date)
        isToday = CalendarViewModel.IsTodayFor(date)
        // TODO: 27/07/2020 Get events for current date and add views to container
    }

    fun MakeToday()
    {
        monthDayTextView!!.setTextColor(resources.getColor(R.color.todaysDate))
        if (isTableItem)
            monthDayTextViewFrame!!.setBackgroundResource(R.drawable.daily_day_title_view_background)
        else
            viewFrame!!.setBackgroundResource(R.drawable.daily_day_title_view_background)
    }

    private fun wireViews()
    {
        viewFrame = findViewById(R.id.month_item_frame)
        monthDayTextView = findViewById(R.id.month_item_day_of_month)
        eventsContainer = findViewById(R.id.month_item_events_container)
        monthDayTextViewFrame = findViewById(R.id.month_name_textview_frame)
    }
}