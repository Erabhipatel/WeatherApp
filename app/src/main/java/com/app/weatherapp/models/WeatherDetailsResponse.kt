package com.app.weatherapp.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat

data class WeatherDetailsResponse(
    val lat: String,
    val lon: String,
    val timezone: String,
    val timezone_offset: String,
    val current: Current
) {
    //convert temp Fahrenheit To Celsius
    fun fahrenheitToCelsius(temp: Float): Float {
        return DecimalFormat("#.##").format((temp - 32) * 5 / 9).toFloat()
    }


}

@BindingAdapter("icon")
fun loadImage(view: ImageView, url: String?) {
    if (url != null)
        Glide.with(view)
            .load("http://openweathermap.org/img/w/$url.png")
            .into(view)

}
