package com.example.projectofinalfranciscocompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuEleccionPartidaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                MenuEleccionPartida()
            }
        }
    }
}

@Composable
fun MenuEleccionPartida( modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.fondo))) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(colorResource(R.color.fondo2)))
        {
            PartidaSlider(modifier = Modifier)


        }
    }
}
@Composable
fun PartidaSlider(modifier: Modifier = Modifier) {
    var arrayPartida by remember { mutableStateOf<List<Partida>>(emptyList()) }
    var db_ref = FirebaseDatabase.getInstance().reference
    var partidaRef by remember { mutableStateOf(db_ref.child("Uno").child("Partidas")) }

    LaunchedEffect(key1 = true) {


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempArray = mutableListOf<Partida>()
                for (i in snapshot.children) {
                    val partida = i.getValue(Partida::class.java)
                    if (partida != null) {
                        tempArray.add(partida)
                    }

                }
                arrayPartida = tempArray
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PartidaSlider", "Error al obtener datos de Firebase: ${error.message}")
            }
        }
        partidaRef.addValueEventListener(valueEventListener)

        // This is to load the data faster
        partidaRef.get().addOnSuccessListener {
            val tempArray = mutableListOf<Partida>()
            for (i in it.children) {
                val partida = i.getValue(Partida::class.java)
                if (partida != null) {
                    tempArray.add(partida)
                }
                //si en tempArray existen valores repetidos se eliminan
                tempArray.distinct()
            }
            arrayPartida = tempArray
            Log.d("array", arrayPartida.toString())
            Log.d("arraysiez", arrayPartida.size.toString())
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Log.d("arra2ysize", arrayPartida.size.toString())
        arrayPartida.forEach { card ->
            Log.d("card", card.toString())
            items(1) { index ->
                AnimatedPartida2(card)
            }
        }
    }
}

@Composable
fun AnimatedPartida2(partida: Partida) {
    var isExpanded by remember { mutableStateOf(false) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val targetWidth = if (isExpanded) screenWidth * 0.8f else 202.dp
    val targetHeight = if (isExpanded) screenHeight * 0.6f else 250.dp
    val targetOffsetX = if (isExpanded) (screenWidth - targetWidth) / 64 else 0.dp
    val targetOffsetY = if (isExpanded) (screenHeight - targetHeight) / 128 else 0.dp
    val targetElevation = if (isExpanded) 16.dp else 10.dp
    val targetZIndex = if (isExpanded) 10f else 0f

    val animatedHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    val animatedOffsetX by animateDpAsState(
        targetValue = targetOffsetX,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    val animatedOffsetY by animateDpAsState(
        targetValue = targetOffsetY,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    val animatedZIndex by animateDpAsState(
        targetValue = targetZIndex.dp,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(animatedZIndex.value)
    ) {
        Card(
            modifier = Modifier
                .offset(x = animatedOffsetX, y = animatedOffsetY)
                .fillMaxWidth()
                .height(animatedHeight)
                .padding(10.dp)
                .border(
                    2.dp,
                    Color.Black, shape = RoundedCornerShape(15.dp)
                )
                .clickable { isExpanded = !isExpanded },
            elevation = CardDefaults.cardElevation(targetElevation)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.imagenpartidauno),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, Color.Black),
                    contentScale = ContentScale.Crop
                )

                if (isExpanded) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Nombre: ${partida.nombre}", fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth().background(colorResource(R.color.fondo4),shape = RoundedCornerShape(15.dp)).border(2.dp, Color.Black),
                            textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Precio: ${partida.precio}€",  fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth().background(colorResource(R.color.fondo4),shape = RoundedCornerShape(15.dp)).border(2.dp, Color.Black),
                            textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Jugadores: ${partida.lista_jugadores!!.size}/${partida.cantidad_jugadores}",
                             fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth().background(colorResource(R.color.fondo4),shape = RoundedCornerShape(15.dp)).border(2.dp, Color.Black),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tiempo de espera: ${partida.tiempo_espera_partida} Min",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth().background(colorResource(R.color.fondo4),shape = RoundedCornerShape(15.dp)).border(2.dp, Color.Black),
                            textAlign = TextAlign.Center
                        )


                    }
                    Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                        //Crea dos botones para unirse a la partida o para salir de ella
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Unirse")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Salir")
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview8() {
    ProjectoFinalFranciscoComposeTheme {
        MenuEleccionPartida()
    }
}