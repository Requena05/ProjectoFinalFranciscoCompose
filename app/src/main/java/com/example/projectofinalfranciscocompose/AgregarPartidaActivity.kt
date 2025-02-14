package com.example.projectofinalfranciscocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Text(
                text = "Listado de Partidas",
                modifier = Modifier.padding(innerPadding)
            )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview7() {
    ProjectoFinalFranciscoComposeTheme {
        ListadoPartidas()
    }
}