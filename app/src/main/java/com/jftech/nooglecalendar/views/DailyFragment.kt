package com.jftech.nooglecalendar.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import com.jftech.nooglecalendar.CalendarApp
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel

class DailyFragment: Fragment()
{
    private var daysView: DaysLayoutView? = null
    private var dropdownContainer: LinearLayout? = null
    private var dailyType: CalendarViewModel.Companion.DailyType = CalendarApp.CurrentDailyType!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.daily_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        CalendarApp.DailyFragmentReference = this
        daysView = view.findViewById(R.id.daily_days_view)
        dropdownContainer = view.findViewById(R.id.dropdown_toolbar_daily_titles_container)
        daysView!!.ChangeLayoutType(dailyType)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        CalendarApp.DailyFragmentReference = null
        daysView = null
        dropdownContainer = null
    }

    fun OnDateChanged()
    {
        daysView!!.OnDateChanged()
    }

    fun OnToolbarExpanded(toolbarView: LinearLayout)
    {
        val notOneDayType = CalendarApp.CurrentDailyType == CalendarViewModel.Companion.DailyType.Week || CalendarApp.CurrentDailyType == CalendarViewModel.Companion.DailyType.ThreeDays
        val params: LinearLayout.LayoutParams =
            toolbarView.layoutParams as LinearLayout.LayoutParams
        params.marginStart = if (notOneDayType) 160 else 35
        toolbarView.apply {
            layoutParams = params
        }
        dropdownContainer!!.removeAllViews()
        dropdownContainer!!.addView(toolbarView)
    }

    fun OnToolbarContracted()
    {
        val daysLayoutView = dropdownContainer!![0] as LinearLayout
        val params: LinearLayout.LayoutParams =
            daysLayoutView.layoutParams as LinearLayout.LayoutParams
        params.marginStart = 0
        daysLayoutView.apply {
            layoutParams = params
        }
        dropdownContainer!!.removeAllViews()
        CalendarApp.OnDailyToolbarContracted(daysLayoutView)
    }
}