package com.example.jacobgraves.myapplication.model

import android.graphics.RectF
import com.example.jacobgraves.myapplication.R
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.asin
import kotlin.math.hypot

abstract class Enemy{
    var name = "Enemy"
    private var healthValue :Int = 0
    private var attackValue :Int = 0
    private var movementSpeed :Float = 0.0f
    var accelerationX:Float = 0.0f
    var accelerationY:Float = 0.0f
    private var xPosition :Float = 0f
    private var yPosition :Float = 0f
    private var width:Int = 0
    private var height:Int = 0
    var image = R.drawable.isaac
    lateinit var moveLeftAnimationSet:IntArray
    lateinit var moveRightAnimationSet:IntArray
    lateinit var moveUpAnimationSet:IntArray
    lateinit var moveDownAnimationSet:IntArray
    var animationCounter:Int = 0
    var lastDirection = "down"
    var bulletArray:Array<Bullet?>
    var bulletCounter = 0
    var sensorRadius = 300

    constructor(){
        setHealthValue(5)
        setAttackValue(1)
        setMovementSpeed(1.0f)
        setXPosition(1000f)
        setYPosition(500f)
        setWidth(80)
        setHeight(160)
        bulletArray = Array(100){null}

        assignImages()
    }


    //Setters----------------------------
    fun setHealthValue(x: Int){
        if (x < 0){
            healthValue = 0
        }else {
            healthValue = x
        }
    }

    fun setAttackValue(x: Int){
        if (x < 0){
            attackValue = 0
        }else{
            attackValue = x
        }
    }

    fun setMovementSpeed(x: Float){
        if (x < 0){
            movementSpeed = 0.0f
        }else{
            movementSpeed = x
        }
    }

    fun setXPosition(x: Float){
        if (x < 0){
            xPosition = 0.0f
        }else{
            xPosition = x
        }
    }

    fun setYPosition (x: Float){
        if (x < 0){
            yPosition = 0.0f
        }else{
            yPosition = x
        }
    }

    fun setWidth (x: Int){
        if (x < 0){
            width = 100
        }else{
            width = x
        }
    }

    fun setHeight (x: Int){
        if (x < 0){
            height = 200
        }else{
            height = x
        }
    }



    //Getters----------------------------
    fun getHealthValue(): Int{
        return healthValue
    }

    fun getAttackValue(): Int{
        return healthValue
    }

    fun getMovementSpeed(): Float{
        return movementSpeed
    }

    fun getXPosition(): Float{
        return xPosition
    }

    fun getYPosition (): Float{
        return yPosition
    }

    fun getWidth (): Int{
        return width
    }

    fun getHeight (): Int{
        return height
    }


//Other Stuff------------------------

    fun inRadius(player:Player): Boolean{
        var inRadius = false
        var xDifference = player.getXPosition() - xPosition
        xDifference = xDifference.absoluteValue
        var yDifference = yPosition - player.getYPosition()
        yDifference = yDifference.absoluteValue
        var hypotenuse = hypot(xDifference, yDifference)
        if(hypotenuse <= sensorRadius){
            inRadius = true
        }

        /*
        //If enemy is to the Left of the player and enemy is below the player
        if(getMidX() < player.getMidX() && getMidY() > player.getMidY()){
            var xDifference = player.getMidX() - getMidX()
            var yDifference = getMidY() - player.getMidY()
            var hypotenuse = hypot(xDifference, yDifference)
            if(hypotenuse <= sensorRadius){
                inRadius = true
            }
            var angle = asin(yDifference/hypotenuse)
        }
        //If enemy is to the Right of the player and enemy is below the player
        if(getMidX() > player.getMidX() && getMidY() > player.getMidY()){
            var xDifference = getMidX() - player.getMidX()
            var yDifference = getMidY() - player.getMidY()
            var hypotenuse = hypot(xDifference, yDifference)
            if(hypotenuse <= sensorRadius){
                inRadius = true
            }
            //var angle = asin(yDifference/hypotenuse)
        }
        //If enemy is to the Right of the player and enemy is above the player
        if(getMidX() > player.getMidX() && getMidY() < player.getMidY()){
            var xDifference = player.getMidX() - getMidX()
            var yDifference = getMidY() - player.getMidY()
            var hypotenuse = hypot(xDifference, yDifference)
            if(hypotenuse <= sensorRadius){
                inRadius = true
            }
            //var angle = asin(yDifference/hypotenuse)
        }
        //If enemy is to the Left of the player and enemy is above the player
        if(getMidX() < player.getMidX() && getMidY() < player.getMidY()){
            var xDifference = player.getMidX() - getMidX()
            var yDifference = getMidY() - player.getMidY()
            var hypotenuse = hypot(xDifference, yDifference)
            if(hypotenuse <= sensorRadius){
                inRadius = true
            }
            //var angle = asin(yDifference/hypotenuse)
        }
        */
        return inRadius
    }

    fun assignImages(){
        image = R.drawable.mario_peace
        moveLeftAnimationSet = IntArray(3)
        moveLeftAnimationSet[0] = R.drawable.mario_stand
        moveLeftAnimationSet[1] = R.drawable.mario_run_1
        moveLeftAnimationSet[2] = R.drawable.mario_run_2
        moveRightAnimationSet = IntArray(3)
        moveRightAnimationSet[0] = (-1) * R.drawable.mario_stand
        moveRightAnimationSet[1] = (-1) * R.drawable.mario_run_1
        moveRightAnimationSet[2] = (-1) * R.drawable.mario_run_2
        moveUpAnimationSet = IntArray(4)
        moveUpAnimationSet[0] = R.drawable.mario_run_up_1
        moveUpAnimationSet[1] = R.drawable.mario_run_up_2
        moveUpAnimationSet[2] = R.drawable.mario_run_up_1
        moveUpAnimationSet[3] = R.drawable.mario_run_up_3
        moveDownAnimationSet = IntArray(4)
        moveDownAnimationSet[0] = R.drawable.mario_face_forward
        moveDownAnimationSet[1] = R.drawable.mario_run_down_1
        moveDownAnimationSet[2] = R.drawable.mario_face_forward
        moveDownAnimationSet[3] = R.drawable.mario_run_down_2
    }

    fun moveUp(){
        accelerationY -= 2f * movementSpeed;
        if (accelerationY < (-1f) * 7f * movementSpeed){
            accelerationY = (-1f) * 7f * movementSpeed
        }
        yPosition += accelerationY;
    }

    fun moveDown(){
        accelerationY += 2f * movementSpeed;
        if (accelerationY > 7f * movementSpeed){
            accelerationY = 7f * movementSpeed
        }
        yPosition += accelerationY;
    }

    fun moveLeft(){
        accelerationX -= 2f * movementSpeed;
        if (accelerationX < (-1f) * 7f * movementSpeed){
            accelerationX = (-1f) * 7f * movementSpeed
        }
        xPosition += accelerationX;
    }

    fun moveRight(){
        accelerationX += 2f * movementSpeed;
        if (accelerationX > 7f * movementSpeed){
            accelerationX = 7f * movementSpeed
        }
        xPosition += accelerationX;
    }

    fun decelerate(){
        if (accelerationX < 0) {
            accelerationX += movementSpeed
        }else {if (accelerationX > 0){
            accelerationX -= movementSpeed
        }}
        if (accelerationY < 0){
            accelerationY += movementSpeed
        }else {if (accelerationY > 0){
            accelerationY -= movementSpeed
        }}
    }

    fun updateAnimations(){
        if(accelerationX > 0f){
            if (animationCounter >= moveRightAnimationSet.size){
                animationCounter = 0
            }
            image = moveRightAnimationSet[animationCounter]
            lastDirection ="right"
            animationCounter += 1
        }else
        if(accelerationX < 0f){
            if (animationCounter >= moveLeftAnimationSet.size){
                animationCounter = 0
            }
            image = moveLeftAnimationSet[animationCounter]
            lastDirection ="left"
            animationCounter += 1
        }else
        if(accelerationY < 0f){
            if (animationCounter >= moveUpAnimationSet.size){
                animationCounter = 0
            }
            image = moveUpAnimationSet[animationCounter]
            lastDirection ="up"
            animationCounter += 1
        }else
        if(accelerationY > 0f){
            if (animationCounter >= moveDownAnimationSet.size){
                animationCounter = 0
            }
            image = moveDownAnimationSet[animationCounter]
            lastDirection ="down"
            animationCounter += 1
        }else
        if(accelerationX == 0f){
            if(lastDirection.equals("up")){
                image = R.drawable.mario_run_up_1
            }else if(lastDirection.equals("down")){
                image = R.drawable.mario_face_forward
            }else if(lastDirection.equals("left")){
                image = R.drawable.mario_stand
            }else if(lastDirection.equals("right")){
                image = R.drawable.mario_stand * (-1)
            }else{
                image = R.drawable.mario_peace
            }
        }
    }

    fun shoot(direction:String){
        if (bulletCounter > 99){
            bulletCounter = 0
        }
        bulletArray[bulletCounter] = Bullet(this,direction)
        bulletCounter++
    }

    fun moveBullets(){
        for (bullet in bulletArray){
            if (bullet != null){
                bullet.move()
            }
        }
    }

    fun updateBulletAnimations(){
        for (bullet in bulletArray){
            if (bullet != null){
                bullet.updateAnimations()
            }
        }
    }
}