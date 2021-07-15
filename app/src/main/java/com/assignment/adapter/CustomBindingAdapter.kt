package com.assignment.adapter

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.assignment.R
import com.assignment.activity.CityWeatherInformationActivity
import com.assignment.repository.Constants.PREF_UNIT_SYSTEM
import com.assignment.utilities.DateTimeUtil
import com.assignment.utilities.SharedPrefUtil


@BindingAdapter("setTemp")
fun AppCompatTextView.setTemp(temp: String?) {
    val myResArray = resources.getStringArray(R.array.listArray)
    if (SharedPrefUtil.getSharedPref(PREF_UNIT_SYSTEM) == myResArray[0]) {
        this.text = temp.plus(" ").plus(this.context.getString(R.string.celsius))
    } else {
        this.text = temp.plus(" ").plus(this.context.getString(R.string.fahrenheit))
    }
}

@BindingAdapter("visibility")
fun AppCompatImageButton.visibility(cityName: String?) {
    this.visibility = if (SharedPrefUtil.bookmarkAlreadyStored(cityName))
        View.INVISIBLE
    else View.VISIBLE


}

@BindingAdapter("convertDate")
fun AppCompatTextView.convertDate(milliTime: Int) {
    this.text = DateTimeUtil.getDate(milliTime)
}

@BindingAdapter("src")
fun AppCompatImageView.src(iconId: String?) {
    when (iconId) {
        "01d" -> {
            this.setImageResource(R.drawable.ic_weather_clear_sky)
        }
        "01n" -> {
            this.setImageResource(R.drawable.ic_weather_clear_sky)
        }
        "02d" -> {
            this.setImageResource(R.drawable.ic_weather_few_cloud)
        }
        "02n" -> {
            this.setImageResource(R.drawable.ic_weather_few_cloud)
        }
        "03d" -> {
            this.setImageResource(R.drawable.ic_weather_scattered_clouds)
        }
        "03n" -> {
            this.setImageResource(R.drawable.ic_weather_scattered_clouds)
        }
        "04d" -> {
            this.setImageResource(R.drawable.ic_weather_broken_clouds)
        }
        "04n" -> {
            this.setImageResource(R.drawable.ic_weather_broken_clouds)
        }
        "09d" -> {
            this.setImageResource(R.drawable.ic_weather_shower_rain)
        }
        "09n" -> {
            this.setImageResource(R.drawable.ic_weather_shower_rain)
        }
        "10d" -> {
            this.setImageResource(R.drawable.ic_weather_rain)
        }
        "10n" -> {
            this.setImageResource(R.drawable.ic_weather_rain)
        }
        "11d" -> {
            this.setImageResource(R.drawable.ic_weather_thunderstorm)
        }
        "11n" -> {
            this.setImageResource(R.drawable.ic_weather_thunderstorm)
        }
        "13d" -> {
            this.setImageResource(R.drawable.ic_weather_snow)
        }
        "13n" -> {
            this.setImageResource(R.drawable.ic_weather_snow)
        }
        "15d" -> {
            this.setImageResource(R.drawable.ic_weather_mist)
        }
        else -> {
            this.setImageResource(R.drawable.ic_weather_mist)
        }
    }
}

@BindingAdapter("onClick")
fun ConstraintLayout.onClick(cityName: String) {
    this.setOnClickListener { view ->

        val intent = Intent(view.context, CityWeatherInformationActivity::class.java)
        intent.putExtra("cityName", cityName)
        view.context.startActivity(intent)
    }
}