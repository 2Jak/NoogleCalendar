package com.jftech.nooglecalendar

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import com.jftech.nooglecalendar.views.*

object CalendarApp
{
            //Calendar Fragment
    //Calendar Fragment References
    enum class CalendarFragmentType
    {
        Schedule,
        Daily,
        Month,
        Search
    }
    var CalendarActivityReference: CalendarActivity? = null
    var MainToolbarReference: ExtendedToolbar? = null
    var CurrentFragmentType: CalendarFragmentType? = null
    var CurrentDailyType: CalendarViewModel.Companion.DailyType? = null
    var DailyFragmentReference: DailyFragment? = null
    var ScheduleFragmentReference: ScheduleFragment? = null
    var MonthFragmentReference: MonthFragment? = null
    var SearchFragmentReference: SearchFragment? = null

    //Global Event Connectors
    fun NotifyDateChanges()
    {
        if (DailyFragmentReference != null)
            DailyFragmentReference!!.OnDateChanged()
        if (MonthFragmentReference != null)
            MonthFragmentReference!!.OnDateChanged()
        if (ScheduleFragmentReference != null)
            ScheduleFragmentReference!!.OnDateChanged()
        if (MainToolbarReference != null)
            MainToolbarReference!!.Refresh()
    }

    fun NotifyEventChanges()
    {
        if (DailyFragmentReference != null)
            DailyFragmentReference!!.OnDateChanged()
        if (MonthFragmentReference != null)
            MonthFragmentReference!!.OnDateChanged()
        if (ScheduleFragmentReference != null)
            ScheduleFragmentReference!!.OnDateChanged()
    }

    fun OnDailyToolbarExtended(dayTitlesLayout: LinearLayout?)
    {
        if (DailyFragmentReference != null)
            if (dayTitlesLayout != null)
                DailyFragmentReference!!.OnToolbarExpanded(dayTitlesLayout)
            else
                DailyFragmentReference!!.OnToolbarContracted()
        else
            Log.d("App Error!", "How did you even get here??? Expending daily calendar_toolbar with no daily fragment")
    }

    fun OnDailyToolbarContracted(dayTitlesLayout: LinearLayout)
    {
        if (MainToolbarReference != null)
            MainToolbarReference!!.OnCalendarContracted(dayTitlesLayout)
        else
            Log.d("App Error!", "How did you even get here??? Contracting a calendar_toolbar that doesn't exist eh?")
    }

    fun OnEventSearchResult(events: Array<Event>)
    {
        if (SearchFragmentReference != null)
            SearchFragmentReference!!.OnSearchGotResult(events)
        else
            Log.d("App Error!", "How did you even get here??? search toolbar is up but not the fragment?")
    }

            //Event Fragment
    //Global Event Connectors
    fun LoadEventActivityWith(event: Event, context: Context)
    {
        EventViewModel.SelectedEvent = event
        val eventIntent = Intent(context, EventActivity::class.java)
        context.startActivity(eventIntent)
    }
}