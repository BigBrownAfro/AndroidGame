package com.example.jacobgraves.myapplication

class GameLoop(val gameController:GameController) : Thread() {
    private var running: Boolean = false
    private val targetFPS = 60
    var updateStart: Long = 0
    var updateTime: Long = 0
    var longestUpdateTime: Long = 0
    var frameSpaceStart1 : Long = 0
    var frameSpaceStart2 : Long = 0
    var frameSpace : Long = 0
    var longestFrameSpace: Long = 0

    fun setRunning(isRunning: Boolean) {
        this.running = isRunning
    }

    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long

        val targetTime = (1000 / targetFPS).toLong()
        println("target time" + targetTime)

        while (running) {
            startTime = System.nanoTime()
            frameSpaceStart2 = System.currentTimeMillis()

            frameSpace = (frameSpaceStart2 - frameSpaceStart1)
            if(frameSpace > longestFrameSpace && frameSpaceStart1 != 0.toLong()){
                longestFrameSpace = frameSpace
            }

            frameSpaceStart1 = System.currentTimeMillis()

            updateStart = System.currentTimeMillis()
            gameController.update()
            updateTime = System.currentTimeMillis() - updateStart

            if (updateTime > longestUpdateTime){
                longestUpdateTime = updateTime
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

            println(waitTime)

            try {
                sleep(waitTime - 1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}