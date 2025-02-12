package com.example.projectofinalfranciscocompose

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(colorResource(R.color.fondo))) {
                        MenuPrincipal(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun MenuPrincipal( modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var sharedPreferences:SharedPreferences
    sharedPreferences = context.getSharedPreferences("comun", 0)
    var islogued=sharedPreferences.getBoolean("islogued",false)
    var tipo=sharedPreferences.getInt("tipo",0)

    //por defecto islogued es falso pero se tiene que cambiar a true cuando se inicie sesion


    Row (
            modifier = Modifier
               .fillMaxSize()){
            Column{
                //tiene que ir un boton invisible debajo de la imagen
                IconButton(onClick = {
                    Log.d("islogued", islogued.toString())
                    if (islogued && tipo == 2) {
                        val intent = Intent(context, MenuDelJuegoAdministradorActivity::class.java)
                        context.startActivity(intent)
                    }else if (islogued &&tipo == 1) {
                        val intent = Intent(context, MenuDelJuegoUsuarioActivity::class.java)
                        context.startActivity(intent)

                }else{
                        val intent = Intent(context, RegistroActivity::class.java)
                        context.startActivity(intent)


                    }

                },
                modifier = Modifier.fillMaxSize()) {
                    Image(
                    ImageBitmap.imageResource(id = R.drawable.uno), contentDescription = "",
                    modifier
                        .fillMaxSize()
                ) }

            }
        }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenuPrincipalPreview() {
    ProjectoFinalFranciscoComposeTheme {
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .fillMaxSize()) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.fondo))) {
                MenuPrincipal(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}