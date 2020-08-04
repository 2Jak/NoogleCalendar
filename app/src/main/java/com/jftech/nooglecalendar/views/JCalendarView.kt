package com.jftech.nooglecalendar.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class JCalendarView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs)
{
    private var currentDate = CalendarViewModel.CurrentDate.value
    private var layoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    init
    {
        layoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            this.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener!!)
            layoutViews()
        }
        this.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
    }

    private fun layoutViews()
    {
        val dates = CalendarViewModel.GetDatesForCalendarView(currentDate!!)
        var dateCount = 0
        for (i in 0..5)
        {
            for (j in 0..6)
            {
                val date = dates[dateCount]
                if (date.month == currentDate!!.month)
                {
                    val viewSize = calculateDayViewSize()
                    val dayParams = ConstraintLayout.LayoutParams(viewSize, viewSize)
                    val dayView = MonthDayView(context, null).apply {
                        layoutParams = dayParams
                    }
                    dayView.InitWith(date)
                    dayView.setOnClickListener { onDayViewClick(dayView.Date) }
                    dayView.x = j.toFloat()/7f * this.width.toFloat()
                    dayView.y = i.toFloat()/6f * this.height.toFloat()
                    if (date == currentDate)
                        dayView.MakeToday()
                    addView(dayView)
                }
                dateCount++
            }
        }
        invalidate()
    }

    private fun calculateDayViewSize(): Int
    {
        val floatSize = this.width.toFloat() / 7
        return floatSize.toInt()
    }

    private fun onDayViewClick(date: LocalDateTime)
    {
        CalendarViewModel.OnMonthTableDayClick(date)
    }
}
