package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.jacobgraves.myapplication.model.SoundManager
import kotlinx.android.synthetic.main.game_view.*
import kotlinx.android.synthetic.main.settings_view.*

class SettingsController : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_view)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

    }

    }

