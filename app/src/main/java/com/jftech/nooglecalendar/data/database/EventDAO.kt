package com.jftech.nooglecalendar.data.database

import androidx.room.*
import com.jftech.nooglecalendar.data.database.models.RoomEvent

@Dao
interface EventDAO
{
    @Query("SELECT * FROM events")
    suspend fun GetEvents(): List<RoomEvent>
    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun GetEventById(id: String): RoomEvent
    @Query("SELECT * FROM events WHERE start_date = :startDate")
    suspend fun GetEventByStartDate(startDate: String): RoomEvent
    @Delete
    suspend fun DeleteEvent(event: RoomEvent)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InserOrUpdate(event: RoomEvent): Long
}