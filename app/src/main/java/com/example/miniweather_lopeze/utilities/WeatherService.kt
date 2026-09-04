package com.example.miniweather_lopeze.utilities

import android.content.Context
import com.example.miniweather_lopeze.R
import com.example.miniweather_lopeze.domain.Weather

class WeatherService(private val context: Context) {
    val weatherStates = arrayOf(
        context.getString(R.string.snowy),
        context.getString(R.string.windy),
        context.getString(R.string.stormy),
        context.getString(R.string.rainy),
        context.getString(R.string.cloudy),
        context.getString(R.string.sunny)
    )

    fun getCities(): Array<String>{
        return arrayOf("Ciudad de México", "Londres", "Paris", "Guadalajara", "Ciudad Obregón")
    }

    fun generateWeather(): Weather{
        val temp = (-15..50).random()
        val weatherIndex = when(temp){
            in -15..0 -> 0
            in 1..18 -> (1..4).random()
            in 19..25 -> (4..5).random()
            else -> 5
        }
        return Weather(temp, weatherStates[weatherIndex])
    }

    fun getWeather(city: String): Weather {
        return generateWeather()
    }
}