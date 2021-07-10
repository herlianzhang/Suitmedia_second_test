package com.herlianzhang.suitmedia_first_test.ui.event_or_guest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventOrGuestViewModel @Inject constructor() : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _event = MutableLiveData<String>()
    val event: LiveData<String>
        get() = _event

    private val _guest = MutableLiveData<String>()
    val guest: LiveData<String>
        get() = _guest

    fun setName(name: String) {
        _name.postValue(name)
    }

    fun setEvent(event: String) {
        _event.postValue(event)
    }

    fun setGuest(guest: String) {
        _guest.postValue(guest)
    }
}