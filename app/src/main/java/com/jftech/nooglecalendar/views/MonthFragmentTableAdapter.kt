package com.jftech.nooglecalendar.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jftech.nooglecalendar.viewmodels.CalendarViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class MonthFragmentTableAdapter(private val dates: Array<LocalDateTime>): RecyclerView.Adapter<MonthFragmentTableAdapter. MonthFragmentTableViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  MonthFragmentTableViewHolder
    {
        val view = MonthDayView(parent.context, true)
        view.apply {
            layoutParams = ViewGroup.LayoutParams(calculateViewWidth(parent.width, context), calculateViewHeight(parent.height, context))
        }
        return  MonthFragmentTableViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return 42
    }

    override fun onBindViewHolder(holder: MonthFragmentTableViewHolder, position: Int)
    {
        val castView = holder.itemView as MonthDayView
        castView.InitWith(dates[position])
        //castView.setOnClickListener { CalendarViewModel.OnMonthTableDayClick(dates[position]) }
    }

    private fun calculateViewWidth(parentWidth: Int, context: Context): Int
    {
        val width = parentWidth.toFloat() / 7f
        return width.toInt()
    }

    private fun calculateViewHeight(parentHeight: Int, context: Context): Int
    {
        val height = parentHeight.toFloat() / 6f
        return height.toInt()
    }

    class  MonthFragmentTableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}