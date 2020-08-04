package com.jftech.nooglecalendar.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jftech.nooglecalendar.CalendarApp
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import com.jftech.nooglecalendar.views.gesture_recognizers.PanGestureListener

class MonthFragment(val contextActv: Context): Fragment()
{
    private lateinit var monthTableRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return  inflater.inflate(R.layout.month_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        CalendarApp.MonthFragmentReference = this
        monthTableRecyclerView = view.findViewById(R.id.month_table_recyclerview)
        monthTableRecyclerView.requestDisallowInterceptTouchEvent(false)
        monthTableRecyclerView.layoutManager = GridLayoutManager(context,7)
        setAdapter()
        monthTableRecyclerView.setOnTouchListener(object: PanGestureListener(contextActv)
        {
            override fun OnPanLeft(): Boolean
            {
                CalendarViewModel.ShiftCurrentDateDown()
                return true
            }

            override fun OnPanRight(): Boolean
            {
                CalendarViewModel.ShiftCurrentDateUp()
                return true
            }
        })
    }

    override fun onDestroy()
    {
        super.onDestroy()
        CalendarApp.MonthFragmentReference = null
    }

    fun OnDateChanged()
    {
        setAdapter()
    }

    private fun setAdapter()
    {
        monthTableRecyclerView.adapter = MonthFragmentTableAdapter(CalendarViewModel.GetDatesForCalendarView(CalendarViewModel.CurrentDate.value!!))
        monthTableRecyclerView.invalidate()
    }
}