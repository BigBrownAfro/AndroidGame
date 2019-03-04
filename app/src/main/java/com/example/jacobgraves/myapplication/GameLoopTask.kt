package com.example.jacobgraves.myapplication

import android.graphics.Canvas
import java.util.*

class GameLoopTask(private val gameView:TestGameView):TimerTask() {
    private var start:Long = 0

    override fun run() {

        gameView.update()

        //gameView.draw(gameView.holder.)

        canvas = null

        start = System.currentTimeMillis()
        canvas = gameView.holder.lockCanvas()
        println(System.currentTimeMillis()-start)

        synchronized(gameView.holder) {
            gameView.draw(canvas!!)
        }

        gameView.holder.unlockCanvasAndPost(canvas)
    }

    companion object {
        private var canvas: Canvas? = null
    }
}