package com.example.projectofinalfranciscocompose

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectofinalfranciscocompose.Util.Companion.CrearPartida
import com.example.projectofinalfranciscocompose.Util.Companion.EscribirCarta
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.FirebaseDatabase

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
fun ListadoPartidas(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier
            .background(colorResource(R.color.fondo))
            .fillMaxSize()
    ) { innerPadding ->
        var context = LocalContext.current
        var partidacreada: Partida = Partida()

        var cantidad_jugadores by remember { mutableStateOf(2) }
        var precio by remember { mutableStateOf(0) }
        var nombre by remember { mutableStateOf("") }
        var tiempo by remember { mutableStateOf(5) }




        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding()
                .background(colorResource(R.color.fondo2))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //Pondremos una imagen centrar que tendra el icono de partida
//                Image(ImageBitmap.imageResource(R.drawable.partida), contentDescription = "partida", modifier = Modifier.size(200.dp)))
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        ImageBitmap.imageResource(R.drawable.imagenpartidauno),
                        contentDescription = "partida",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }



                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre De la Partida") },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .border(2.dp, Color.Black),
                        //desactivamos el teclado
                        singleLine = true,
                        maxLines = 1,

                        )

                    TextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = cantidad_jugadores.toString(),
                        onValueChange = {
                            if (it.isNotEmpty() && it.toIntOrNull() != null) {
                                cantidad_jugadores = it.toString().toInt()
                            } else {

                                Toast.makeText(
                                    context,
                                    "Solo puedes introducir numeros",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        },
                        label = { Text("Maximo de jugadores") },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .border(2.dp, Color.Black),

                        )
                    TextField(
                        value = precio.toString(),
                        onValueChange = {
                            if (it.isNotEmpty() && it.toIntOrNull() != null) {

                                precio = it.toString().toInt()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Solo puedes introducir numeros",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        },
                        label = { Text("Precio €") },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .border(2.dp, Color.Black),

                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = tiempo.toString(),
                        onValueChange = {
                            if (it.isNotEmpty() && it.toIntOrNull() != null) {
                                tiempo = it.toString().toInt()
                            } else {

                                Toast.makeText(
                                    context,
                                    "Solo puedes introducir numeros",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        label = { Text("Tiempo máximo para entrar en la partida(min)") },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .border(2.dp, Color.Black),

                        )

                    TextButton(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
                        //comprobamos que existan valores en los campos
                        db_ref = FirebaseDatabase.getInstance().getReference()
                        if (nombre.isEmpty() || cantidad_jugadores.toString()
                                .isEmpty() || precio.toString().isEmpty()
                        ) {
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
                        } else if (cantidad_jugadores > 24) {
                            Toast.makeText(
                                context,
                                "El numero de jugadores no puede ser mayor a 24",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@TextButton
                        } else if (precio.toString().toDoubleOrNull() == null) {
                            Toast.makeText(
                                context,
                                "El precio tiene que ser un numero",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            db_ref.child("Uno").child("Partidas").get().addOnSuccessListener {
                                var sharedPreferences: SharedPreferences =
                                    context.getSharedPreferences("comun", 0)
                                var user = sharedPreferences.getString("id_creador", "")
                                var id_partida = ""
                                id_partida =
                                    db_ref.child("Uno").child("Partidas").push().key.toString()
                                //crearemos la partida
                                for (i in it.children) {
                                    val partida = i.getValue(Partida::class.java)
                                    if (partida?.nombre == nombre) {
                                        Toast.makeText(
                                            context,
                                            "Ya existe una partida con ese nombre",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@addOnSuccessListener
                                    }
                                }
                                partidacreada.nombre = nombre
                                partidacreada.precio = precio
                                partidacreada.cantidad_jugadores = cantidad_jugadores
                                partidacreada.tiempo_espera_partida = tiempo
                                partidacreada.id_creador = user.toString()
                                partidacreada.id_partida = id_partida
                                //La lista de jugadores tiene que tener un maximo de la cantidad de jugadores
                                partidacreada.lista_jugadores = mutableListOf()
                                //Con un boton se le añadiran los jugadores
                                //si se repite el nombre de la partida se le mostrara un mensaje de error


                                CrearPartida(db_ref, id_partida, partidacreada)
                                Toast.makeText(context, "Partida Creada", Toast.LENGTH_SHORT)
                                    .show()


                            }

                        }


                    }) {

                        Text(
                            text = "Crear Partida",
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
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview7() {
    ProjectoFinalFranciscoComposeTheme {
        ListadoPartidas()
    }
}