package com.jftech.nooglecalendar.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.jftech.nooglecalendar.CalendarApp
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import com.jftech.nooglecalendar.views.gesture_recognizers.PanGestureListener
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*


class ExtendedToolbar(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): Toolbar(context, attrs, defStyleAttr)
{
    constructor(context: Context?): this(context, null, R.style.AppTheme)
    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, R.style.AppTheme)

    private lateinit var itemsContainer: LinearLayout
    private var layoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    private var isExpanded: Boolean = false

    init
    {
        layoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            this.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener!!)
            layoutViews()
        }
        this.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
    }

    fun Refresh()
    {
        layoutViews()
    }

    fun OnCalendarContracted(dailyTitlesFrame: LinearLayout)
    {
        itemsContainer.addView(dailyTitlesFrame)
        invalidate()
    }

    private fun layoutViews()
    {
        initDefault()
        when (CalendarApp.CurrentFragmentType)
        {
            CalendarApp.CalendarFragmentType.Schedule -> initSchedule()
            CalendarApp.CalendarFragmentType.Daily -> initDaily()
            CalendarApp.CalendarFragmentType.Month -> initMonth()
            CalendarApp.CalendarFragmentType.Search -> initSearch()
            null -> Log.d("App Reference Error", "Trying to layout calendar_toolbar with no App reference")
        }
    }

    private fun initDefault()
    {
        itemsContainer = findViewById(R.id.toolbar_item_container)
        itemsContainer.removeAllViews()
        val showTodayButton: ImageButton = findViewById(R.id.show_today_imagebutton)
        showTodayButton.setOnClickListener {
            onShowTodayClicked()
        }
        requestFocus()
    }

    private fun initSchedule()
    {
        val params = layoutParams
        params.height = 150
        apply {
            layoutParams = params
        }
        val dailyToolbarMain = View.inflate(context, R.layout.calendar_toolbar_daily_main_layout, null)
        val dailyToolbarMainMonthTextView: TextView = dailyToolbarMain.findViewById(R.id.month_name_textview)
        dailyToolbarMainMonthTextView.text = CalendarViewModel.CurrentDate.value!!.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        itemsContainer.addView(dailyToolbarMain)
    }

    private fun initDaily()
    {
        val params = layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = resources.getDimension(R.dimen.daily_toolbar_height).toInt()
        apply {
            layoutParams = params
        }
        itemsContainer.removeAllViews()
        val dailyTitlesFrame = buildDayDateTitlesView(CalendarViewModel.GetDatesForCurrentDayFor(CalendarApp.CurrentDailyType!!))
        val dailyToolbarMain = View.inflate(context, R.layout.calendar_toolbar_daily_main_layout, null)
        val dailyToolbarMainMonthTextView: TextView = dailyToolbarMain.findViewById(R.id.month_name_textview)
        dailyToolbarMainMonthTextView.text = CalendarViewModel.CurrentDate.value!!.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        dailyTitlesFrame.setOnTouchListener(object: PanGestureListener(context)
        {
            override fun OnPanLeft(): Boolean
            {
                if (!isExpanded)
                    CalendarViewModel.ShiftCurrentDateDown()
                return true
            }

            override fun OnPanRight(): Boolean
            {
                if (!isExpanded)
                    CalendarViewModel.ShiftCurrentDateUp()
                return true
            }
        })
        itemsContainer.addView(dailyToolbarMain)
        itemsContainer.addView(dailyTitlesFrame)
        setListenersForDailyToolbar(dailyTitlesFrame)
        //Contract Calendar on click outside of calendar_toolbar
        setOnFocusChangeListener { _, b ->
            if (!b)
                this.onToolbarExpandClicked(dailyTitlesFrame)
        }
    }

    private fun initMonth()
    {
        val params = layoutParams
        params.height = 150
        apply {
            layoutParams = params
        }
        val dailyToolbarMain = View.inflate(context, R.layout.calendar_toolbar_daily_main_layout, null)
        dailyToolbarMain.findViewById<ImageView>(R.id.month_name_imageview).setImageDrawable(null)
        val dailyToolbarMainMonthTextView: TextView = dailyToolbarMain.findViewById(R.id.month_name_textview)
        dailyToolbarMainMonthTextView.text = CalendarViewModel.CurrentDate.value!!.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        itemsContainer.addView(dailyToolbarMain)
    }

    private fun initSearch()
    {
        val params = layoutParams
        params.height = 150
        apply {
            layoutParams = params
        }
        itemsContainer.removeAllViews()
        val searchView = SearchView(context)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                searchForEvents(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchForEvents(query)
                return true
            }
        })
        itemsContainer.addView(searchView)
    }

    private fun buildDayDateTitlesView(dates: Array<LocalDateTime>): LinearLayout
    {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.START)
        val layout = LinearLayout(context).apply {
            layoutParams = params
            weightSum = dates.size.toFloat()
        }
        for (date in dates)
        {
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            val titleViewFrame = LinearLayout(context).apply { layoutParams = params }
            val titleView = DailyDayTitleView(context)
            titleView.PostInit(date)
            titleViewFrame.addView(titleView)
            layout.addView(titleViewFrame)
        }
        return layout
    }

    private fun setListenersForDailyToolbar(dayTitlesLayout: LinearLayout)
    {
        val monthNameFrame: ConstraintLayout = itemsContainer.findViewById(R.id.month_name_frame)
        monthNameFrame.setOnClickListener {
            onToolbarExpandClicked(dayTitlesLayout)
        }
    }

    private fun onToolbarExpandClicked(dayTitlesLayout: LinearLayout)
    {
        changeVisibilityCalendarView()
        if (isExpanded)
            CalendarApp.OnDailyToolbarExtended(null)
        else
        {
            itemsContainer.removeView(dayTitlesLayout)
            CalendarApp.OnDailyToolbarExtended(dayTitlesLayout)
        }
        isExpanded = !isExpanded
    }

    private fun onShowTodayClicked()
    {
        CalendarViewModel.ShowToday()
    }


    private fun changeVisibilityCalendarView()
    {
        val calendarView: JCalendarView = itemsContainer.findViewById(R.id.month_toolbar_calendar)
        if (isExpanded)
            calendarView.visibility = View.GONE
        else
            calendarView.visibility = View.VISIBLE
    }

    private fun searchForEvents(searchString: String)
    {
        var result = EventViewModel.GetEventsContainingStringInTitle(searchString.trimStart().trimEnd())
        if (searchString.trim() == "")
            result = arrayOf()
        CalendarApp.OnEventSearchResult(result)
    }
}