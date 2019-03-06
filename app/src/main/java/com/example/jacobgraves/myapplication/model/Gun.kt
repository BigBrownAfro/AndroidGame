package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R

abstract class Gun (val gameController: GameController){
    var name = "Gun"
    private var damageValue:Int = 1
    private var accuracy:Float = 1f
    private var xPosition:Float = 1f
    private var yPosition:Float = 1f
    private var width:Int = 0
    private var height:Int = 0
    private var currentAmmo:Int = 0
    private var maxAmmo:Int = 0
    var image = R.drawable.testgun
    lateinit var shootAnimation:IntArray
    var animationCounter:Int = 0

    init{
        setMaxAmmo(50)
        setCurrentAmmo(50)
        setDamageValue(1)
        setAccuracy(1.0f)
        setXPosition(1000f)
        setYPosition(500f)
        setWidth(80)
        setHeight(160)
    }

    //Setters----------------------------

    fun setDamageValue(x: Int){
        if (x < 0){
            damageValue = 0
        }else{
            damageValue = x
        }
    }

    fun setMaxAmmo(x:Int){
        if(x < 1){
            maxAmmo = 1
        }else{
            maxAmmo = x
        }
    }

    fun setCurrentAmmo(x:Int){
        if(x < 0){
            currentAmmo = 0
        }else{
            currentAmmo = x
        }
    }

    fun setAccuracy(x: Float){
        if(x<0){
            accuracy = 1f
        }else{
            accuracy = x
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

    fun getDamageValue(): Int{
        return damageValue
    }

    fun getMaxAmmo():Int{
        return maxAmmo
    }

    fun getCurrentAmmo():Int{
        return currentAmmo
    }

    fun getAccuracy(): Float{
        return accuracy
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

    //reload, shoot, setup images, update images.
}