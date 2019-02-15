package com.example.jacobgraves.myapplication.model

class Engine {
    var player: Player

    constructor(name:String){
        player = Player(name)
    }

    fun update(){
        println(player.name)
    }

}