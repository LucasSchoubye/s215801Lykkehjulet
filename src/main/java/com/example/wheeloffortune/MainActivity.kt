package com.example.wheeloffortune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wheeloffortune.ui.theme.WheelOfFortuneTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //var modelView by remember { mutableStateOf(WheelOfFortuneModel(0, "Black Adam", "_____ \n\n ____")) //modelView.rolled = 1}
            WheelOfFortuneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val seed = (1..4).random()
                    WordDisplayScreen(seed)
                }
            }
        }
    }
}

@Composable
fun WordDisplayScreen(seed: Int) {

    // Set up an initial state
    val modelView by remember { mutableStateOf(
        WheelOfFortuneModel(0,
            "BLACK ADAM",
            arrayOf("B","L","A","C","K","\n","\n","A","D","A","M"),
            "_ _ _ _ _ \n\n _ _ _ _",
            arrayOf("_","_","_","_","_","\n","\n","_","_","_","_"),
            "Fictional Characters",
            "0 kr"
            )
        )
    }
    modelView.setupGame(seed)

    Column( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ){
        DisplayWordAndTitle(modelView = modelView)
        if (modelView.lives.value > 0 && modelView.mutWord.value != modelView.mutHintedWord.value) {
            if (modelView.mutRolled.value == 1) {
                WordGuesser(modelView = modelView)
            } else if (modelView.mutRolled.value == 0) {
                WheelSpinner(modelView = modelView)
            }
        }
        else
        {
            GameOver(modelView = modelView)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 40.dp),
            Arrangement.SpaceEvenly)
        {
            Text(text = "Lives Left: "+modelView.lives.value.toString())
        }
    }
}

@Composable
fun DisplayWordAndTitle (modelView: WheelOfFortuneModel){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 40.dp),
        Arrangement.SpaceEvenly
    ){
        Text(text = "WHEEL OF FORTUNE", fontSize = 30.sp)
    }
    Row(modifier = Modifier
        .fillMaxWidth(),
        Arrangement.SpaceEvenly
    ){
        Text(text = "Category: " + modelView.mutCategory.value, fontSize = 15.sp)
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 0.dp),
        Arrangement.SpaceEvenly
    ){
        Text(text = "The word to guess is:", fontSize = 20.sp)
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 70.dp),
        Arrangement.SpaceEvenly
    ){
        Text(text = modelView.mutHintedWord.value, fontSize = 40.sp)
    }
}

@Composable
fun WheelSpinner (modelView: WheelOfFortuneModel)
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 0.dp),
        Arrangement.SpaceEvenly
    )
    {
        Text(text = "Your Bank: " + modelView.bank.value.toString() + " Kr.")
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 40.dp),
        Arrangement.SpaceEvenly
    )
    {
        OutlinedButton(
            onClick = {
                modelView.mutRolled.value = 1
                modelView.spinWheel()
            }
        ) {
            Text(text = "SPIN THE WHEEL!")
        }
    }
}

@Composable
fun WordGuesser (modelView: WheelOfFortuneModel)
{
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 0.dp),
        Arrangement.SpaceEvenly
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp),
            Arrangement.SpaceEvenly){
            BasicTextField(modifier = Modifier
                .height(20.dp)
                .background(color = Color.LightGray),
                value = textState, onValueChange = {
                textState = it
            })
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            Arrangement.SpaceEvenly){
            //Text("You are guessing: " + textState.text)
            Text("You rolled "+modelView.mutRolledValue.value)
        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 0.dp),
        Arrangement.SpaceEvenly
    )
    {
        if (textState.text.length > 0)
        {
            OutlinedButton(
                onClick = {
                    modelView.mutRolled.value = 0
                    if (textState.text.length > 1)
                    {
                        modelView.quessWord(textState.text)
                    }
                    else
                    {
                        modelView.quessLetter(textState.text)
                    }
                }
            ) {
                if (textState.text.length > 1)
                {
                    Text(text = "Guess this word")
                }
                else
                {
                    Text(text = "Guess this letter")
                }
            }
        }
    }
}

@Composable
fun GameOver (modelView: WheelOfFortuneModel)
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 0.dp),
        Arrangement.SpaceEvenly
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth(),
            Arrangement.SpaceEvenly){

            // Display Text
            if (modelView.lives.value == 0) {
                Text("GAMEOVER YOU LOST")
            }
            else
            {
                Text("YOU WON")
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            Arrangement.SpaceEvenly)
        {
            // Reset Button
            OutlinedButton(onClick = {
                modelView.reset.value = false
                modelView.setupGame((1..5).random())
            })
            {
                Text("Play Again")
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = WheelOfFortuneModel(0,"BLACK ADAM","_____ \n" +
            "\n" +
            " ____")
    WheelOfFortuneTheme {
        //WordDisplayScreen("_ L _ _ K \n\n _ D _ _", "Fictional Character" , "\$1000", viewModel)
    }
}*/