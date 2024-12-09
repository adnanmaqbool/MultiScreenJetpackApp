package com.axelliant.wearhouse.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ObservableCode  : MutableLiveData<Event<Int>>() {

    fun set(newValue: Int) {
        value = Event(newValue)
    }
}