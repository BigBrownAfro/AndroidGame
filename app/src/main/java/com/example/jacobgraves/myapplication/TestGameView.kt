package com.example.jacobgraves.myapplication

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.jacobgraves.myapplication.model.Engine
import java.util.*


class TestGameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    //private val thread: TestGameThread
    private val gameEngine:Engine
    private val timer:Timer
    private val updateTask:TimerTask

    init{
        holder.addCallback(this)
        gameEngine = Engine("Reggie",resources)

        //thread = TestGameThread(holder, this)

        timer = Timer()
        updateTask = GameLoopTask(this)
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        timer.cancel()
        Thread.sleep(100)
        /*
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            retry = false
        }
        */
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        timer.scheduleAtFixedRate(updateTask,Date(),16)
        //thread.setRunning(true)
        //thread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    /**
     * Function to update the positions of player and game objects
     */
    fun update() {
        gameEngine.update()
    }

    /**
     * Everything that has to be drawn on Canvas
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        gameEngine.draw(canvas)
    }
}
