package com.example.wheeloffortune

import androidx.compose.runtime.*

data class WheelOfFortuneModel (
    var rolled: Int,
    var word: String,
    var wordList: Array<String>,
    var hintedWord: String,
    var wordHintList: Array<String>,
    var category: String,
    var rolledValue: String
)
{
    var mutCategory = mutableStateOf(category)
    var mutRolled = mutableStateOf(rolled)
    var mutRolledValue = mutableStateOf(rolledValue)
    var mutRolledInt = mutableStateOf(0)
    var mutWord = mutableStateOf(word)
    var mutHintedWord = mutableStateOf(hintedWord)
    var bank = mutableStateOf(0)
    var lives = mutableStateOf(5)
    var reset = mutableStateOf(false)

    // Word array
    var mutWordList = mutableStateOf(wordList)
    var mutWordHintList = mutableStateOf(wordHintList)

    // Create Word
    fun setupGame(seed: Int)
    {
        if (reset.value == false)
        {
            reset.value = true

            // Reset
            lives.value = 5
            bank.value = 0

            when(seed){
                1 -> {
                    mutCategory.value = "Fictional Character"
                    mutWord.value = "BLACK ADAM"
                    mutHintedWord.value = "_ _ _ _ _ \n\n _ _ _ _"
                    mutWordList.value = arrayOf("B","L","A","C","K","\n","\n","A","D","A","M")
                    mutWordHintList.value =arrayOf("_","_","_","_","_","\n","\n","_","_","_","_")
                }
                2 -> {
                    mutCategory.value = "Fictional Character"
                    mutWord.value = "SHERLOCK HOLMES"
                    mutHintedWord.value = "_ _ _ _ _ _ _ _ \n\n _ _ _ _ _ _"
                    mutWordList.value = arrayOf("S","H","E","R","L","O","C","K","\n","\n","H","O","L","M","E","S")
                    mutWordHintList.value =arrayOf("_","_","_","_","_","_","_","_","\n","\n","_","_","_","_","_","_")
                }
                3 -> {
                    mutCategory.value = "Fictional Character"
                    mutWord.value = "CAPTAIN AMERICA"
                    mutHintedWord.value = "_ _ _ _ _ _ _ _ \n\n _ _ _ _ _ _"
                    mutWordList.value = arrayOf("C","A","P","T","A","I","N","\n","\n","A","M","E","R","I","C","A")
                    mutWordHintList.value =arrayOf("_","_","_","_","_","_","_","\n","\n","_","_","_","_","_","_","_")
                }
                4 -> {
                    mutCategory.value = "Software Terms"
                    mutWord.value = "ALGORITHM"
                    mutHintedWord.value = "_ _ _ _ _ _ _ _ _"
                    mutWordList.value = arrayOf("A","L","G","O","R","I","T","H","M")
                    mutWordHintList.value =arrayOf("_","_","_","_","_","_","_","_","_")
                }
                5 -> {
                    mutCategory.value = "Software Terms"
                    mutWord.value = "HASHMAPPING"
                    mutHintedWord.value = "_ _ _ _ _ _ _ _ _ _ _"
                    mutWordList.value = arrayOf("H","A","S","H","M","A","P","P","I","N","G")
                    mutWordHintList.value =arrayOf("_","_","_","_","_","_","_","_","_","_","_")
                }
            }
        }
    }

    fun spinWheel(){
        when((1..5).random()) {
            // Bankrupt
            1 -> {
                bank.value = 0
                mutRolledInt.value = 0
                mutRolledValue.value = "Bankrupt!"
            }
            2 -> {
                mutRolledInt.value = 200
                mutRolledValue.value = "200 kr"
            }
            3 -> {
                mutRolledInt.value = 500
                mutRolledValue.value = "500 kr"
            }
            4 -> {
                mutRolledInt.value = 1000
                mutRolledValue.value = "1000 kr"
            }
            5 -> {
                mutRolledInt.value = 5000
                mutRolledValue.value = "5000 kr"
            }
        }
    }

    fun quessWord(word: String){

        // Change letters if match
        if (word.uppercase() == mutWord.value.uppercase())
        {
            mutHintedWord.value = mutWord.value
        }
        else
        {
            lives.value--
        }
    }

    fun quessLetter(letter: String){
        var quessedRight = false

        // Change letters if match
        for (i in 0..mutWordList.value.size-1) {
            if (letter.uppercase() == mutWordList.value[i].uppercase())
            {
                mutWordHintList.value.set(i,letter.uppercase())
                bank.value += mutRolledInt.value
                quessedRight = true
            }
        }

        // If Match update the graphic word
        if (quessedRight == true) {
            var newHint = ""
            for (i in 0..mutWordHintList.value.size-1) {
                newHint = newHint + mutWordHintList.value[i] + " "
            }
            mutHintedWord.value = newHint
        }
        else
        {
            lives.value--
        }
    }
}