package com.jftech.nooglecalendar.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel

class DaysLayoutView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs)
{
    private var layoutType: CalendarViewModel.Companion.DailyType = CalendarViewModel.Companion.DailyType.OneDay
        set(value)
        {
            field = value
            layoutViews()
        }

    init
    {
        layoutViews()
    }

    fun ChangeLayoutType(type: CalendarViewModel.Companion.DailyType)
    {
         layoutType = type
    }

    fun OnDateChanged()
    {
        layoutViews()
    }

    private fun layoutViews()
    {
        removeAllViews()
        val dates = CalendarViewModel.GetDatesForCurrentDayFor(layoutType)
        val count = setWeightSumGetCount()
        for (i in 1..count)
        {
            val dayHoursLayout = View.inflate(context, R.layout.day_hours_layout, null).apply()
            {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 2400,1f)
            }
            val eventsView: EventsView = dayHoursLayout.findViewById(R.id.day_hours_layout_events_view)
            eventsView.InitWith(dates[i - 1])
            val dayView: DayView = dayHoursLayout.findViewById(R.id.day_hours_layout_day_view)
            dayView.InitWith(dates[i - 1])
            addView(dayHoursLayout)
        }
        invalidate()
    }

    private fun setWeightSumGetCount(): Int
    {
       return when (layoutType)
        {
            CalendarViewModel.Companion.DailyType.OneDay ->
            {
                weightSum = 1f
                1
            }
            CalendarViewModel.Companion.DailyType.ThreeDays ->
            {
                weightSum = 3f
                3
            }
            CalendarViewModel.Companion.DailyType.Week ->
            {
                weightSum = 7f
                7
            }
            else -> 1
        }
    }
}