package com.example.projectofinalfranciscocompose

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.A
import androidx.core.content.edit
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MenuDelJuegoAdministradorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                MenuDelAdministrador()
            }
        }

        var sp: SharedPreferences = getSharedPreferences("comun", 0)
        sp.edit().putBoolean("islogued", true).apply()
        sp.edit().putInt("tipo",2).apply()
    }


}

@Composable
fun MenuDelAdministrador(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold { innerPadding ->
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .background(colorResource(R.color.gray))
                        .padding(16.dp),
                    drawerContainerColor = colorResource(R.color.black)
                ) {
                    ExtendedFloatingActionButton(modifier=Modifier.width(170.dp),

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
                    ExtendedFloatingActionButton(modifier=Modifier.width(170.dp),

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
                                sharedPreferences.edit { putInt("tipo", 0) }
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box {
                        ExtendedFloatingActionButton(modifier=Modifier.width(170.dp),

                            text = { Text("Ver pedidos") },
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
                                    Log.d("<", "1")
                                }
                            },
                        )
                        Text(text = "0",
                            modifier=Modifier
                                .align(Alignment.TopEnd)
                                .size(30.dp)
                                .background(colorResource(R.color.fondo3), shape = RoundedCornerShape(100))
                                .border(2.dp, Color.Black, shape = RoundedCornerShape(100))
                                .padding(3.dp)

                        ,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
                }
            },
            modifier = Modifier.background(colorResource(R.color.fondo))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
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
                        text = "Almacen",
                        color = Color.Black,
                        fontSize = 40.sp,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(16.dp)
                            .wrapContentWidth()
                            .wrapContentHeight()
                    )
                    IconButton(modifier = Modifier.size(70.dp), onClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu",modifier = Modifier
                            .size(40.dp)
                            .padding(2.dp))
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
}


@Composable
fun CardSlider(modifier: Modifier = Modifier) {
    var arrayCarta by remember { mutableStateOf<List<Carta>>(emptyList()) }
    var db_ref = FirebaseDatabase.getInstance().reference
    var cartaref=db_ref.child("Uno").child("Tienda")

    //LaunchedEffect will fetch the data when the component is displayed

    LaunchedEffect(key1 = true) {
        val valueEventListener = object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val carta = i.getValue(Carta::class.java)
                    if (carta != null) {
                        arrayCarta += carta
                    }
                    //comparamos el size de las cartas de firebase con el size de nuestro array local
                    if(arrayCarta.size!=snapshot.childrenCount.toInt()){
                        //ahora actualiza el array local
                        arrayCarta= emptyList()
                        for (i in snapshot.children) {
                            val carta = i.getValue(Carta::class.java)
                            if (carta != null) {
                                arrayCarta += carta
                                Log.d("array", arrayCarta.toString())
                            }
                        }


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", error.toString())
            }


        }
        cartaref.addValueEventListener(valueEventListener)



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
            items(1) { index ->
                AnimatedCard(card=card)
            }
        }
    }
}

@Composable
fun AnimatedCard(card: Carta) {
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
    val animatedElevation by animateDpAsState(
        targetValue = targetElevation,
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
                .padding(4.dp)
                .shadow(animatedElevation)
                .clickable { isExpanded = !isExpanded },
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                var imagenResourse = when (card.imagen) {
                    "2130968581" -> R.drawable.cartaunoazul
                    "2130968580" -> R.drawable.cartaunoamarilla
                    "2130968579" -> R.drawable.cartauno
                    "2130968582" -> R.drawable.cartaunoverde
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
                                Button(modifier = Modifier.wrapContentWidth(), onClick = {
                                    db_ref = FirebaseDatabase.getInstance().getReference()
                                    card.publicada=true
                                    Util.PublicarCarta(db_ref, card!!.id_creador.toString(), card)
                                    isExpanded=false
                                    Util.borrarCarta(db_ref, card!!.id_creador.toString())

                                }) {
                                    Text(
                                        "Publicar",
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }

                                Button(modifier = Modifier.wrapContentWidth(), onClick = {
                                    db_ref = FirebaseDatabase.getInstance().getReference()
                                    Util.borrarCarta(db_ref, card!!.id_creador.toString())
                                    isExpanded=false
                                })
                                    {
                                    Text("Eliminar",
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )

                                }
                                var context = LocalContext.current
                                Button(modifier = Modifier.wrapContentWidth(), onClick = {
                                    db_ref = FirebaseDatabase.getInstance().getReference()
                                    val intent = Intent(context, AñadirCartaActivity::class.java)
                                    //crea una sp para pasar toda la carta para que se pueda editar
                                    val sharedPreferences = context.getSharedPreferences("comun", 0)
                                    sharedPreferences.edit { putString("carta", card.id_creador ) }
                                    context.startActivity(intent)

                                }) {
                                    Text(
                                        "Editar",
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
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview4() {
    ProjectoFinalFranciscoComposeTheme {
        MenuDelAdministrador()
    }
}

