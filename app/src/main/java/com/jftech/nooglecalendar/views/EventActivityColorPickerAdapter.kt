package com.jftech.nooglecalendar.views

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.viewmodels.EventViewModel

class EventActivityColorPickerAdapter(private var eventColor: Int): RecyclerView.Adapter<EventActivityColorPickerAdapter.EventActivityColorPickerViewHolder>()
{
    private val colors = arrayOf(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.CYAN)
    private val colorNames = arrayOf("Blue", "Green", "Red", "Yellow", "Magenta", "Cyan")
    private lateinit var contextReference: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventActivityColorPickerViewHolder
    {
        contextReference = parent.context
        return EventActivityColorPickerViewHolder(View.inflate(parent.context, R.layout.event_color_item_layout, null))
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: EventActivityColorPickerViewHolder, position: Int)
    {
        val colorImageView: ImageView = holder.itemView.findViewById(R.id.color_imageview)
        val colorNameTextView: TextView = holder.itemView.findViewById(R.id.color_textview)
        val drawable: GradientDrawable
        if (position == positionForColor(eventColor))
        {
            drawable = contextReference.getDrawable(R.drawable.daily_day_title_view_background) as GradientDrawable
            drawable.mutate()
            drawable.setColor(colors[position])
        }
        else
        {
            drawable = contextReference.getDrawable(R.drawable.event_color_unchecked_background) as GradientDrawable
            drawable.mutate()
            drawable.setStroke(15, colors[position])
        }
        colorImageView.setImageDrawable(drawable)
        colorNameTextView.text = colorNames[position]
        holder.itemView.setOnClickListener { onColorClick(position) }
    }

    private fun positionForColor(color: Int): Int
    {
        for (i in 1..colors.size)
            if (colors[i-1] == color)
                return i - 1
        return -1
    }

    private fun onColorClick(position: Int)
    {
        EventViewModel.OnColorPicked(colors[position])
    }

    class  EventActivityColorPickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}