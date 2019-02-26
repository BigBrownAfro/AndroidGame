package com.example.jacobgraves.myapplication.model

import android.graphics.RectF
import com.example.jacobgraves.myapplication.R
import kotlin.math.*

class Player{
    var name :String
    private var healthValue :Int = 0
    private var attackValue :Int = 0
    private var movementSpeed :Float = 0.0f
    var accelerationX:Float = 0.0f
    var accelerationY:Float = 0.0f
    private var xPosition :Float = 0.0f
    private var yPosition :Float = 0.0f
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
    var hitBox: RectF
    var maxReload = 20
    var reloadTime = 0;

    constructor(characterName: String){
        name = characterName
        if (name == "Dead Guy"){
            setHealthValue(-100)
            setAttackValue(1000)
            setMovementSpeed(1.0f)
            setHeight(400)
            setWidth(200)
        }else if(name == "Reggie"){
            setHealthValue(3)
            setAttackValue(1)
            setMovementSpeed(1.5f)
            setHeight(100)
            setWidth(50)
        }else if(name == "Frank"){
            setHealthValue(2)
            setAttackValue(2)
            setMovementSpeed(2.0f)
            setHeight(400)
            setWidth(200)
        }
        else if(name == "Psychomantis") {
            setHealthValue(1)
            setAttackValue(3)
            setMovementSpeed(1.5f)
            setHeight(400)
            setWidth(200)
        }
        setXPosition(300f)
        setYPosition(300f)
        bulletArray = Array(500){null}
        hitBox = RectF(getXPosition()-getWidth()/2,getYPosition()-getHeight()/2,getXPosition()+getWidth()/2,getYPosition()+getHeight()/2)

        assignPictures(characterName)
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

    fun assignPictures(name:String){
        if(name.equals("Reggie")){
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
        }else{
            image = R.drawable.isaac
            moveLeftAnimationSet = IntArray(1)
            moveLeftAnimationSet[0] = R.drawable.isaac
            moveRightAnimationSet = IntArray(1)
            moveRightAnimationSet[0] = R.drawable.isaac
            moveUpAnimationSet = IntArray(1)
            moveUpAnimationSet[0] = R.drawable.isaac
            moveDownAnimationSet = IntArray(1)
            moveDownAnimationSet[0] = R.drawable.isaac
        }
    }

    fun moveUp(){
        accelerationY -= movementSpeed;
        if (accelerationY < (-1f) * 7f * movementSpeed){
            accelerationY = (-1f) * 7f * movementSpeed
        }
        yPosition += accelerationY;
    }

    fun moveDown(){
        accelerationY += movementSpeed;
        if (accelerationY > 7f * movementSpeed){
            accelerationY = 7f * movementSpeed
        }
        yPosition += accelerationY;
    }

    fun moveLeft(){
        accelerationX -= movementSpeed;
        if (accelerationX < (-1f) * 7f * movementSpeed){
            accelerationX = (-1f) * 7f * movementSpeed
        }
        xPosition += accelerationX;
    }

    fun moveRight(){
        accelerationX += movementSpeed;
        if (accelerationX > 7f * movementSpeed){
            accelerationX = 7f * movementSpeed
        }
        xPosition += accelerationX;
    }

    fun move(angle:Float){
        //xPosition += 4f * movementSpeed * cos(angle)
        //yPosition -= 4f * movementSpeed * sin(angle)
        /*
        if(movementSpeed * cos(angle) > 0 && accelerationX < 0){
            accelerationX = 0f
        }
        if(movementSpeed * cos(angle) < 0 && accelerationX > 0){
            accelerationX = 0f
        }
        if(movementSpeed * sin(angle) > 0 && accelerationY < 0){
            accelerationX = 0f
        }
        if(movementSpeed * sin(angle) > 0 && accelerationY > 0){
            accelerationX = 0f
        }*/

        accelerationX += movementSpeed * cos(angle)
        accelerationY -= movementSpeed * sin(angle)

        if(hypot(xPosition,yPosition) > 4f * movementSpeed){
            var newAngle = acos(accelerationX/ hypot(accelerationX,accelerationY))
            if(accelerationY > 0){
                newAngle *= -1f
            }
            accelerationX = 4f * movementSpeed * cos(angle)
            accelerationY = -4f * movementSpeed * sin(angle)
        }

        xPosition += accelerationX
        yPosition += accelerationY

    }

    fun decelerate(){
        accelerationX *= .9f
        accelerationY *= .9f
        if(accelerationX < .1f && accelerationX > -.1f){
            accelerationX = 0f
        }
        if(accelerationY < .1f && accelerationY > -.1f){
            accelerationY = 0f
        }
        xPosition += accelerationX
        yPosition += accelerationY
        /*if (accelerationX < 0) {
            accelerationX += movementSpeed
        }else {if (accelerationX > 0){
            accelerationX -= movementSpeed
        }}
        if (accelerationY < 0){
            accelerationY += movementSpeed
        }else {if (accelerationY > 0){
            accelerationY -= movementSpeed
        }}*/
    }

    fun shoot(direction:String){
        if(reloadTime <= 0){
            if (bulletCounter >= bulletArray.size){
                bulletCounter = 0
            }
            bulletArray[bulletCounter] = Bullet(this,direction)
            bulletCounter++
            reloadTime = maxReload
        }
        reloadTime--
    }

    fun updateAnimations(){
        if(accelerationX > 0){
            if (animationCounter >= moveRightAnimationSet.size){
                animationCounter = 0
            }
            image = moveRightAnimationSet[animationCounter]
            lastDirection ="right"
            animationCounter += 1
        }else
        if(accelerationX < 0){
            if (animationCounter >= moveLeftAnimationSet.size){
                animationCounter = 0
            }
            image = moveLeftAnimationSet[animationCounter]
            lastDirection ="left"
            animationCounter += 1
        }else
        if(accelerationY < 0){
            if (animationCounter >= moveUpAnimationSet.size){
                animationCounter = 0
            }
            image = moveUpAnimationSet[animationCounter]
            lastDirection ="up"
            animationCounter += 1
        }else
        if(accelerationY > 0){
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

    fun moveBullets(){
        for (bullet in bulletArray){
            if (bullet != null){
                if(!bullet.isAlive){
                    bulletArray[bulletArray.indexOf(bullet)] = null
                }else{
                    bullet.move()
                    if(bullet.xPosition > 2200 || bullet.xPosition < -100 || bullet.yPosition > 1200 || bullet.yPosition < -100){
                        bullet.isAlive = false
                    }
                }
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

    fun updateHitbox(){
        hitBox = RectF(getXPosition()-getWidth()/2,getYPosition()-getHeight()/2,getXPosition()+getWidth()/2,getYPosition()+getHeight()/2)
    }

    fun updateBulletHitboxes(){
        var count = 0
        for (bullet in bulletArray) {
            if (bullet != null) {
                bullet.updateHitbox()
            }
            count++
        }
    }
}