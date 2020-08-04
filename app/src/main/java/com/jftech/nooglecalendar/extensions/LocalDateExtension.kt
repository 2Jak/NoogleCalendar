package com.jftech.nooglecalendar.extensions

import java.time.LocalDateTime

fun LocalDateTime.TimeToMinutes(): Int = (this.hour * 60) + this.minute