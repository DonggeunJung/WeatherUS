package com.example.weatherus.view

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.weatherus.R
import com.example.weatherus.data.State
import com.example.weatherus.databinding.ActivityMainBinding
import com.example.weatherus.util.RequestPermissionsUtil
import com.example.weatherus.util.Util
import com.example.weatherus.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var weatherViewModel : WeatherViewModel
    private val popupListState: ListPopupWindow by lazy {
        ListPopupWindow(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }
    override fun onStart() {
        super.onStart()
        RequestPermissionsUtil(this).requestLocation()
    }

    private fun init() {
        observeLiveData()
        weatherViewModel.reqStates()

        val pair = readPrefCity()
        if(pair.first.length >= 2 && pair.second.length >= 2) {
            binding.tvState.text = pair.first
            binding.etCity.setText(pair.second)
            reqWetherByName()
        } else {
            getLocation()
        }
    }

    private fun observeLiveData() {
        weatherViewModel.states.observe(this) { states ->
            initStateList(states)
        }

        weatherViewModel.weather.observe(this) { weather ->
            binding.weather = weather
            val c = weather.cToF(293.0)

            // Save weather infomation to SharedPreferences
            val state = binding.tvState.text.toString()
            val city = binding.etCity.text.toString()
            if(!state.isNullOrEmpty() && !city.isNullOrEmpty())
                savePrefCity(state, city)
        }
    }

    private fun initStateList(states: List<State>) {
        binding.tvState.setOnClickListener {
            popupListState.show()
        }

        popupListState.apply {
            width = 400
            anchorView = binding.tvState
            setAdapter(ModuleArrayAdapter(this@MainActivity, R.layout.list_state, states))
        }

        popupListState.setOnItemClickListener { _, _, position, _ ->
            binding.tvState.text = states[position].abbreviation
            popupListState.dismiss()
        }
    }

    fun onBtnWeather(v: View) {
        reqWetherByName()
    }

    private fun reqWetherByName() {
        val state = binding.tvState.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        if(state.isNullOrEmpty() || state.length < 2 || city.isNullOrEmpty() || city.length < 2) {
            Toast.makeText(this, "State & City name should be entered.", Toast.LENGTH_SHORT).show()
            return
        }

        // hide keyboard
        Util.hideKeypad(this, binding.etCity)

        weatherViewModel.reqWeather("$city,$state")
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { success: Location? ->
                success?.let { location ->
                    onAchieveLocation(location.latitude, location.longitude)
                    Log.d(TAG, "${location.latitude}, ${location.longitude}")
                }
            }
            .addOnFailureListener { fail ->
                Log.d(TAG, fail.localizedMessage)
            }
    }

    private fun onAchieveLocation(lat: Double, lon: Double) {
        weatherViewModel.reqWeatherLocation(lat, lon)
    }

    private fun savePrefCity(state: String, city: String) {
        val editor = pref.edit()
        editor.putString(KEY_STATE, state)
        editor.putString(KEY_CITY, city)
        editor.apply()
    }

    private fun readPrefCity(): Pair<String,String> {
        val state = pref.getString(KEY_STATE, "") ?: ""
        val city = pref.getString(KEY_CITY, "") ?: ""
        return Pair(state, city)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == RequestPermissionsUtil.REQUEST_LOCATION)
            getLocation()
    }

    companion object {
        const val KEY_STATE = "state"
        const val KEY_CITY = "city"
    }
}