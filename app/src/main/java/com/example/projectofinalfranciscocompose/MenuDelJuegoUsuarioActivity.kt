package com.example.projectofinalfranciscocompose

import android.app.Activity.RESULT_OK
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.provider.Settings.Secure.putInt
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.projectofinalfranciscocompose.MenuDelJuegoUsuarioActivity.Companion.create_notification
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class MenuDelJuegoUsuarioActivity : ComponentActivity() {

    companion object {
        val APP_ID = "com.example.seminarionotificacioneskotlinfranciscorequena"
        val NOTIFICATION_ID = "${APP_ID}_c1"
        val id = AtomicInteger(0)
        fun create_notification(): Int {
            return id.incrementAndGet()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            ProjectoFinalFranciscoComposeTheme {
                MenuOpciones()

            }
        }
        var sp: SharedPreferences = getSharedPreferences("comun", 0)
        sp.edit().putBoolean("islogued", true).apply()
        sp.edit().putInt("tipo",1).apply()

    }

}


@Composable
fun MenuOpciones(modifier: Modifier = Modifier) {
    var context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var db_ref = FirebaseDatabase.getInstance().reference
    var buscarValor by rememberSaveable { mutableStateOf("") }
    var cartaref= db_ref.child("Uno").child("Publicacion")
    var arrayCarta = remember { mutableStateListOf<Carta>() }

    var arrayCartaFiltrada = remember {
        derivedStateOf {
            arrayCarta.filter {
                it.Nombre?.contains(buscarValor) == true
            }
        }
    }


    //LaunchedEffect will fetch the data when the component is displayed
    LaunchedEffect(key1 = true, key2 = vertienda) {

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
//                                val intent =
//                                    Intent(context, AboutActivity::class.java)
//                                context.startActivity(intent)
                                drawerState.close()
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    ExtendedFloatingActionButton(
                        text = { Text("Jugar Partida") },
                        icon = {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = " ",
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(30.dp)
                            )
                        },
                        onClick = {
                            scope.launch {
                                Toast.makeText(context, "Jugar Partida", Toast.LENGTH_SHORT).show()

                                val intent = Intent(context, MenuEleccionPartidaActivity::class.java)
                                intent.putExtra("tipo", 1)
                                context.startActivity(intent)
                                drawerState.close()
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
                                sharedPreferences.edit().putBoolean("islogued",false).apply()
                                sharedPreferences.edit().putInt("tipo",0).apply()
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                                drawerState.close()
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
                    Text(
                        text = "Tienda",
                        color = Color.Black,
                        fontSize = 40.sp,
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentWidth()
                            .wrapContentHeight()
                    )
                    IconButton(modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp), onClick = {
                    }){
                        //Este icono es del monedero
                        Icon(
                            ImageBitmap.imageResource(R.drawable.monedero),
                            contentDescription = "Cartera",)
                    }
                    IconButton(modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp), onClick = {
                            val intent = Intent(context, MazoActivity::class.java)
                            context.startActivity(intent)
                    }){
                        Image(ImageBitmap.imageResource(R.drawable.mazo),contentDescription = "mazo")
                    }
                    IconButton(modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp), onClick = {
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Row {
                        CartasPublicadas(arrayCartaFiltrada.value)
                    }

                }
            }


        }
    }
}
@Composable
fun CartasPublicadas(arrayCarta: List<Carta>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
            .height(600.dp),
    ) {
        Log.d("arra2ysize", arrayCarta.size.toString())
        items(arrayCarta.size) { index ->
            AnimatedCardPublicada(card = arrayCarta[index])
        }
    }
}

@Composable
fun AnimatedCardPublicada(card: Carta) {
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
                .padding(10.dp).border(2.dp, Color.Black,shape = RoundedCornerShape(15.dp))
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
                            var context = LocalContext.current
                            Button(modifier = Modifier.wrapContentWidth(), onClick = {
                                Toast.makeText(context, "Carta Pedida", Toast.LENGTH_SHORT).show()
//                                //Mandamos una peticion al administrador para que la acepte
                                //Mandaremos con un Intent la carta que se va a pedir para que en un lazyRow que tendra el administrador se muestre la carta
                                var db_ref = FirebaseDatabase.getInstance().reference
                                var sp: SharedPreferences = context.getSharedPreferences("comun", 0)
                                var user = sp.getString("username", "").toString()

                                Util.CrearPedido(db_ref,user,card)

                                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                                    val channelId= user.toString()
                                    val channelName="Mi canal"
                                    val notificationChannel= NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT)
                                    context.getSystemService(NotificationManager::class.java).createNotificationChannel(notificationChannel)


                                }

                                val builder= NotificationCompat.Builder(context,user.toString())
                                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                                    .setContentTitle("Hace mucho frio")
                                    .setContentText("Abrigate o te pondrás malo. Mi id es $user")
                                    .setColor(ContextCompat.getColor(context,R.color.fondo2))


                                with(context.getSystemService<NotificationManager>()){
                                    this?.notify(1,builder.build())
                                    create_notification()

                                }

                            }) {
                                Text(
                                    "Pedir Carta",
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
fun GreetingPreview() {

    ProjectoFinalFranciscoComposeTheme {

            MenuOpciones()

    }
}