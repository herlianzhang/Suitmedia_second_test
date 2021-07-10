package com.herlianzhang.suitmedia_first_test.ui.event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.herlianzhang.suitmedia_first_test.utils.EventPreference
import com.herlianzhang.suitmedia_first_test.vo.Event
import com.herlianzhang.suitmedia_first_test.vo.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(app: Application) : AndroidViewModel(app) {

    private val eventPref = EventPreference(getApplication())

    private val _data = MutableLiveData<List<Event>>()
    val data: LiveData<List<Event>>
        get() = _data

    private val _isEventMapView = MutableLiveData(eventPref.isEventMapView)
    val isEventMapView: LiveData<Boolean>
        get() = _isEventMapView

    private val _markerLocation = MutableLiveData<List<Location>>()
    val markerLocation: LiveData<List<Location>>
        get() = _markerLocation

    init {
        generateDummyData()
    }

    private fun generateDummyData() {
        val tmp = listOf(
            Event(
                0,
                "https://blog.excellence.asia/wp-content/uploads/2017/09/Untitled-design-3.jpg",
                "Let Success Make The Noise",
                "Nov 09 2014",
                listOf("nutricia", "highlight F3"),
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                3.586778875597328,
                98.69235248434691
            ),
            Event(
                1,
                "https://hipproduction.com/wp-content/uploads/2016/09/eventsbyambrosia-850x491.jpg",
                "Semangat Tahun Baru",
                "Oct 21 2014",
                listOf("nutricia"),
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                3.587721163281257,
                98.69073243014833
            ),
            Event(
                2,
                "https://images.unsplash.com/photo-1540575467063-178a50c2df87?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZXZlbnR8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80",
                "Work Hard In Smile",
                "Jun 14 2014",
                listOf("nutricia", "highlight F3", "event"),
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                3.5874213445781127,
                98.69324297771439
            ),
            Event(
                3,
                "https://i1.wp.com/insight.mbiz.co.id/wp-content/uploads/2019/01/event-activation-and-event-organizer.jpg?fit=888%2C667&ssl=1",
                "Another Message",
                "Jun 14 2014",
                listOf("event"),
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                3.5857707715888614,
                98.69261915691627
            )
        )
        _data.postValue(tmp)
    }

    fun setMarkLocation(position: Int) {
        val markerLocation = _data.value?.mapIndexed() { index, event ->
            Location(
                name = event.name.toString(),
                latLng = LatLng(event.lat ?: 0.0, event.lng ?: 0.0),
                index == position
            )
        } ?: emptyList()
        _markerLocation.postValue(markerLocation)
    }

    fun changeEventStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            eventPref.changeEventStatus()
            _isEventMapView.postValue(eventPref.isEventMapView)
        }
    }
}

