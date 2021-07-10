package com.herlianzhang.suitmedia_first_test.vo

import com.google.android.gms.maps.model.LatLng

data class Location(
    val name: String,
    val latLng: LatLng,
    val isSelected: Boolean,
)