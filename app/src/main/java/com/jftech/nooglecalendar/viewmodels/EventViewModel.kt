package com.jftech.nooglecalendar.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jftech.nooglecalendar.CalendarApp
import com.jftech.nooglecalendar.data.EventRepository
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.extensions.TimeToMinutes
import java.time.LocalDateTime

class EventViewModel(): ViewModel()
{
    companion object
    {
        var SelectedEvent: Event = Event()
        private var eColor: MutableLiveData<Int> = MutableLiveData<Int>(0)
        var EventColor: LiveData<Int> = eColor

        fun CalculateEventViewPosition(event: Event): Float
        {
            return (event.StartDate.hour.toFloat() + (event.StartDate.minute.toFloat() / 60f)) * 100f
        }

        fun CreateOrUpdate(event: Event) = EventRepository.CreateOrUpdate(event)

        fun GetEventWithId(id: String): Event = EventRepository.GetEventById(id)

        fun GetEventWithTitle(title: String): Event = EventRepository.GetEventByTitle(title)

        fun GetEventsContainingStringInTitle(searchString: String): Array<Event> = EventRepository.GetEventsContainingStringInTitle(searchString)

        fun GetEventsForDate(date: LocalDateTime): Array<Event> = EventRepository.GetEventsByDate(date)

        fun CalculateEventViewHeight(event: Event): Int = (CalculateDurationForEvent(event.StartDate, event.EndDate) * 100).toInt()

        fun CalculateDurationForEvent(startTime: LocalDateTime, endTime: LocalDateTime): Float = (endTime.TimeToMinutes() - startTime.TimeToMinutes()).toFloat() / 60f

        fun OnEventClick(event: Event) = CalendarApp.LoadEventActivityWith(event, CalendarApp.CalendarActivityReference!!)

        fun OnEventLongClick(event: Event): Boolean
        {
            EventRepository.DeleteEvent(event)
            return true
        }

        fun OnColorPicked(color: Int)
        {
            eColor.value = color
        }
    }
}