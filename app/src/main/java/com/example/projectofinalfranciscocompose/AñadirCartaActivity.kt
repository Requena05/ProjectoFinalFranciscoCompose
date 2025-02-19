package com.example.projectofinalfranciscocompose

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectofinalfranciscocompose.R.drawable.cartauno
import com.example.projectofinalfranciscocompose.R.drawable.cartaunoamarilla
import com.example.projectofinalfranciscocompose.R.drawable.cartaunoazul
import com.example.projectofinalfranciscocompose.R.drawable.cartaunoverde
import com.example.projectofinalfranciscocompose.Util.Companion.EditarCarta
import com.example.projectofinalfranciscocompose.Util.Companion.EscribirCarta
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.FirebaseDatabase

class AñadirCartaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var context = LocalContext.current
                    var sp: SharedPreferences = context.getSharedPreferences("comun", 0)
                    var editar = sp.getString("carta", " ")
                    if (editar == " "){
                        CrearCarta(modifier = Modifier.padding(innerPadding))
                    }else{
                        EditarCarta(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
@Composable
fun EditarCarta(modifier: Modifier = Modifier) {
    var context = LocalContext.current
    var sp: SharedPreferences = context.getSharedPreferences("comun", 0)
    var editar = sp.getString("carta", " ")
    Log.d("editar", editar.toString())
    db_ref = FirebaseDatabase.getInstance().getReference()

    var numero by remember { mutableStateOf(0) }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var currentIndex by remember { mutableStateOf(0) }
    var nombre by remember { mutableStateOf("") }



    LaunchedEffect(key1 = editar) {
        db_ref.child("Uno").child("Tienda").get().addOnSuccessListener {
            //con el id de la carta rellenamos los campos
            for (i in it.children) {
                val carta = i.getValue(Carta::class.java)
                if (carta!!.id_creador == editar) {
                    numero = carta!!.numero.toString().toInt()
                    precio = carta.Precio.toString()
                    descripcion = carta.descripcion.toString()
                    nombre = carta.Nombre.toString()

                }


            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fondo2)).padding(top = 40.dp),
    ) {
        var cartacreada: MutableList<Carta>
        cartacreada = mutableListOf()


        var posiblecartas: MutableList<String>
        posiblecartas = mutableListOf()
        posiblecartas.add("Rojo")
        posiblecartas.add("Azul")
        posiblecartas.add("Amarillo")
        posiblecartas.add("Verde")
        when (posiblecartas[currentIndex]) {
            "Rojo" -> {
                posiblecartas[currentIndex] = cartauno.toString()
            }

            "Azul" -> {
                posiblecartas[currentIndex] = cartaunoazul.toString()
            }

            "Amarillo" -> {
                posiblecartas[currentIndex] = cartaunoamarilla.toString()
            }

            "Verde" -> {
                posiblecartas[currentIndex] = cartaunoverde.toString()

            }

            else -> {
                posiblecartas[currentIndex] = cartauno.toString()
            }
        }


        Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 80.dp)) {

            Image(
                ImageBitmap.imageResource(posiblecartas[currentIndex].toInt()),
                contentDescription = "",
                modifier = Modifier
                    .height(300.dp)
                    .width(185.dp)
                    .border(2.dp, Color.Black)

            )

            Text(
                text = "$numero\n--",
                color = Color.Black,
                fontSize = 50.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier.padding(7.dp)

            )
            Text(
                text = "$numero",
                color = Color.Black,
                fontSize = 160.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier
                    //El numero tiene que tener color por dentro
                    .padding(7.dp).align(Alignment.Center),

                )
            Text(
                text = "$numero \n--",
                color = Color.Black,
                fontSize = 50.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier.rotate(180f)
                    .padding(7.dp)
                    .align(Alignment.BottomEnd)

            )
        }
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                if (posiblecartas.isEmpty()) {
                    Text("No items to display")

                } else {
                    fun moveBackward() {
                        currentIndex =
                            if (currentIndex > 0) currentIndex - 1 else posiblecartas.size - 1
                    }

                    fun moveForward() {
                        currentIndex =
                            if (currentIndex < posiblecartas.size - 1) currentIndex + 1 else 0
                    }
                    Button(
                        onClick = { moveBackward() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Previous"
                        )
                    }

                    Button(
                        onClick = { moveForward() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next"
                        )
                    }

                }
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Button(modifier = Modifier.padding(end = 8.dp).width(70.dp), onClick = {
                    numero++
                    if (numero == 10) {
                        numero = 0
                    }

                }) {
                    Text("+", modifier = Modifier.align(Alignment.CenterVertically))
                }
                Button(modifier = Modifier.padding(start = 8.dp).width(70.dp), onClick = {
                    numero--
                    if (numero == -1) {
                        numero = 9
                    }
                }) {
                    Text("-", modifier = Modifier.align(Alignment.CenterVertically))
                }
            }

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre De la Carta") },
                modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Black)
            )

            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripcion") },
                modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Black)
            )
            TextField(
                value = precio,
                onValueChange = { precio = it.replace(",", ".") },
                label = { Text("Precio €") },
                modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Black)
            )
        }
        TextButton(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            //comprobamos que existan valores en los campos
            db_ref = FirebaseDatabase.getInstance().getReference()
            if (nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
                Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@TextButton
            } else if (nombre.length > 20) {
                Toast.makeText(
                    context,
                    "El nombre no puede tener mas de 20 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
                return@TextButton
            } else if (descripcion.length > 50) {
                Toast.makeText(
                    context,
                    "La descripcion no puede tener mas de 50 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
                return@TextButton
            } else if (precio.toDoubleOrNull() == null) {
                Toast.makeText(
                    context,
                    "El precio tiene que ser un numero",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@TextButton
            } else {

                db_ref.child("Uno").child("Usuarios").get().addOnSuccessListener {
                    var sharedPreferences: SharedPreferences =
                        context.getSharedPreferences("username", MODE_PRIVATE)
                    var user = sharedPreferences.getString("username", "")
                    Log.d("user", user.toString())
                    for (i in it.children) {
                        val usuario = i.getValue(UsuarioLogin::class.java)
                        if (usuario != null && usuario.username == user.toString()) {
                            Log.d("duro", posiblecartas[currentIndex].toString())
                            var id_carta =
                                db_ref.child("Uno").child("Tienda").child(editar!!).key.toString()
                            cartacreada.add(
                                Carta(
                                    user.toString(),
                                    numero,
                                    precio.toInt(),
                                    nombre,
                                    descripcion,
                                    id_carta,
                                    posiblecartas[currentIndex].toString()
                                )
                            )



                            EditarCarta(db_ref, id_carta, cartacreada[0])
                            Toast.makeText(context, "Carta Editada", Toast.LENGTH_SHORT)
                                .show()
                        }
                        cartacreada.clear()

                    }
                }

            }


        }) {

            Text(
                text = "Editar Carta",
                modifier = modifier
                    //tiene que estar al centro
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .wrapContentWidth()
                    .padding(16.dp),
                fontSize = 22.sp,
            )
        }
    }

}
@Composable
fun CrearCarta(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fondo2))
    ) {
        var cartacreada: MutableList<Carta> = mutableListOf()


        var posiblecartas: MutableList<String>
        posiblecartas = mutableListOf()
        posiblecartas.add("Rojo")
        posiblecartas.add("Azul")
        posiblecartas.add("Amarillo")
        posiblecartas.add("Verde")

        var context = LocalContext.current


        var numero by remember { mutableStateOf(0) }
        var precio by remember { mutableStateOf(0) }
        var descripcion by remember { mutableStateOf("") }
        var currentIndex by remember { mutableStateOf(0) }
        var nombre by remember { mutableStateOf("") }
        var sp: SharedPreferences = context.getSharedPreferences("comun", 0)
        var editar = sp.getString("carta", " ")
        Log.d("editar", editar.toString())
        db_ref = FirebaseDatabase.getInstance().getReference()

        when (posiblecartas[currentIndex]) {
            "Rojo" -> {
                posiblecartas[currentIndex] = cartauno.toString()
            }

            "Azul" -> {
                posiblecartas[currentIndex] = cartaunoazul.toString()
            }

            "Amarillo" -> {
                posiblecartas[currentIndex] = cartaunoamarilla.toString()
            }

            "Verde" -> {
                posiblecartas[currentIndex] = cartaunoverde.toString()

            }

            else -> {
                posiblecartas[currentIndex] = cartauno.toString()
            }
        }
        Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 80.dp)) {
            Image(
                ImageBitmap.imageResource(posiblecartas[currentIndex].toInt()),
                contentDescription = "",
                modifier = Modifier
                    .height(300.dp)
                    .width(185.dp)
                    .border(2.dp, Color.Black)

            )

            Text(
                text = "$numero\n--",
                color = Color.Black,
                fontSize = 50.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier.padding(7.dp)

            )
            Text(
                text = "$numero",
                color = Color.Black,
                fontSize = 160.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier
                    //El numero tiene que tener color por dentro
                    .padding(7.dp).align(Alignment.Center),

                )
            Text(
                text = "$numero \n--",
                color = Color.Black,
                fontSize = 50.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier.rotate(180f)
                    .padding(7.dp)
                    .align(Alignment.BottomEnd)

            )
        }
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                if (posiblecartas.isEmpty()) {
                    Text("No items to display")

                } else {
                    fun moveBackward() {
                        currentIndex =
                            if (currentIndex > 0) currentIndex - 1 else posiblecartas.size - 1
                    }

                    fun moveForward() {
                        currentIndex =
                            if (currentIndex < posiblecartas.size - 1) currentIndex + 1 else 0
                    }
                    Button(
                        onClick = { moveBackward() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Previous"
                        )
                    }

                    Button(
                        onClick = { moveForward() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next"
                        )
                    }

                }
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Button(modifier = Modifier.padding(end = 8.dp).width(70.dp), onClick = {
                    numero++
                    if (numero == 10) {
                        numero = 0
                    }

                }) {
                    Text("+", modifier = Modifier.align(Alignment.CenterVertically))
                }
                Button(modifier = Modifier.padding(start = 8.dp).width(70.dp), onClick = {
                    numero--
                    if (numero == -1) {
                        numero = 9
                    }
                }) {
                    Text("-", modifier = Modifier.align(Alignment.CenterVertically))
                }
            }

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre De la Carta") },
                modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Black)
            )

            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripcion") },
                modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Black)
            )
            TextField(
                value = precio.toString(),
                onValueChange = { if(it.isNotEmpty()&&it.toIntOrNull()!=null){
                    precio = it.toString().toInt()
                }else{
                    Toast.makeText(context, "Solo puedes introducir numeros", Toast.LENGTH_SHORT).show()

                } },
                label = { Text("Precio €") },
                modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Black)
            )
        }


        TextButton(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            //comprobamos que existan valores en los campos
            db_ref = FirebaseDatabase.getInstance().getReference()
            if (nombre.isEmpty() || descripcion.isEmpty() || precio.toString().isEmpty()) {
                Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@TextButton
            } else if (nombre.length > 20) {
                Toast.makeText(
                    context,
                    "El nombre no puede tener mas de 20 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
                return@TextButton
                //no puede haber dos cartas con el mismo nombre
            }else if( db_ref.child("Uno").child("Tienda").get().toString().contains(nombre)){
                Toast.makeText(context, "Ya existe una carta con ese nombre", Toast.LENGTH_SHORT).show()
                return@TextButton
            } else if (descripcion.length > 50) {
                Toast.makeText(
                    context,
                    "La descripcion no puede tener mas de 50 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
                return@TextButton
            } else if (precio.toString().toDoubleOrNull() == null) {
                Toast.makeText(
                    context,
                    "El precio tiene que ser un numero",
                    Toast.LENGTH_SHORT).show()
            } else {
                //comprobamos que el nombre de la carta no exista
                db_ref.child("Uno").child("Tienda").get().addOnSuccessListener {
                    for (i in it.children) {
                        val carta = i.getValue(Carta::class.java)
                        if (carta != null && carta.Nombre == nombre) {
                            Toast.makeText(context, "Ya existe una carta con ese nombre", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }else{
                            db_ref.child("Uno").child("Usuarios").get().addOnSuccessListener {

                                var sharedPreferences: SharedPreferences =
                                    context.getSharedPreferences("comun", MODE_PRIVATE)
                                var user = sharedPreferences.getString("username", "")
                                Log.d("user", user.toString())
                                for (i in it.children) {
                                    val usuario = i.getValue(UsuarioLogin::class.java)
                                    if (usuario != null && usuario.username == user.toString()) {
                                        Log.d("duro", posiblecartas[currentIndex].toString())
                                        var id_carta =
                                            db_ref.child("Uno").child("Tienda").push().key.toString()
                                        cartacreada.add(
                                            Carta(
                                                user.toString(),
                                                numero,
                                                precio.toString().toInt(),
                                                nombre,
                                                descripcion,
                                                id_carta,
                                                posiblecartas[currentIndex]
                                            )
                                        )
                                        Log.d("carta", cartacreada[0].toString())
                                        EscribirCarta(db_ref, id_carta, cartacreada[0])
                                        Toast.makeText(context, "Carta Creada", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    cartacreada.clear()

                                }
                            }
                        }
                    }
                }


            }


        }) {

            Text(
                text = "Crear Carta",
                modifier = modifier
                    //tiene que estar al centro
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .wrapContentWidth()
                    .padding(16.dp),
                fontSize = 22.sp,
            )
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview5() {
    ProjectoFinalFranciscoComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CrearCarta(modifier = Modifier.padding(innerPadding))
        }
    }
}