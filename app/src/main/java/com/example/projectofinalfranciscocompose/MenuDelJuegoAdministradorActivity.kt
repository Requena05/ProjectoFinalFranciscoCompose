package com.example.projectofinalfranciscocompose

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.A
import androidx.core.content.edit

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
        val context = applicationContext
        var sharedPreferences:SharedPreferences
        sharedPreferences = context.getSharedPreferences("comun", 0)
        sharedPreferences.edit().putBoolean("comun",true).apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finishAffinity()
    }
}

@Composable
fun MenuDelAdministrador(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .background(colorResource(R.color.gray))
                    .padding(16.dp),
                drawerContainerColor = colorResource(R.color.black)
            ) {
                ExtendedFloatingActionButton(
                    text = { Text("Añadir Carta") },
                    icon = {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = " ",
                            modifier = Modifier
                                .width(20.dp)
                                .height(30.dp)
                        )
                    },
                    onClick = {
                        scope.launch {
                            val intent = Intent(context, AñadirCartaActivity::class.java)
                            context.startActivity(intent)
                        }
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                ExtendedFloatingActionButton(
                    text = { Text("Cerrar Sesion") },
                    icon = {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = " ",
                            modifier = Modifier
                                .width(20.dp)
                                .height(30.dp)
                        )
                    },
                    onClick = {
                        scope.launch {
                            val sharedPreferences = context.getSharedPreferences("comun", 0)
                            sharedPreferences.edit { putBoolean("comun", false) }
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        }
                    },
                )
            }
        },
        modifier = Modifier.background(colorResource(R.color.fondo2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.fondo2))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tienda",
                    color = Color.Black,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .wrapContentWidth()
                        .wrapContentHeight()
                )
                IconButton(onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                Row {
                    CardSlider()
                }

            }
        }
    }
}



@Composable
    fun CardSlider(modifier: Modifier = Modifier) {
        var arrayCarta by remember { mutableStateOf<List<Carta>>(emptyList()) }
        var db_ref = FirebaseDatabase.getInstance().reference

        //LaunchedEffect will fetch the data when the component is displayed
        LaunchedEffect(key1 = true) {
            db_ref.child("Uno").child("Tienda").get().addOnSuccessListener {
                val tempArray = mutableListOf<Carta>()
                for (i in it.children) {
                    val carta = i.getValue(Carta::class.java)
                    if (carta != null) {
                        tempArray.add(carta)
                    }
                    //si en tempArray existen valores repetidos se eliminan
                    tempArray.distinct()
                }
                arrayCarta = tempArray
                Log.d("array", arrayCarta.toString())
                Log.d("arraysiez", arrayCarta.size.toString())
            }
        }

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .height(600.dp),
        ) {
            Log.d("arra2ysize", arrayCarta.size.toString())
            arrayCarta.forEach { card ->
                Log.d("card", card.toString())
                items(1){ index->
                Card(
                    modifier = Modifier
                        .width(202.dp)
                        .height(345.dp)
                        .padding(8.dp)
                    ,
                        elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        var imagenResourse = when (card.imagen) {
                            "2130968581" -> R.drawable.cartaunoazul
                            "2130968580" -> R.drawable.cartaunoamarilla
                            "2130968579" -> R.drawable.cartauno
                            "2130968582" -> R.drawable.cartaunoverde
                            else -> R.drawable.cartaunoamarilla
                        }
                        IconButton(modifier = Modifier
                            .align(Alignment.TopEnd)
                            .fillParentMaxSize(), onClick = {
                            Log.i("He cliclao","sss")
                        }) {
                            Image(
                            bitmap = ImageBitmap.imageResource(imagenResourse),
                            contentDescription = "",
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(90.dp)

                                .border(2.dp, Color.Black)
                        ) }
                        
                        Text(
                            text = "${card.numero}\n--",
                            color = Color.Black,
                            fontSize = 50.sp,
                            fontFamily = FontFamily.Default,
                            modifier = Modifier.padding(7.dp)
                        )
                        Text(
                            text = "${card.numero}",
                            color = Color.Black,
                            fontSize = 160.sp,
                            fontFamily = FontFamily.Default,
                            modifier = Modifier
                                .padding(7.dp)
                                .align(Alignment.Center),
                        )
                        Text(
                            text = "${card.numero} \n--",
                            color = Color.Black,
                            fontSize = 50.sp,
                            fontFamily = FontFamily.Default,
                            modifier = Modifier
                                .rotate(180f)
                                .padding(7.dp)
                                .align(Alignment.BottomEnd)
                        )
                    }
                }
            }
            }
        }
    }










@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview4() {
    ProjectoFinalFranciscoComposeTheme {
        MenuDelAdministrador()
    }
}

