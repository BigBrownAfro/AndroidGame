package com.example.jacobgraves.myapplication.model

class Engine {
    var player: Player
    var frameCount: Int
    var imageX: Float
    var imageY: Float

    constructor(name:String){
        player = Player(name)
        frameCount = 60
        imageX = 0.0F
        imageY = 0.0F
    }

    fun update(){
        if (frameCount == 1){
            println("It's been 60 frames")
            frameCount = 61
        }
        imageX += 1F
        imageY += 1F
        frameCount -= 1
    }
}