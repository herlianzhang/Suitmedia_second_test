package com.herlianzhang.suitmedia_first_test.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herlianzhang.suitmedia_first_test.vo.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor() : ViewModel() {

    private val _data = MutableLiveData<List<Event>>()
    val data: LiveData<List<Event>>
        get() = _data

    init {
        generateDummyData()
    }

    private fun generateDummyData() {
        val tmp = listOf(
            Event(
                0,
                "https://blog.excellence.asia/wp-content/uploads/2017/09/Untitled-design-3.jpg",
                "Let Success Make The Noise",
                "Nov 09 2014"
            ),
            Event(
                1,
                "https://hipproduction.com/wp-content/uploads/2016/09/eventsbyambrosia-850x491.jpg",
                "Semangat Tahun Baru",
                "Oct 21 2014"
            ),
            Event(
                2,
                "https://lh3.googleusercontent.com/proxy/CVvu0L93oftuMvnlziSuDS4iJUeWrLu2idqZsIxB5d7NOc9OfTISGtxzX5IPwckucLbNI1WVOqLs2a2K8CiaMGS9bwOyGt67PS5lnp8SsXQUA9E3p5pbsfiH-J7ZGKmKatBKkYIXaDw",
                "Work Hard In Smile",
                "Jun 14 2014"
            ),
            Event(
                3,
                "https://i1.wp.com/insight.mbiz.co.id/wp-content/uploads/2019/01/event-activation-and-event-organizer.jpg?fit=888%2C667&ssl=1",
                "Another Message",
                "Jun 14 2014"
            )
        )
        _data.postValue(tmp)
    }
}