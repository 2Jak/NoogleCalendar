package com.jftech.nooglecalendar.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jftech.nooglecalendar.CalendarApp
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.data.EventRepository
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import com.jftech.nooglecalendar.viewmodels.EventViewModel

class ScheduleFragment: Fragment()
{
    private lateinit var eventListRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return  inflater.inflate(R.layout.schedule_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        CalendarApp.ScheduleFragmentReference = this
        eventListRecyclerView = view.findViewById(R.id.schedule_fragment_recyclerview)
        eventListRecyclerView.layoutManager = LinearLayoutManager(context)
        setAdapter()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        CalendarApp.ScheduleFragmentReference = null
    }

    fun OnDateChanged()
    {
        setAdapter()
        //eventListRecyclerView.verticalScrollbarPosition = (eventListRecyclerView.adapter as ScheduleFragmentListAdapter).GetPositionFor(CalendarViewModel.CurrentDate.value!!)
    }

    private fun setAdapter()
    {
        eventListRecyclerView.adapter = ScheduleFragmentListAdapter(EventRepository.Events.value!!.toTypedArray())
        eventListRecyclerView.invalidate()
    }
}