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
    val speed:Int
    val health:Int
    val coin:Int

    init {
        soundPoolBuilder = SoundPool.Builder().setMaxStreams(10)
        soundPool = soundPoolBuilder.build()
        quack = soundPool.load(gameController, R.raw.quack, 1)
        pisto1 = soundPool.load(gameController, R.raw.pistol_shoot, 1)
        shotgun = soundPool.load(gameController, R.raw.shotgun, 1)
        arrow = soundPool.load(gameController, R.raw.arrow, 1)
        speed = soundPool.load(gameController, R.raw.speed_pickup, 1)
        health = soundPool.load(gameController, R.raw.health_pickup, 1)
        coin = soundPool.load(gameController, R.raw.coin_pickup, 1)

    }
}