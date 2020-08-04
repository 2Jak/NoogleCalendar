package com.jftech.nooglecalendar.views

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.EventViewModel

class SearchFragmentListAdapter(private val events: Array<Event>): RecyclerView.Adapter<SearchFragmentListAdapter.SearchFragmentListViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFragmentListAdapter.SearchFragmentListViewHolder
    {
        return SearchFragmentListViewHolder(SimpleEventView(parent.context))
    }

    override fun getItemCount(): Int
    {
        return events.size
    }

    override fun onBindViewHolder(holder: SearchFragmentListAdapter.SearchFragmentListViewHolder, position: Int)
    {
        val eventForPosition = events[position]
        val castView: SimpleEventView = holder.itemView as SimpleEventView
        castView.InitWith(eventForPosition)
        castView.setOnClickListener { EventViewModel.OnEventClick(eventForPosition) }
    }

    class SearchFragmentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}