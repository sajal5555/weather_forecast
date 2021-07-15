package com.assignment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assignment.R
import com.assignment.fragment.WeatherSettingFragment
import kotlinx.android.synthetic.main.activity_weather_setting.*


class WeatherSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_weather_setting)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, WeatherSettingFragment())
            .commit()

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


    }


}