package com.jftech.nooglecalendar.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import java.time.LocalDateTime
import kotlin.properties.Delegates

class EventsView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs)
{
    fun InitWith(date: LocalDateTime)
    {
        for (event: Event in EventViewModel.GetEventsForDate(date))
        {
            var view = EventView(context, null)
            view.InitWith(event)
            val params = LayoutParams(LayoutParams.MATCH_PARENT, EventViewModel.CalculateEventViewHeight(event))
            view.apply {
                layoutParams = params
            }
            view.y = EventViewModel.CalculateEventViewPosition(event) + 50f
            view.x = 2f
            addView(view)
        }
    }
}