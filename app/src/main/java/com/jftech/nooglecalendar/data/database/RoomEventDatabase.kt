package com.jftech.nooglecalendar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jftech.nooglecalendar.data.database.models.RoomEvent

private const val DATABASE = "events"

@Database(
    entities = [RoomEvent::class],
    version = 1,
    exportSchema = false
)

abstract class RoomEventDatabase: RoomDatabase()
{
    abstract fun RoomEventDao(): EventDAO

    companion object
    {
        @Volatile
        private var instance: RoomEventDatabase? = null

        fun GetInstance(context: Context): RoomEventDatabase
        {
            return instance ?: synchronized(this)
            {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RoomEventDatabase
        {
            return Room.databaseBuilder(context, RoomEventDatabase::class.java, DATABASE).build()
        }
    }
}