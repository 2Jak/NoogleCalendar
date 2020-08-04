package com.jftech.nooglecalendar.views

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.jftech.nooglecalendar.CalendarApp
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.data.EventRepository
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import kotlinx.android.synthetic.main.calendar_toolbar.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: 29/07/2020 Bug fixes
class CalendarActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: ExtendedToolbar
    private lateinit var navigationView: NavigationView
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var addEventButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        EventRepository.InitDB(applicationContext)
        GlobalScope.launch { EventRepository.FillFromDB() }
        CalendarApp.CalendarActivityReference = this

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        toolbar = findViewById(R.id.toolbar)
        CalendarApp.MainToolbarReference = toolbar

        setSupportActionBar(toolbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()

        fragmentManager = supportFragmentManager
        addEventButton = findViewById(R.id.calendar_add_event_imagebutton)
        addEventButton.setOnClickListener {
            CalendarApp.LoadEventActivityWith(Event(), this)
        }
        CalendarViewModel.CurrentDate.observe(this, Observer { CalendarApp.NotifyDateChanges() })
        EventRepository.Events.observe(this, Observer { CalendarApp.NotifyEventChanges() })

        if (CalendarApp.CurrentFragmentType == null)
        {
            CalendarApp.CurrentFragmentType = CalendarApp.CalendarFragmentType.Daily
            CalendarApp.CurrentDailyType = CalendarViewModel.Companion.DailyType.OneDay
        }
        setFragmentForType()
    }

    override fun onStart()
    {
        super.onStart()
        CalendarApp.NotifyEventChanges()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean
    {
        when (p0.itemId)
        {
            R.id.navItemSchedule ->
            {
                CalendarApp.CurrentFragmentType = CalendarApp.CalendarFragmentType.Schedule
                CalendarApp.CurrentDailyType = CalendarViewModel.Companion.DailyType.Schedule
                replaceFragment(ScheduleFragment())
            }
            R.id.navItemDay ->
            {
                CalendarApp.CurrentFragmentType = CalendarApp.CalendarFragmentType.Daily
                CalendarApp.CurrentDailyType = CalendarViewModel.Companion.DailyType.OneDay
                replaceFragment(DailyFragment())
            }
            R.id.navItem3Days ->
            {
                CalendarApp.CurrentFragmentType = CalendarApp.CalendarFragmentType.Daily
                CalendarApp.CurrentDailyType = CalendarViewModel.Companion.DailyType.ThreeDays
                replaceFragment(DailyFragment())
            }
            R.id.navItemWeek ->
            {
                CalendarApp.CurrentFragmentType = CalendarApp.CalendarFragmentType.Daily
                CalendarApp.CurrentDailyType = CalendarViewModel.Companion.DailyType.Week
                replaceFragment(DailyFragment())
            }
            R.id.navItemMonth ->
            {
                CalendarApp.CurrentDailyType = CalendarViewModel.Companion.DailyType.Month
                CalendarApp.CurrentFragmentType = CalendarApp.CalendarFragmentType.Month
                replaceFragment(MonthFragment(this))
            }
            R.id.navItemSearch ->
            {
                CalendarApp.CurrentDailyType = null
                CalendarApp.CurrentFragmentType = CalendarApp.CalendarFragmentType.Search
                replaceFragment(SearchFragment())
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        CalendarApp.MainToolbarReference!!.Refresh()
        return true
    }

    override fun onDestroy()
    {
        super.onDestroy()
        CalendarApp.MainToolbarReference = null
        CalendarApp.CalendarActivityReference = null
    }

    private fun replaceFragment(fragment: Fragment)
    {
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
    
    private fun setFragmentForType()
    {
        when (CalendarApp.CurrentFragmentType!!)
        {
            CalendarApp.CalendarFragmentType.Schedule -> replaceFragment(ScheduleFragment())
            CalendarApp.CalendarFragmentType.Daily -> replaceFragment(DailyFragment())
            CalendarApp.CalendarFragmentType.Month -> replaceFragment(MonthFragment(this))
            CalendarApp.CalendarFragmentType.Search -> replaceFragment(SearchFragment())
        }
    }
}