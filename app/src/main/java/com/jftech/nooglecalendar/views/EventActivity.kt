package com.jftech.nooglecalendar.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jftech.nooglecalendar.R
import com.jftech.nooglecalendar.models.Event
import com.jftech.nooglecalendar.viewmodels.EventViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class EventActivity: AppCompatActivity()
{
    private lateinit var toolbar: Toolbar
    private lateinit var titleEditText: EditText
    private lateinit var startDateButton: Button
    private lateinit var startTimeButton: Button
    private lateinit var endDateButton: Button
    private lateinit var endTimeButton: Button
    private lateinit var colorPickerRecyclerView: RecyclerView
    private lateinit var descriptionEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var startDate: LocalDate
    private lateinit var startTime: LocalTime
    private lateinit var endDate: LocalDate
    private lateinit var endTime: LocalTime

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        wireViews()
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        saveButton.setOnClickListener { onSaveButtonClicked() }
        addEventsToDateTimeButtons()
        loadEvent()
        setAdapter()
        EventViewModel.EventColor.observe(this, Observer { setAdapter() })
    }

    private fun loadEvent()
    {
        titleEditText.setText(EventViewModel.SelectedEvent.Title, TextView.BufferType.EDITABLE)
        startDateButton.text = EventViewModel.SelectedEvent.StartDate.toLocalDate().toString()
        startTimeButton.text = EventViewModel.SelectedEvent.StartDate.toLocalTime().toString()
        endDateButton.text = EventViewModel.SelectedEvent.EndDate.toLocalDate().toString()
        endTimeButton.text = EventViewModel.SelectedEvent.EndDate.toLocalTime().toString()
        descriptionEditText.setText(EventViewModel.SelectedEvent.Description, TextView.BufferType.EDITABLE)
        startDate = EventViewModel.SelectedEvent.StartDate.toLocalDate()
        startTime = EventViewModel.SelectedEvent.StartDate.toLocalTime()
        endDate = EventViewModel.SelectedEvent.EndDate.toLocalDate()
        endTime = EventViewModel.SelectedEvent.EndDate.toLocalTime()
        EventViewModel.OnColorPicked(EventViewModel.SelectedEvent.EventColor)
    }

    private fun wireViews()
    {

        toolbar = findViewById(R.id.event_toolbar)
        saveButton = findViewById(R.id.event_toolbar_save_button)
        titleEditText = findViewById(R.id.event_title_textedit)
        startDateButton = findViewById(R.id.event_start_date)
        startTimeButton = findViewById(R.id.event_start_time)
        endDateButton = findViewById(R.id.event_end_date)
        endTimeButton = findViewById(R.id.event_end_time)
        colorPickerRecyclerView = findViewById(R.id.event_colors_recyclerview)
        descriptionEditText = findViewById(R.id.event_description_textedit)
    }

    private fun setAdapter()
    {
        colorPickerRecyclerView.layoutManager = LinearLayoutManager(this)
        colorPickerRecyclerView.adapter = EventActivityColorPickerAdapter(EventViewModel.EventColor.value!!)
        colorPickerRecyclerView.invalidate()
    }

    private fun addEventsToDateTimeButtons()
    {
        startDateButton.setOnClickListener {
            DatePickerDialog(this).apply {
                setOnDateSetListener { _, y, m, d ->
                    onDatePicked(true, LocalDate.of(y,m + 1,d))
                }
            }.show()
        }
        startTimeButton.setOnClickListener {
            val time = LocalTime.now()
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, h, m ->
                onTimePicked(true, LocalTime.of(h,m))
            }, time.hour, time.minute, true).show()
        }
        endDateButton.setOnClickListener {
            DatePickerDialog(this).apply {
                setOnDateSetListener { _, y, m, d ->
                    onDatePicked(false, LocalDate.of(y,m + 1,d))
                }
            }.show()
        }
        endTimeButton.setOnClickListener {
            val time = LocalTime.now().plusHours(1)
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, h, m ->
                onTimePicked(false, LocalTime.of(h,m))
            }, time.hour, time.minute, true).show()
        }
    }

    private fun onDatePicked(isStart: Boolean, date: LocalDate)
    {
        if (isStart)
        {
            startDate = date
            startDateButton.text = date.toString()
        }
        else
        {
            endDate = date
            endDateButton.text = date.toString()
        }
    }

    private fun onTimePicked(isStart: Boolean, time: LocalTime)
    {
        if (isStart)
        {
            startTime = time
            startTimeButton.text = time.toString()
        }
        else
        {
            endTime = time
            endTimeButton.text = time.toString()
        }
    }

    private fun onSaveButtonClicked()
    {
        EventViewModel.SelectedEvent.Update(titleEditText.text.toString(), LocalDateTime.of(startDate, startTime).toString(), LocalDateTime.of(endDate, endTime).toString(), descriptionEditText.text.toString(), EventViewModel.EventColor.value.toString())
        EventViewModel.CreateOrUpdate(EventViewModel.SelectedEvent)
        EventViewModel.SelectedEvent = Event()
        finish()
    }
}