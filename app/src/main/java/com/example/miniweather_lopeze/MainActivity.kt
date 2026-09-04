package com.example.miniweather_lopeze

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miniweather_lopeze.utilities.WeatherService
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
    //Aqui se declararon las variables de acuerdo a su id que se le dió en el drawable
    private lateinit var tvTemperature: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var tvCity: TextView
    private lateinit var ivWeather: ImageView
    private lateinit var tvWeather: TextView
    private lateinit var weatherService: WeatherService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Indicar que los íconos de la barra de estado no están sobre un fondo claro
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

        // Inicialización de vistas
        tvTemperature = findViewById(R.id.tvTemperature)
        tvGreeting = findViewById(R.id.tvGreeting)
        tvCity = findViewById(R.id.tvCity)
        ivWeather = findViewById(R.id.ivWeather)
        tvWeather = findViewById(R.id.tvWeather)
        
        weatherService = WeatherService(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        // 1. Obtener la ciudad del Intent
        val citySelected = intent.getStringExtra("city") ?: "Desconocida"
        tvCity.text = citySelected

        // 2. Lógica del saludo según la hora
        val time = LocalTime.now().hour
        val greetingRes = when (time) {
            in 5..11 -> R.string.good_morning
            in 12..19 -> R.string.good_afternoon
            else -> R.string.good_evening
        }
        tvGreeting.text = getString(greetingRes)

        // 3. Obtener el clima desde el servicio
        val weatherInfo = weatherService.getWeather(citySelected)
        
        // 4. Llenar la información
        tvTemperature.text = "${weatherInfo.temperature}°"
        tvWeather.text = weatherInfo.weather

        // 5. Cambiar imagen según el clima
        val imageRes = when (weatherInfo.weather) {
            getString(R.string.sunny) -> R.drawable.ic_sunny
            getString(R.string.cloudy) -> R.drawable.ic_cloudy
            getString(R.string.rainy) -> R.drawable.ic_rainy
            getString(R.string.stormy) -> R.drawable.ic_stormy
            getString(R.string.snowy) -> R.drawable.ic_snowy
            getString(R.string.windy) -> R.drawable.ic_windy
            else -> R.drawable.ic_cloudy
        }
        ivWeather.setImageResource(imageRes)
    }
}
