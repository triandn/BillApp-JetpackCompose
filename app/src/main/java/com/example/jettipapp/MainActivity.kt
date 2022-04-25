package com.example.jettipapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.ui.theme.JetTipAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val total = remember {
                mutableStateOf(0f)
            }
            val viewStates = remember{ mutableStateOf(ViewStates())}
            JetTipAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier =  Modifier.padding(10.dp)) {
                        CreatePayInfo(viewStates.value)
                        CreateInputCard(viewStates.value){
                                newValue -> viewStates.value = newValue
                            val tip = viewStates.value.tip
                            val split = viewStates.value.split

                            viewStates.value.total = tip / split
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreatePayInfo(states: ViewStates){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        shape = RoundedCornerShape(corner = CornerSize(13.dp)),
        color = Color(android.graphics.Color.parseColor("#c1a1d6")),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(15.dp)
            ,horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Total Per Person",
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Text(text = "$${if(states.total.isFinite()) states.total.format(2) else 0}",
                modifier = Modifier
                    .padding(13.dp),
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.h3
            )
        }
    }
}
@Composable
fun CreateInputCard(states: ViewStates= ViewStates(), onChange: (ViewStates) -> Unit){
    var sliderPosition by remember{ mutableStateOf(0f)}
    var isOpen by remember { mutableStateOf(false)}
    Card(modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 4.dp
    ) {
        Column(
        ) {
            OutlinedTextField(value = "${states.text}",
                onValueChange = {
                    states.text = it
                    isOpen = it.trim().isNotEmpty()
                    states.tip = if (it.trim().isNotEmpty()) (states.text.toFloat()*(sliderPosition)/100f) else 0f
                    onChange(states)
                },
                label = { Text("Enter Bill") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_attach_money_24), contentDescription = "")}
            )
            if(isOpen){
                Row(modifier = Modifier.padding(5.dp)) {
                    Text(text = "Split",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )

                    Box(modifier = Modifier.fillMaxWidth()){
                        Row(modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(5.dp)
                        ) {
                            OutlinedButton(onClick = { states.split += 1
                                onChange(states)
                                states.total = (states.text.toInt()  / states.split).toFloat()
                            },
                                modifier= Modifier.size(50.dp),
                                border = BorderStroke(1.dp, Color.Black),
                                shape = CircleShape,
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                                elevation = ButtonDefaults.elevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 8.dp,
                                    disabledElevation = 0.dp
                                )
                            ) {
                                Icon(Icons.Default.Add, "add")
                            }
                            Text(text = "${states.split}",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.CenterVertically),
                                style = MaterialTheme.typography.body1
                            )

                            OutlinedButton(onClick = { if(states.split > 1) states.split -= 1
                                onChange(states)
                                states.total = (states.text.toInt()  / states.split).toFloat()
                            },
                                modifier= Modifier.size(50.dp),
                                border = BorderStroke(1.dp, Color.Black),
                                shape = CircleShape,
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                                elevation = ButtonDefaults.elevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 8.dp,
                                    disabledElevation = 0.dp
                                )
                            ) {
                                Image(painter = painterResource(id = R.drawable.ic_baseline_remove_24), contentDescription = "remove")
                            }
                        }
                    }

                }

                Row(modifier = Modifier.padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Tip",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Text(text = "$${(states.tip).format(2)}",
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body1
                    )

//                    Box(modifier = Modifier.fillMaxWidth()){
//                        Row(modifier = Modifier
//                            .align(Alignment.TopEnd)
//                            .padding(15.dp)
//                        ) {
//
//                        }
//                    }
                }
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                    text = "${sliderPosition.toInt()}%",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1)

                Slider(value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        states.tip = states.text.toFloat()*(sliderPosition)/100f
                        onChange(states)
                    },
                    steps = 19,
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colors.secondary,
                        activeTrackColor = MaterialTheme.colors.secondary
                    ),
                    modifier = Modifier.padding(10.dp,2.dp)
                )
            }
        }
    }
}

class ViewStates(){
    var text: String by mutableStateOf("")
    var split: Int  by mutableStateOf(1)
    var tip: Float by mutableStateOf(0f)
    var total: Float by mutableStateOf(0f)
}

fun Float.format(digits: Int) = "%.${digits}f".format(this)

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTipAppTheme {
        Greeting("Android")
    }
}