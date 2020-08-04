package com.jftech.nooglecalendar.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver()
{
    this.value = this.value
}