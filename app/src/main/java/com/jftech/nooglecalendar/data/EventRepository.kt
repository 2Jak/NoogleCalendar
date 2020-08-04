package com.jftech.nooglecalendar.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jftech.nooglecalendar.data.database.RoomEventDatabase
import com.jftech.nooglecalendar.extensions.notifyObserver
import com.jftech.nooglecalendar.models.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

object EventRepository
{
    private lateinit var database: RoomEventDatabase
    private var events: MutableLiveData<MutableList<Event>> =  MutableLiveData<MutableList<Event>>().apply { value = mutableListOf() }
    var Events: LiveData<MutableList<Event>> = events
        private set


    suspend fun FillFromDB()
    {
         withContext(Dispatchers.IO) {
             val roomEvents = database.RoomEventDao().GetEvents()
             var events: MutableList<Event> = mutableListOf()
             for (rEvent in roomEvents)
                 events.add(Event(rEvent))
             this@EventRepository.events.postValue(events)
             Log.d("IO Channel", "{${this@EventRepository.events.value!!.size}")
         }
    }

    fun CreateOrUpdate(event: Event)
    {
        if (events.value!!.contains(event))
            events.value!![events.value!!.indexOf(event)] = event
        else
            addEvent(event)
        GlobalScope.launch { database.RoomEventDao().InserOrUpdate(event.toRoomEvent()) }
    }

    fun GetEventById(id: String): Event = events.value!![events.value!!.indexOfFirst { event -> event.id == id  }]

    fun GetEventByDate(date: LocalDateTime) = events.value!![events.value!!.indexOfFirst { event -> event.StartDate == date }]

    fun GetEventsByDate(date: LocalDateTime): Array<Event>
    {
        val events: MutableList<Event> = mutableListOf()
        for (event in this.events.value!!)
            if (event.StartDate.toLocalDate() == date.toLocalDate())
                events.add(event)
        return events.toTypedArray()
    }
    fun GetEventByTitle(title: String) = events.value!![events.value!!.indexOfFirst { event -> event.Title == title }]

    fun GetEventsContainingStringInTitle(searchString: String): Array<Event>
    {
        var searchResult: MutableList<Event> = mutableListOf()
        for (event in events.value!!)
            if (event.Title.contains(searchString, true))
                searchResult.add(event)
        return searchResult.toTypedArray()
    }

    fun DeleteEvent(event: Event)
    {
        removeEvent(event)
        GlobalScope.launch { database.RoomEventDao().DeleteEvent(event.toRoomEvent()) }
    }

    fun InitDB(context: Context)
    {
        database = RoomEventDatabase.GetInstance(context)
    }

    private fun addEvent(event: Event)
    {
        events.value!!.add(event)
        events.notifyObserver()
    }

    private fun removeEvent(event: Event)
    {
        events.value!!.remove(event)
        events.notifyObserver()
    }
}