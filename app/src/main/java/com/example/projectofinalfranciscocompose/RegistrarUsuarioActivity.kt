package com.example.projectofinalfranciscocompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectofinalfranciscocompose.Util.Companion.escribirUsuario
import com.example.projectofinalfranciscocompose.Util.Companion.existeUsuario
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarUsuarioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.fondo))){
                        ModoregistroUsuario(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModoregistroUsuario( modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize())
    {
        Image(
            ImageBitmap.imageResource(R.drawable.usuario), "",
            modifier
                .fillMaxWidth()
                .height(220.dp)
        )
        var text_name by remember { mutableStateOf(" ") }
        var text_username by remember { mutableStateOf(" ") }
        var text_email by remember { mutableStateOf(" ") }
        var text_password by remember { mutableStateOf(" ") }
        var text_fechaNacimiento by remember { mutableStateOf(" ") }
        var db_ref: DatabaseReference= FirebaseDatabase.getInstance().getReference()
        var Usuarios: MutableList<UsuarioLogin> = mutableListOf()
        db_ref.child("Uno").child("Usuarios").get().addOnSuccessListener {
            for (i in it.children) {
                val usuario = i.getValue(UsuarioLogin::class.java)
                if (usuario != null) {
                    Usuarios.add(usuario)
                }
            }
        }
        //Crear una referencia a la base de datos
        TextField(
            value = text_name,
            onValueChange = { text_name = it },
            label = { Text("Nombre")},
            //elevation 4 dp

            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),

            )
        TextField(
            value = text_fechaNacimiento,
            onValueChange = { text_fechaNacimiento = it },
            label = { Text("Fecha de nacimiento")},
            //elevation 4 dp

            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),

            )

        TextField(
            value = text_username,
            onValueChange = { text_username = it },
            label = { Text("Username")},
            //elevation 4 dp

            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),

            )
        TextField(
            value = text_email,
            onValueChange = { text_email = it },
            label = { Text("E-mail")},
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),
        )
        TextField(
            value = text_password,
            onValueChange = { text_password = it },
            label = { Text("Password")},
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),

            )
        Row(modifier = Modifier
            .wrapContentWidth()
            .align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                val usuario = UsuarioLogin(text_name, text_username, text_email, text_password, text_fechaNacimiento)

                if(text_email.isEmpty() || text_password.isEmpty() || text_username.isEmpty() || text_name.isEmpty() || text_fechaNacimiento.isEmpty()){
                    Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()

                }else if(text_password.length <8){
                        Toast.makeText(context, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
                }else if(!text_email.contains("@") && !text_email.contains(".") ) {
                    Toast.makeText(context, "El email no es válido", Toast.LENGTH_SHORT).show()

                }else if(text_name.length < 3){
                    Toast.makeText(context, "El nombre debe tener al menos 3 caracteres", Toast.LENGTH_SHORT).show()
                }else{

                         if(existeUsuario(Usuarios, text_username)){
                             Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                         }else{

                            escribirUsuario(db_ref, text_username, usuario)
                            val intent = Intent(context, RegistroActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                        }
                }
            },Modifier.padding(end = 10.dp))
            {
                Text("Registrarse")
            }
            }
        }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    ProjectoFinalFranciscoComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(Modifier
                .fillMaxSize()
                .background(colorResource(R.color.fondo))){
                ModoregistroUsuario(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}