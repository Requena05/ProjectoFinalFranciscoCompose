package com.example.projectofinalfranciscocompose

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerPedidosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VerPedidos(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
var userPedido=""
@Composable
fun VerPedidos( modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var buscarValor by rememberSaveable { mutableStateOf("") }

    var arrayPedidos = remember { mutableStateListOf<Carta>() }

    var arrayCartaFiltrada = remember {
        derivedStateOf {
            arrayPedidos.filter {
                it.Nombre?.contains(buscarValor) == true
            }
        }
    }

    var db_ref = FirebaseDatabase.getInstance().reference
    var sp : SharedPreferences = context.getSharedPreferences("comun", MODE_PRIVATE)
    var cartaref = db_ref.child("Uno").child("Pedido")
    db_ref.child("Uno").child("Pedido").get().addOnSuccessListener {
       Log.d("ssada",it.value.toString())
        if ( it.value==null){
            Log.d("null", "null")
        }




    }
    LaunchedEffect(key1 = true) {

        var valueEValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null) {

                } else {
                    for (i in snapshot.value as Map<*, *>) {
                        Log.v("firebase", i.key.toString())
                        userPedido = i.key.toString()
                        for (j in i.value as Map<*, *>) {

                            Log.v("durooooo", j.toString())

                            var descripcion =
                                j.value.toString().split(",")[0].split("=")[1]
                            Log.d("descripcion", descripcion)

                            var id_carta =
                                j.value.toString().split(",")[1].split("=")[1]
                            Log.d("id_carta", id_carta.toString())

                            var precio =
                                j.value.toString().split(",")[2].split("=")[1].toInt()
                            Log.d("precio", precio.toString())

                            var numero =
                                j.value.toString().split(",")[3].split("=")[1].toInt()
                            Log.d("numero", numero.toString())

                            var imagen =
                                j.value.toString().split(",")[4].split("=")[1].toInt()
                            Log.d("imagen", imagen.toString())

                            var id_creador =
                                j.value.toString().split(",")[5].split("=")[1]
                            Log.d("id_creador", id_creador)
                            var publicada =
                                j.value.toString().split(",")[6].split("=")[1]
                            Log.d("publicada", publicada)

                            var nombre = j.value.toString().split(",")[7].split("=")[1]
                            Log.d("nombre", nombre)

                            //borramos todos la linea de pedido

                            var cartapedida = Carta(
                                id_carta,
                                numero,
                                precio,
                                nombre,
                                descripcion,
                                id_creador,
                                imagen.toString(),
                                publicada.toBoolean()
                            )

                            arrayPedidos.add(cartapedida)
                        }

                        Log.d("valores", i.value.toString())
                    }

                }
            }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        cartaref.addValueEventListener(valueEValueEventListener)
    }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.fondo2))
                .padding(innerPadding)
        ) {
            Row {
                Text(
                    text = "Pedidos",
                    color = Color.Black,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentWidth()
                        .wrapContentHeight()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            {
                //Crearemos una barra para buscar cartas por nombre en la tienda
                TextField(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(), value = buscarValor,
                    onValueChange = {
                        buscarValor = it
                    },
                    label = { Text("Buscar Carta") })
                Spacer(modifier = Modifier.height(16.dp))

            }


            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                Row {

                    PedidosSlider(arrayCartaFiltrada.value)

                }

            }
        }
    }


}
@Composable
fun PedidosSlider(arrayCarta: List<Carta>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
            .height(600.dp),
    ) {
        Log.d("arra2ysize", arrayCarta.size.toString())
        items(arrayCarta.size) { index ->
            AnimatedCardPedido(card = arrayCarta[index])
        }
    }
}
@Composable
fun AnimatedCardPedido(card: Carta) {
    var isExpanded by remember { mutableStateOf(false) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val targetWidth = if (isExpanded) screenWidth * 0.8f else 202.dp
    val targetHeight = if (isExpanded) screenHeight * 0.6f else 345.dp
    val targetOffsetX = if (isExpanded) (screenWidth - targetWidth) / 64 else 0.dp
    val targetOffsetY = if (isExpanded) (screenHeight - targetHeight) / 4 else 0.dp
    val targetElevation = if (isExpanded) 16.dp else 10.dp
    val targetZIndex = if (isExpanded) 10f else 0f

    val animatedWidth by animateDpAsState(
        targetValue = targetWidth,
        animationSpec = tween(durationMillis = 500), label = ""
    )
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
                .width(animatedWidth)
                .height(animatedHeight)
                .padding(10.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp))
                .clickable { isExpanded = !isExpanded },
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                var imagenResourse = when (card.imagen) {
                    "2131165276" -> R.drawable.cartaunoazul
                    "2131165274" -> R.drawable.cartauno
                    "2131165277" -> R.drawable.cartaunoverde
                    else -> R.drawable.cartaunoamarilla
                }
                Image(
                    bitmap = ImageBitmap.imageResource(imagenResourse),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, Color.Black),
                    contentScale = ContentScale.Crop
                )

                if (isExpanded) {
                    //que cambie los datos cuando la carta se expande
                    //tiene que esperar a que se cargue la carta


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            //pondra el nombre del usuario que la ha pedido
                            text = "${userPedido}",
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            modifier = Modifier.padding(7.dp)
                        )
                        Text(text = "Nombre: ${card.Nombre}", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Descripción: ${card.descripcion}", fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Precio: ${card.Precio}€", fontSize = 20.sp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            if (vertienda) {
                                Log.d("carta", card.toString())
                            } else {
                            }


                            Button(modifier = Modifier.wrapContentWidth(), onClick = {

                            })
                            {
                                Text(
                                    "Denegar",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )

                            }
                            Button(modifier = Modifier.wrapContentWidth(), onClick = {

                            }) {
                                Text(
                                    "Aceptar",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }

                        }
                    }

                } else {

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
                            .align(Alignment.Center)
                    )
                    Text(
                        text = "${card.numero}\n--",
                        color = Color.Black,
                        fontSize = 50.sp,
                        fontFamily = FontFamily.Default,
                        modifier = Modifier
                            .padding(7.dp)
                            .rotate(180f)
                            .align(Alignment.BottomEnd)
                    )


                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview9() {
    ProjectoFinalFranciscoComposeTheme {
        VerPedidos()
    }
}
