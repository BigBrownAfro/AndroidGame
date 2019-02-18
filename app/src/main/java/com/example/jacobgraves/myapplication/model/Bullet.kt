package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.R

class Bullet {
    var attackValue:Int
    var isAlive:Boolean
    var friendly:Boolean
    var image = R.drawable.bullet
    lateinit var animationSet:IntArray
    var size:Float
    var type:String
    var xPosition:Float
    var yPosition:Float
    var movementSpeed:Float
    var direction:String
    var animationCounter = 0

    constructor(player:Player, bulletDirection:String){
        attackValue = 1
        isAlive = true
        friendly = true
        size = 1f
        type = "regular"
        movementSpeed = 3f
        xPosition = player.getXPosition()
        yPosition = player.getYPosition()
        direction = bulletDirection


        assignPictures()
    }

    constructor(bulletType:String, player:Player, bulletDirection:String){
        attackValue = 1
        isAlive = true
        friendly = true
        size = 1f
        type = bulletType
        movementSpeed = 3f
        xPosition = player.getXPosition()
        yPosition = player.getYPosition()
        direction = bulletDirection


        assignPictures()
    }

    constructor(enemy:Enemy, bulletDirection:String){
        attackValue = 1
        isAlive = true
        friendly = false
        size = 1f
        type = "regular"
        movementSpeed = 3f
        xPosition = enemy.getXPosition()
        yPosition = enemy.getYPosition()
        direction = bulletDirection


        assignPictures()
    }

    constructor(bulletType:String, enemy:Enemy, bulletDirection:String){
        attackValue = 1
        isAlive = true
        friendly = false
        size = 1f
        type = bulletType
        movementSpeed = 3f
        xPosition = enemy.getXPosition()
        yPosition = enemy.getYPosition()
        direction = bulletDirection


        assignPictures()
    }

    fun assignPictures(){
        if (type.equals("regular")){
            animationSet = IntArray(3)
            animationSet[0] = R.drawable.bullet_animation_1
            animationSet[1] = R.drawable.bullet_animation_2
            animationSet[2] = R.drawable.bullet_animation_3
        }else{
            animationSet = IntArray(3)
            animationSet[0] = R.drawable.bullet_animation_1
            animationSet[1] = R.drawable.bullet_animation_2
            animationSet[2] = R.drawable.bullet_animation_3
        }
    }

    fun move(){
        if(direction == "up"){
            yPosition -= movementSpeed
        }else if(direction == "down"){
            yPosition += movementSpeed
        }else if(direction == "left"){
            xPosition -= movementSpeed
        }else if(direction == "right"){
            xPosition += movementSpeed
        }
    }

}