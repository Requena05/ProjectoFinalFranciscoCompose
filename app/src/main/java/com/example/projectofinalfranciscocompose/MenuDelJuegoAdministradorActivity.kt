package com.example.projectofinalfranciscocompose

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import para poder alinear el contenido del Scafold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.A

class MenuDelJuegoAdministradorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                MenuDelAdministrador()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        var islogued: SharedPreferences=getSharedPreferences("comun", 0)
        islogued.edit().putBoolean("comun", true).apply()
        finishAffinity()
    }
}

@Composable
fun MenuDelAdministrador( modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fondo))
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .background(colorResource(R.color.gray))
                        .padding(16.dp)
                        ,
                    drawerContainerColor = colorResource(R.color.black)
                ) {
                    ExtendedFloatingActionButton(
                        text = { Text("Añadir Carta") },
                        icon = {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = " ",
                                modifier = Modifier.width(20.dp).height(30.dp)
                            )
                        },
                        onClick = {

                            scope.launch {
                                val intent=Intent(context,AñadirCartaActivity::class.java)
                                context.startActivity(intent)
                            }
                        },
                    )
                }
            },
            modifier = Modifier.background(colorResource(R.color.fondo))
        ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(colorResource(R.color.fondo))

                ) {
                    var isOpening by remember { mutableStateOf(false) }
                    val openAngle by animateFloatAsState(
                        targetValue = if (isOpening) 180f else 0f,
                        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Tienda",
                            color = Color.Black,
                            fontSize = 40.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                                .background(Color.White)
                                .align(Alignment.CenterHorizontally)
                        )
//                        CardSlider(cardItem())
                    }
                }
            }
        }
        //En esta parte estaran las cartas creadas por los adminitradores ,esas cartas no estaran en la
        //tienda del usuario ,el administrador tendrá que pinchar en las cartas y ver sus detalles para
        //añadirla a la tienda del usuario


    }



@Composable
fun CardSlider(cards: List<Carta>) {
    val coroutineScope = rememberCoroutineScope()
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    coroutineScope.launch {
                        offsetX += dragAmount
                        // Limit the offset to prevent scrolling too far
                        offsetX = offsetX.coerceIn(
                            -size.width.toFloat() * (cards.size - 1),
                            0f
                        )
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = animatedOffsetX.dp)
        ) {
            cards.forEach { card ->
                cardItem()
            }
        }
    }
}

@Composable
fun cardItem():List<Carta> {
    //Aqui recibiremos de la base de datos todos los datos de la carta y la crearemos aqui
    var arrayCarta by remember { mutableStateOf(ArrayList<Carta>()) }
//    var db_ref = FirebaseDatabase.getInstance().reference
    val context = LocalContext.current
//    db_ref.child("Uno").child("Tienda").child("Carta").

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ){

    }
    return arrayCarta
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview4() {
    ProjectoFinalFranciscoComposeTheme {
        MenuDelAdministrador()
    }
}