package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.R

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

    constructor(){
        setHealthValue(5)
        setAttackValue(1)
        setMovementSpeed(1.0f)
        setXPosition(1000f)
        setYPosition(300f)
        setWidth(100)
        setHeight(200)

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
}