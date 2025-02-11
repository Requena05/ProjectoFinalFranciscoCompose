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
import androidx.compose.runtime.Composable
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
import com.example.projectofinalfranciscocompose.Util.Companion.EscribirCarta
import com.example.projectofinalfranciscocompose.Util.Companion.Existecarta
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.FirebaseDatabase

class AñadirCartaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CrearCarta(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CrearCarta(modifier: Modifier = Modifier) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.fondo))) {
        var cartacreada:MutableList<Carta>
        cartacreada= mutableListOf()

        //HAZ esta imagen invisible
        var posiblecartas:MutableList<String>
        posiblecartas= mutableListOf()
        posiblecartas.add("Rojo")
        posiblecartas.add("Azul")
        posiblecartas.add("Amarillo")
        posiblecartas.add("Verde")
        var Numero by remember { mutableStateOf(6) }
        var precio by remember { mutableStateOf("") }
        var descripcion by remember { mutableStateOf("") }
        var currentIndex by remember { mutableStateOf(0) }
        var nombre by remember { mutableStateOf("") }
        var context = LocalContext.current
        when(posiblecartas[currentIndex]) {
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
        Box(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 40.dp)) {
            Image(
                ImageBitmap.imageResource(posiblecartas[currentIndex].toInt()), contentDescription = "",
                modifier = Modifier
                    .height(300.dp)
                    .width(185.dp)
                    .border(2.dp, Color.Black)

            )

            Text(
                text = "$Numero\n--",
                color = Color.Black,
                fontSize = 50.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier.padding(7.dp)

            )
            Text(
                text = "$Numero",
                color = Color.Black,
                fontSize = 160.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier
                    //El numero tiene que tener color por dentro
                    .padding(7.dp).align(Alignment.Center)
                ,

            )
            Text(
                text = "$Numero \n--",
                color = Color.Black,
                fontSize = 50.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                modifier = Modifier.rotate(180f)
                    .padding(7.dp)
                    .align(Alignment.BottomEnd)

            )
        }
            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                Row (modifier = Modifier.align(Alignment.CenterHorizontally)){
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
                    Button(modifier = Modifier.padding(end = 8.dp).width(70.dp),onClick={
                        Numero++
                        if(Numero==10){
                            Numero=0
                        }

                    }) {
                        Text("+",modifier = Modifier.align(Alignment.CenterVertically))
                    }
                    Button(modifier = Modifier.padding(start = 8.dp).width(70.dp),onClick={
                        Numero--
                        if(Numero==-1){
                            Numero=9
                        }
                    }) {
                        Text("-",modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }

                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre De la Carta")},
                    modifier=Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                )

                TextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripcion")},
                   modifier=Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                )
                TextField(
                    value = "$precio",
                    onValueChange = { precio = it.replace(",",".")},
                    label = { Text("Precio €")},
                    modifier=Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                )
            }
            db_ref = FirebaseDatabase.getInstance().getReference()

        TextButton(modifier = Modifier.align(Alignment.CenterHorizontally),onClick ={
            db_ref.child("Uno").child("Usuarios").get().addOnSuccessListener {
                var sharedPreferences:SharedPreferences=context.getSharedPreferences("username", MODE_PRIVATE)
               var user= sharedPreferences.getString("username","")
                Log.d("user",user.toString())
                for (i in it.children) {
                    val usuario = i.getValue(UsuarioLogin::class.java)
                    if (usuario != null && usuario.username==user.toString()) {
                        Log.d("duro",posiblecartas[currentIndex].toString())
                        var id_carta=db_ref.child("Uno").child("Tienda").push().key.toString()
                        cartacreada.add(Carta(user.toString(), Numero,precio,nombre,descripcion,id_carta,posiblecartas[currentIndex]))
                        EscribirCarta(db_ref,id_carta,cartacreada[0])
                        Toast.makeText(context, "Carta Creada", Toast.LENGTH_SHORT).show()
                    }
                    cartacreada.clear()

                }


            }




        }) {

            Text(
                text = "Crear Carta"
                , modifier = modifier
                    //tiene que estar al centro
                    .background(Color.White)
                    .border(2.dp, Color.Black)
                    .wrapContentWidth()
                    .padding(16.dp)
                ,
                fontSize = 22.sp,
            )}

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