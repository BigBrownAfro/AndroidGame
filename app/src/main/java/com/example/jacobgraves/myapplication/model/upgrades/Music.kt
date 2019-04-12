package com.example.jacobgraves.myapplication.model.upgrades

import android.media.SoundPool
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.MainMenuController
import com.example.jacobgraves.myapplication.R

class Music(controller: MainMenuController) {
    val soundPoolBuilder: SoundPool.Builder
    val soundPool: SoundPool
    val iNeedAHero:Int

    init {
        soundPoolBuilder = SoundPool.Builder().setMaxStreams(2)
        soundPool = soundPoolBuilder.build()
        iNeedAHero = soundPool.load(controller, R.raw.sick_music_short, 1)
    }
}