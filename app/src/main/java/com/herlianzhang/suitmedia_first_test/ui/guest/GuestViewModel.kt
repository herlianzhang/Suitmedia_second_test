package com.herlianzhang.suitmedia_first_test.ui.guest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herlianzhang.suitmedia_first_test.api.Result
import com.herlianzhang.suitmedia_first_test.repository.GuestRepository
import com.herlianzhang.suitmedia_first_test.utils.Event
import com.herlianzhang.suitmedia_first_test.vo.Guest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GuestViewModel @Inject constructor(private val rep: GuestRepository) : ViewModel() {

    private var job: Job? = null

    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private val _data = MutableLiveData<List<Guest>>()
    val data: LiveData<List<Guest>>
        get() = _data

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean>
        get() = _isError

    private val _navigateBack = MutableLiveData<Event<Pair<String, String>>>()
    val navigateBack: LiveData<Event<Pair<String, String>>>
        get() = _navigateBack

    init {
        getGuests()
    }

    fun getGuests() {
        if (job?.isActive == true) return

        job?.cancel()
        job = viewModelScope.launch {
            _isError.postValue(false)
            rep.getGuests().collect {
                when (it) {
                    is Result.Success -> {
                        it.data?.let { data ->
                            _data.postValue(data)
                        }
                    }
                    is Result.Error -> _isError.postValue(true)
                    is Result.Loading -> _isLoading.postValue(it.value)
                }
            }
        }
    }

    fun onClick(guest: Guest) {
        try {
            val calendar = Calendar.getInstance().also {
                it.time = format.parse(guest.birthdate.toString()) ?: it.time
            }
            val date = calendar.get(Calendar.DATE)
            val message = when {
                date % 2 == 0 && date % 3 == 0 -> "IOS"
                date % 2 == 0 -> "blackberry"
                date % 3 == 0 -> "android"
                else -> "feature phone"
            }
            _navigateBack.postValue(Event(Pair(guest.name.toString(), message)))
        } catch (e: Exception) {
            Timber.e("Exception cause $e")
        }
    }
}