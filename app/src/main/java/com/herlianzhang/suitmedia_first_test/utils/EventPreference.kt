package com.herlianzhang.suitmedia_first_test.utils

import android.content.Context
import androidx.core.content.edit

class EventPreference(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(EVENT, Context.MODE_PRIVATE)

    val isEventMapView: Boolean
        get()  = sharedPreferences.getBoolean(IS_EVENT_MAP_VIEW, false)

    fun changeEventStatus() {
        sharedPreferences.edit {
            putBoolean(IS_EVENT_MAP_VIEW, !isEventMapView)
        }
    }

    companion object {
        private const val EVENT = "event"
        private const val IS_EVENT_MAP_VIEW = "is_event_map_view"
    }
}