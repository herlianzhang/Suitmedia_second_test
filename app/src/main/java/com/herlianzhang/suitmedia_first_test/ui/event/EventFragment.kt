package com.herlianzhang.suitmedia_first_test.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import com.herlianzhang.suitmedia_first_test.R
import com.herlianzhang.suitmedia_first_test.databinding.FragmentEventBinding
import com.herlianzhang.suitmedia_first_test.ui.event_or_guest.EventOrGuestFragment.Companion.EVENT_NAME
import com.herlianzhang.suitmedia_first_test.ui.event_or_guest.EventOrGuestFragment.Companion.EVENT_RESULT
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.max


@AndroidEntryPoint
class EventFragment : Fragment(), EventAdapter.OnClickListener, OnMapReadyCallback {

    private lateinit var binding: FragmentEventBinding
    private val viewModel: EventViewModel by viewModels()

    private val adapter = EventAdapter(this)
    private val eventMapViewAdapter = EventMapViewAdapter(this)

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)

        initMap()
        initAdapter()
        initListener()
        initObserver()

        return binding.root
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initAdapter() {
        binding.rvMain.adapter = adapter
        binding.rvMapView.adapter = eventMapViewAdapter
        PagerSnapHelper().attachToRecyclerView(binding.rvMapView)
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            Timber.d("Back")
        }

        binding.ivNewMediaArticle.setOnClickListener {
            viewModel.changeEventStatus()
        }

        binding.rvMapView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position =
                        (binding.rvMapView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    viewModel.setMarkLocation(position)
                }
            }
        })
    }

    private fun initObserver() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
            eventMapViewAdapter.submitList(data)
        }

        viewModel.isEventMapView.observe(viewLifecycleOwner) {
            binding.rvMain.isVisible = !it
            binding.rvMapView.isVisible = it
            binding.map.isVisible = it

            if (it) {
                val color = requireContext().getColor(R.color.yellow)
                binding.ivBack.setImageResource(R.drawable.btn_backarticle)
                binding.tvTitle.setTextColor(color)
                binding.ivSearch.setColorFilter(color)
                binding.ivNewMediaArticle.setImageResource(R.drawable.btn_newmediaarticle)
                binding.separator.setBackgroundColor(color)
            } else {
                val color = requireContext().getColor(R.color.green)
                binding.ivBack.setImageResource(R.drawable.btn_backarticle_green)
                binding.tvTitle.setTextColor(color)
                binding.ivSearch.setColorFilter(color)
                binding.ivNewMediaArticle.setImageResource(R.drawable.btn_newmediaarticle_green)
                binding.separator.setBackgroundColor(color)
            }
        }

        viewModel.markerLocation.observe(viewLifecycleOwner) { locations ->
            mMap.clear()
            for (location in locations) {
                val iconGenerator = IconGenerator(requireContext())
                val inflatedView = View.inflate(requireContext(), R.layout.map_marker, null)
                (inflatedView as TextView).text = location.name
                if (location.isSelected) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location.latLng, 17f))
                    iconGenerator.setStyle(IconGenerator.STYLE_GREEN)
                } else {
                    iconGenerator.setStyle(IconGenerator.STYLE_ORANGE)
                }
                iconGenerator.setContentView(inflatedView)
                mMap.addMarker(
                    MarkerOptions().position(location.latLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon()))
                )
            }
        }
    }

    override fun onClick(name: String) {
        setFragmentResult(EVENT_RESULT, Bundle().also {
            it.putString(EVENT_NAME, name)
        })
        findNavController().popBackStack()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val position =
            (binding.rvMapView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        viewModel.setMarkLocation(max(0, position))
    }
}