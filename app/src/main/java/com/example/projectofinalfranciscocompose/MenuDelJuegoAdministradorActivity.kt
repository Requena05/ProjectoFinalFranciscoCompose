package com.example.projectofinalfranciscocompose

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Context.UI_MODE_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.TextSwitcher
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
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
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.A
import androidx.core.content.edit
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.logging.Logger.global

var vertienda by mutableStateOf(false)


class MenuDelJuegoAdministradorActivity : ComponentActivity() {


    @SuppressLint("NewApi")
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
        sp.edit().putInt("tipo", 2).apply()
    }



}

@Composable
fun MenuDelAdministrador(modifier: Modifier = Modifier) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isDarkMode by remember { mutableStateOf(true) }
    var buscarValor by rememberSaveable { mutableStateOf("") }

    var arrayCarta = remember { mutableStateListOf<Carta>() }

    var arrayCartaFiltrada = remember {
        derivedStateOf {
            arrayCarta.filter {
                it.Nombre?.contains(buscarValor) == true
            }
        }
    }

    var db_ref = FirebaseDatabase.getInstance().reference
    var cartaref by remember { mutableStateOf(db_ref.child("Uno").child("Tienda")) }

    LaunchedEffect(key1 = true, key2 = vertienda) {
        if (vertienda) {
            cartaref = db_ref.child("Uno").child("Publicacion")
        } else {
            cartaref = db_ref.child("Uno").child("Tienda")
        }

        val valueEventListener = object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount==0L){

                    arrayCarta.clear()

                }
                for (i in snapshot.children) {
                    val carta = i.getValue(Carta::class.java)
                    if (carta != null) {
                        arrayCarta += carta
                        Log.d("array", arrayCarta.toString())
                    }
                    //comparamos el size de las cartas de firebase con el size de nuestro array local
                    if (arrayCarta.size != snapshot.childrenCount.toInt()) {
                        //ahora actualiza el array local
                        arrayCarta.clear()
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



        cartaref.get().addOnSuccessListener {
            val tempArray = mutableListOf<Carta>()
            for (i in it.children) {
                val carta = i.getValue(Carta::class.java)
                if (carta != null) {
                    tempArray.add(carta)
                }
                //si en tempArray existen valores repetidos se eliminan
                tempArray.distinct()
            }
            arrayCarta.clear()
            arrayCarta.addAll(tempArray)
            Log.d("array", arrayCarta.toString())
            Log.d("arraysiez", arrayCarta.size.toString())
        }


    }

    // Track the mode
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
                    ExtendedFloatingActionButton(
                        text = { Text("About") },
                        icon = {
                            Icon(
                                Icons.Filled.Warning,
                                contentDescription = " ",
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(30.dp)
                            )
                        },
                        onClick = {
                            scope.launch {
                                Toast.makeText(context, "About", Toast.LENGTH_SHORT).show()

                            }
                        },
                    )
                    var notis by remember { mutableStateOf(0) }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box {
                        ExtendedFloatingActionButton(
                            modifier = Modifier.width(190.dp),
                            elevation = FloatingActionButtonDefaults.elevation(10.dp),

                            text = { Text("Ver pedidos") },
                            icon = {
                                Icon(
                                    Icons.Filled.Notifications,
                                    contentDescription = " ",
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(30.dp)
                                )
                            },
                            onClick = {
                                scope.launch {
                                    var intent = Intent(context, VerPedidosActivity::class.java)
                                    context.startActivity(intent)

                                }
                            },
                        )
                        Text(
                            text = notis.toString(),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(30.dp)
                                .background(
                                    colorResource(R.color.fondo3),
                                    shape = RoundedCornerShape(100)
                                )
                                .border(2.dp, Color.Black, shape = RoundedCornerShape(100))
                                .padding(3.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.width(190.dp),
                        elevation = FloatingActionButtonDefaults.elevation(10.dp),

                        text = { Text("Ver Tienda") },
                        icon = {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = " ",
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(30.dp)
                            )
                        },
                        onClick = {
                            //Si se pulsa este boton el lazyRow se actualiza pero con los datos de la base de datos (publicaciones)
                            vertienda = true
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.width(190.dp),
                        elevation = FloatingActionButtonDefaults.elevation(10.dp),

                        text = { Text("Ver Almacen") },
                        icon = {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = " ",
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(30.dp)
                            )
                        },
                        onClick = {
                            //Si se pulsa este boton el lazyRow se actualiza pero con los datos de la base de datos (publicaciones)
                            vertienda = false
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.width(190.dp),
                        elevation = FloatingActionButtonDefaults.elevation(10.dp),

                        text = { Text("Agregar Partidas") },
                        icon = {
                            Icon(
                                Icons.Filled.AddCircle,
                                contentDescription = " ",
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(30.dp)
                            )
                        },
                        onClick = {
                            var intent = Intent(context, AgregarPartidaActivity::class.java)
                            var sp: SharedPreferences = context.getSharedPreferences("comun", 0)
                            //pasamos el id del usuario al intent
                            sp.edit().putString("id_creador",sp.getString("username", "")).apply()
                            Log.d("id_creador", sp.getString("username", "").toString())



                            context.startActivity(intent)
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    //Haz un boton para activar desactivar el modo noche

                    Row(modifier=Modifier.border(0.dp, Color.Black, shape = RoundedCornerShape(12)).width(190.dp).background(colorResource(R.color.fondo4),
                    )) {
                        Text(text = "Modo noche",modifier=Modifier.align(Alignment.CenterVertically))
                        Switch( checked = isDarkMode,modifier=Modifier.align(Alignment.CenterVertically) ,onCheckedChange = { valor ->
                            Log.d("dda", isDarkMode.toString())
                            isDarkMode = !isDarkMode
                            Log.d("dda", isDarkMode.toString())

                            if (isDarkMode) {
                                //cambiamos al modo noche
                                AppCompatDelegate.MODE_NIGHT_YES
                                Log.d("modo,", AppCompatDelegate.MODE_NIGHT_YES.toString())
                                Toast.makeText(context, "Modo noche activado", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                //cambiamos al modo dia
                                AppCompatDelegate.MODE_NIGHT_NO
                                Log.d("modo,", AppCompatDelegate.MODE_NIGHT_NO.toString())
                                Toast.makeText(
                                    context,
                                    "Modo noche desactivado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.width(190.dp),
                        elevation = FloatingActionButtonDefaults.elevation(10.dp),
                        text = { Text("Agregar Carta") },
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
                                var sp: SharedPreferences = context.getSharedPreferences("comun", 0)
                                sp.edit().putString("carta", "a").apply()
                                val intent = Intent(context, AñadirCartaActivity::class.java)
                                context.startActivity(intent)
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.width(190.dp),
                        elevation = FloatingActionButtonDefaults.elevation(10.dp),
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
                   if (vertienda){
                       Text(
                           text = "Tienda",
                           color = Color.Black,
                           fontSize = 40.sp,
                           modifier = Modifier
                               .padding(16.dp)
                               .wrapContentWidth()
                               .wrapContentHeight()
                       )
                   }else{
                       Text(
                           text = "Almacen",
                           color = Color.Black,
                           fontSize = 40.sp,
                           modifier = Modifier
                               .padding(16.dp)
                               .wrapContentWidth()
                               .wrapContentHeight()
                       )
                   }
                    IconButton(modifier = Modifier.size(70.dp).align(Alignment.CenterVertically),onClick = {


                        val intent = Intent(context, MenuEleccionPartidaActivity::class.java)
                        intent.putExtra("tipo", 2)
                        context.startActivity(intent)


                    }
                    ){
                        Icon(
                            Icons.Filled.Edit, contentDescription = "Editar partidas", modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                        )

                        }

                    IconButton(modifier = Modifier.size(70.dp).align(Alignment.CenterVertically), onClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    }) {
                        Icon(
                            Icons.Filled.Menu, contentDescription = "Menu", modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp))
                {
                    //Crearemos una barra para buscar cartas por nombre en la tienda
                    TextField(modifier=Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),value = buscarValor,
                        onValueChange = {
                            buscarValor = it
                        },
                        label = { Text("Buscar Carta") })
                    Spacer(modifier = Modifier.height(16.dp))

                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Row {
                        CardSlider(arrayCartaFiltrada.value)
                    }

                }
            }
        }
    }
}


@Composable
fun CardSlider(arrayCarta: List<Carta>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
            .height(600.dp),
    ) {
        Log.d("arra2ysize", arrayCarta.size.toString())
        items(arrayCarta.size) { index ->
            AnimatedCard(card = arrayCarta[index])
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
                                Button(modifier = Modifier.wrapContentWidth(), onClick = {
                                    db_ref = FirebaseDatabase.getInstance().getReference()
                                    card.publicada = true
                                    Util.PublicarCarta(db_ref, card!!.id_creador.toString(), card)
                                    isExpanded = false
                                    Util.borrarCarta(db_ref, card!!.id_creador.toString())

                                }) {
                                    Text(
                                        "Publicar",
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }
                            }


                            Button(modifier = Modifier.wrapContentWidth(), onClick = {
                                db_ref = FirebaseDatabase.getInstance().getReference()
                                if (vertienda) {
                                    Util.BorrarPublicacion(db_ref, card!!.id_creador.toString())
                                    isExpanded = false
                                } else {
                                    Util.borrarCarta(db_ref, card!!.id_creador.toString())

                                }

                            })
                            {
                                Text(
                                    "Eliminar",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )

                            }
                            var context = LocalContext.current
                            Button(modifier = Modifier.wrapContentWidth(), onClick = {
                                db_ref = FirebaseDatabase.getInstance().getReference()
                                if (vertienda) {
                                    Toast.makeText(
                                        context,
                                        "Carta ya publicada no se puede editar",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    val intent =
                                        Intent(context, AñadirCartaActivity::class.java)
                                    //crea una sp para pasar toda la carta para que se pueda editar
                                    val sharedPreferences =
                                        context.getSharedPreferences("comun", 0)
                                    sharedPreferences.edit {
                                        putString(
                                            "carta",
                                            card.id_creador
                                        )
                                    }
                                    context.startActivity(intent)
                                }

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

