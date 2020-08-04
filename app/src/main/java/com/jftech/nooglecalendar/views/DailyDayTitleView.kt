package com.jftech.nooglecalendar.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

class DailyDayTitleView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr)
{
    constructor(context: Context): this(context, null, R.style.AppTheme)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, R.style.AppTheme)
    private var dayOfMonthTextView: TextView? = null
    private var dayOfWeekTextView: TextView? = null
    private var dayOfMonthFrame: ConstraintLayout? = null
    private var dateString: String = ""
    private  var dayOfMonth = 0
        set(value)
        {
            dayOfMonthTextView!!.text = value.toString()
            invalidate()
            field = value
        }
    private var dayOfTheWeek: String = ""
        set(value)
        {
            dayOfWeekTextView!!.text = value
            invalidate()
            field = value
        }
    private var isThisWeek: Boolean = false
        set(value) 
        {
            if (value)
            {
                dayOfMonthTextView!!.setTextColor(resources.getColor(R.color.activeDate))
                dayOfWeekTextView!!.setTextColor(resources.getColor(R.color.activeDate))
            }
            else
            {
                dayOfMonthTextView!!.setTextColor(resources.getColor(R.color.inactiveDate))
                dayOfWeekTextView!!.setTextColor(resources.getColor(R.color.inactiveDate))
            }
            invalidate()
            field = value
        }
    private var isToday: Boolean = false
        set(value)
        {
            if (value)
            {
                dayOfMonthFrame!!.background = resources.getDrawable(R.drawable.daily_day_title_view_background)
                dayOfMonthTextView!!.setTextColor(resources.getColor(R.color.todaysDate))
                dayOfWeekTextView!!.setTextColor(resources.getColor(R.color.calendarPrimary))
            }
            else
            {
                dayOfMonthFrame!!.setBackgroundResource(0)
            }
            invalidate()
            field = value
        }

    init
    {
        LayoutInflater.from(context).inflate(R.layout.daily_day_title_layout, this)
//        setOnClickListener {
//            CalendarViewModel.OnDailyDayTitleClickEvent(dateString)
//        }
    }

    fun PostInit(date: LocalDateTime, shortText: Boolean = false)
    {
        wireTextViews()
        this.isThisWeek = CalendarViewModel.IsCurrentWeekFor(date)
        this.isToday = CalendarViewModel.IsTodayFor(date)
        this.dateString = date.toString()
        this.dayOfTheWeek = date.dayOfWeek.getDisplayName(if(shortText) TextStyle.NARROW else TextStyle.SHORT, Locale.getDefault())
        this.dayOfMonth = date.dayOfMonth
    }

    private fun wireTextViews()
    {
        dayOfMonthTextView = findViewById(R.id.daily_day_month_textview)
        dayOfWeekTextView = findViewById(R.id.daily_day_week_textview)
        dayOfMonthFrame = findViewById(R.id.daily_day_month_frame_view)
    }
}