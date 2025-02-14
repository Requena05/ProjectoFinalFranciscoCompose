package com.example.projectofinalfranciscocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme

class AgregarPartidaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                ListadoPartidas()
            }
        }
    }
}

@Composable
fun ListadoPartidas( modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.background(colorResource(R.color.fondo)).fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding()
                .background(colorResource(R.color.fondo2))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Crear Partidas",
                    color = Color.Black,
                    fontSize = 33.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .wrapContentWidth()
                        .wrapContentHeight()
                )

            }

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                //Pondremos una imagen centrar que tendra el icono de partida
                Image(ImageBitmap.imageResource(R.drawable.partida), contentDescription = "partida", modifier = Modifier.size(200.dp)))

            }

            }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview7() {
    ProjectoFinalFranciscoComposeTheme {
        ListadoPartidas()
    }
}