package com.jftech.nooglecalendar.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jftech.nooglecalendar.CalendarApp
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.data.EventRepository
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel

class SearchFragment: Fragment()
{
    private lateinit var eventListRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return  inflater.inflate(R.layout.search_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        CalendarApp.SearchFragmentReference = this
        eventListRecyclerView = view.findViewById(R.id.search_fragment_recyclerview)
        eventListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        CalendarApp.SearchFragmentReference = null
    }

    fun OnSearchGotResult(events: Array<Event>)
    {
        setAdapter(events)
    }

    private fun setAdapter(events: Array<Event>)
    {
        eventListRecyclerView.adapter = SearchFragmentListAdapter(events)
        eventListRecyclerView.invalidate()
    }
}