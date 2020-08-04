package com.jftech.nooglecalendar.views

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleFragmentListAdapter(private val events: Array<Event>): RecyclerView.Adapter<ScheduleFragmentListAdapter.ScheduleFragmentListViewHolder>()
{
    private var viewHeight: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleFragmentListViewHolder
    {
       return ScheduleFragmentListViewHolder(SimpleEventView(parent.context))
    }

    override fun getItemCount(): Int
    {
        return events.size
    }

    override fun onBindViewHolder(holder: ScheduleFragmentListViewHolder, position: Int)
    {
        val eventForPosition = events[position]
        val castView: SimpleEventView = holder.itemView as SimpleEventView
        viewHeight = castView.height
        castView.InitWith(eventForPosition)
        castView.setOnClickListener { EventViewModel.OnEventClick(eventForPosition) }
    }

    fun GetPositionFor(date: LocalDateTime): Int
    {
        val eventIndex = events.indexOfFirst { event -> event.StartDate.toLocalDate() == date.toLocalDate() }
        if (eventIndex == -1)
            return eventIndex
        return eventIndex * viewHeight
    }

    class  ScheduleFragmentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}