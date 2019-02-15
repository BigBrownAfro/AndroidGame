package com.example.jacobgraves.myapplication.model

class Player{
    var name :String
    private var healthValue :Int = 0
    private var attackValue :Int = 0
    private var movementSpeed :Double = 0.0
    private var xPosition :Double = 0.0
    private var yPosition :Double = 0.0

    constructor(characterName: String){
        name = characterName
        if (name == "Dead Guy"){
            setHealthValue(-100)
            setAttackValue(1000)
            setMovementSpeed(1.0)
        }else if(name == "Reggie"){
            setHealthValue(3)
            setAttackValue(1)
            setMovementSpeed(1.5)
        }else if(name == "Frank"){
            setHealthValue(2)
            setAttackValue(2)
            setMovementSpeed(2.0)
        }
        else if(name == "Psychomantis") {
            setHealthValue(1)
            setAttackValue(3)
            setMovementSpeed(1.5)
        }
        setXPosition(50.0)
        setYPosition(50.0)

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

    fun setMovementSpeed(x: Double){
        if (x < 0){
            movementSpeed = 0.0
        }else{
            movementSpeed = x
        }
    }

    fun setXPosition(x: Double){
        if (x < 0){
            xPosition = 0.0
        }else{
            xPosition = x
        }
    }

    fun setYPosition (x: Double){
        if (x < 0){
            yPosition = 0.0
        }else{
            yPosition = x
        }
    }


//Getters----------------------------
    fun getHealthValue(): Int{
        return healthValue
    }

    fun getAttackValue(): Int{
        return healthValue
    }

    fun getMovementSpeed(): Double{
        return movementSpeed
    }

    fun getXPosition(): Double{
        return xPosition
    }

    fun getYPosition (): Double{
        return yPosition
    }


}