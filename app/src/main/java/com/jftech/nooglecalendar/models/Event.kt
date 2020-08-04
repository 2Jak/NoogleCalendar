package com.jftech.nooglecalendar.models

import android.graphics.Color
import androidx.room.Room
import com.jftech.nooglecalendar.data.database.models.RoomEvent
import java.time.LocalDateTime
import java.util.*


class Event(val id: String, var Title: String, startDate: String, endDate: String, var Description: String, color: String, var StartDate: LocalDateTime = LocalDateTime.parse(startDate), var EndDate: LocalDateTime = LocalDateTime.parse(endDate), var EventColor: Int = color.toInt())
{
    constructor(rEvent: RoomEvent): this(rEvent.id, rEvent.title, rEvent.startDate, rEvent.endDate, rEvent.description, rEvent.color)
    constructor(): this("", "", LocalDateTime.now().toString(), LocalDateTime.now().plusHours(1).toString(), "", Color.BLUE.toString())
    constructor(title: String, startDate: String, endDate: String, description: String, color: String): this(UUID.randomUUID().toString(), title, startDate, endDate, description, color)

    override fun equals(other: Any?): Boolean
    {
        val castOther: Event? = other as Event
        if (castOther != null)
            if (castOther!!.id == this.id)
                return true
        return false
    }

    fun toRoomEvent(): RoomEvent
    {
        return RoomEvent(id, Title, StartDate.toString(), EndDate.toString(), Description, EventColor.toString())
    }

    fun Update(title: String, startDate: String, endDate: String, description: String, color: String)
    {
        this.Title = title
        this.StartDate = LocalDateTime.parse(startDate)
        this.EndDate = LocalDateTime.parse(endDate)
        this.Description = description
        this.EventColor = color.toInt()
    }
}