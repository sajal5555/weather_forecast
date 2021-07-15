package com.assignment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assignment.R
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.activity_weather_setting.*
import kotlinx.android.synthetic.main.activity_weather_setting.toolbar


/**
 * @author Sajal Jain
 * @version 1.0
 * @since 13.07.2021
 */
class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_help)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Help"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        webview.loadUrl("file:///android_asset/help.html")

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}