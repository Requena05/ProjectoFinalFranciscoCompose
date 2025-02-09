package com.example.projectofinalfranciscocompose

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectofinalfranciscocompose.R.drawable.cartauno
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme

class AñadirCartaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CrearCarta(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CrearCarta(modifier: Modifier = Modifier) {
    Column(modifier =Modifier
        .fillMaxSize()
        .background(colorResource(R.color.fondo))) {
        Text(
            text = "Creacion de Carta"
            , modifier = modifier
                //tiene que estar al centro
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
                .border(2.dp, Color.Black)
                .wrapContentWidth()
                .padding(16.dp)
                ,
            fontSize = 22.sp,
        )
        //HAZ esta imagen invisible
        var posiblecartas:MutableList<ImageBitmap>
        posiblecartas= mutableListOf()
        posiblecartas.add(ImageBitmap.imageResource(cartauno))
        posiblecartas.add(ImageBitmap.imageResource(R.drawable.cartaunoazul))
        posiblecartas.add(ImageBitmap.imageResource(R.drawable.cartaunoamarilla))
        posiblecartas.add(ImageBitmap.imageResource(R.drawable.cartaunoverde))

            var currentIndex by remember { mutableStateOf(0) }
            Image(
                posiblecartas[currentIndex], contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(300.dp)
                    .width(185.dp)
                    .border(2.dp, Color.Black)

            )
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {


                if (posiblecartas.isEmpty()) {
                    Text("No items to display")

                } else {
                    fun moveBackward() {
                        currentIndex =
                            if (currentIndex > 0) currentIndex - 1 else posiblecartas.size - 1
                    }

                    fun moveForward() {
                        currentIndex =
                            if (currentIndex < posiblecartas.size - 1) currentIndex + 1 else 0
                    }
                    Button(
                        onClick = { moveBackward() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Previous"
                        )
                    }

                    Button(
                        onClick = { moveForward() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next"
                        )
                    }

                }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview5() {
    ProjectoFinalFranciscoComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CrearCarta(modifier = Modifier.padding(innerPadding))
        }
    }
}