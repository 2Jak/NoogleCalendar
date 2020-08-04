package com.jftech.nooglecalendar.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jftech.nooglecalendar.CalendarApp
import java.time.LocalDateTime
import kotlin.math.absoluteValue

class CalendarViewModel(): ViewModel()
{
    companion object
    {
        enum class DailyType
        {
            OneDay,
            ThreeDays,
            Week,
            Month,
            Schedule
        }

        var CurrentDate = MutableLiveData<LocalDateTime>().apply { value = LocalDateTime.now() }

        fun OnDailyDayTitleClickEvent(dateString: String) = changeCurrentDate(LocalDateTime.parse(dateString))

        fun ShowToday()
        {
            CurrentDate.value = LocalDateTime.now()
        }

        fun GetDatesForCurrentDayFor(daily: DailyType): Array<LocalDateTime>
        {
            val nowDate = CurrentDate.value!!
            val dates: MutableList<LocalDateTime> = mutableListOf(nowDate)
            return when (daily)
            {
                DailyType.OneDay ->
                {
                    dates.toTypedArray()
                }
                DailyType.ThreeDays ->
                {
                    dates.addAll(datesAfter(nowDate, 2))
                    dates.toTypedArray()
                }
                DailyType.Week ->
                {
                    for (date in datesBefore(nowDate, nowDate.dayOfWeek.value - 1))
                        dates.add(0, date)
                    dates.addAll(datesAfter(nowDate, 7 - nowDate.dayOfWeek.value))
                    dates.toTypedArray()
                }
                DailyType.Month -> arrayOf()
                DailyType.Schedule -> arrayOf()
            }
        }

        fun ShiftCurrentDateDown()
        {
            when (CalendarApp.CurrentDailyType!!)
            {
                DailyType.OneDay -> CurrentDate.value = CurrentDate.value!!.minusDays(1)
                DailyType.ThreeDays -> CurrentDate.value = CurrentDate.value!!.minusDays(3)
                DailyType.Week -> CurrentDate.value = CurrentDate.value!!.minusDays(7)
                DailyType.Month -> CurrentDate.value = CurrentDate.value!!.minusMonths(1)
                DailyType.Schedule -> return
            }
        }

        fun ShiftCurrentDateUp()
        {
            when (CalendarApp.CurrentDailyType!!)
            {
                DailyType.OneDay -> CurrentDate.value = CurrentDate.value!!.plusDays(1)
                DailyType.ThreeDays -> CurrentDate.value = CurrentDate.value!!.plusDays(3)
                DailyType.Week -> CurrentDate.value = CurrentDate.value!!.plusDays(7)
                DailyType.Month -> CurrentDate.value = CurrentDate.value!!.plusMonths(1)
                DailyType.Schedule -> return
            }
        }

        fun GetDatesForCalendarView(beginDate: LocalDateTime): Array<LocalDateTime> = datesForFullMonth(beginDate)

        fun OnMonthTableDayClick(date: LocalDateTime) = changeCurrentDate(date)

        fun IsCurrentWeekFor(date: LocalDateTime): Boolean
        {
            val dateWeek =  Math.floor((date.dayOfYear.toDouble() /  7f) - 0.7f).toInt()
            val currentWeek = Math.floor((LocalDateTime.now().dayOfYear.toDouble() / 7f) - 0.866f).toInt()
            return dateWeek == currentWeek
        }

        fun IsCurrentMonthFor(date: LocalDateTime): Boolean = date.monthValue == LocalDateTime.now().monthValue

        fun IsTodayFor(date: LocalDateTime): Boolean = date.toLocalDate() == LocalDateTime.now().toLocalDate()

        fun IsFuture(date: LocalDateTime): Boolean = LocalDateTime.now() < date

        private fun datesBefore(beginDate: LocalDateTime, daysAgo: Int): Array<LocalDateTime>
        {
            var dates: MutableList<LocalDateTime> = mutableListOf()
            var beginDate = beginDate
            for (i in  1..daysAgo)
            {
                beginDate = beginDate.minusDays(1)
                dates.add(beginDate)
            }
            return dates.toTypedArray()
        }

        private fun datesAfter(beginDate: LocalDateTime, daysAhead: Int): Array<LocalDateTime>
        {
            var dates: MutableList<LocalDateTime> = mutableListOf()
            var beginDate = beginDate
            for (i in  1..daysAhead)
            {
                beginDate = beginDate.plusDays(1)
                dates.add(beginDate)
            }
            return dates.toTypedArray()
        }

        private fun datesForFullMonth(beginDate: LocalDateTime): Array<LocalDateTime>
        {
            val numberOfDays = 42
            var beginDate = beginDate.minusDays(beginDate.dayOfMonth.toLong() - 1)
            beginDate = beginDate.minusDays( beginDate.dayOfWeek.value.toLong())
            var dates: MutableList<LocalDateTime> = mutableListOf(beginDate)
            for (i in 2..numberOfDays)
            {
                beginDate = beginDate.plusDays(1)
                dates.add(beginDate)
            }
            return dates.toTypedArray()
        }

        private fun changeCurrentDate(date: LocalDateTime)
        {
            CurrentDate.value = date
        }
    }
}