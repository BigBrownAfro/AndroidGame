package com.example.jacobgraves.myapplication.model

import android.media.SoundPool
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R

class SoundManager(gameController: GameController) {
    val soundPoolBuilder:SoundPool.Builder
    val soundPool:SoundPool
    val quack:Int
    val pisto1:Int
    val shotgun:Int
    val arrow:Int

    init {
        soundPoolBuilder = SoundPool.Builder().setMaxStreams(10)
        soundPool = soundPoolBuilder.build()
        quack = soundPool.load(gameController, R.raw.quack, 1)
        pisto1 = soundPool.load(gameController, R.raw.pistot1, 1)
        shotgun = soundPool.load(gameController, R.raw.shotgun, 1)
        arrow = soundPool.load(gameController, R.raw.arrow, 1)
    }
}