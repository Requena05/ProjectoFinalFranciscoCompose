package com.example.projectofinalfranciscocompose

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectofinalfranciscocompose.Util.Companion.existeUsuario
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.core.content.edit

class RegistroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column( Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.fondo)))
                    {
                        Registodelusuario(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Registodelusuario(modifier: Modifier = Modifier) {
    //Centraremos una imagen dentro de un Column para que ocupe la mitad de la pantalla
    //Justo debajo existiran un formulario de registro para el usuario con dos botones para iniciar sesion y registrarse
   val context = LocalContext.current
    Column(Modifier.fillMaxSize())
    {
        Image(ImageBitmap.imageResource(R.drawable.usuario), "",
            modifier
                .fillMaxWidth()
                .height(220.dp)
        )
        var text_username by remember { mutableStateOf("") }
        var text_email by remember { mutableStateOf("") }
        var text_password by remember { mutableStateOf("") }
        var  db_ref = FirebaseDatabase.getInstance().getReference()
        var Usuarios: MutableList<UsuarioLogin> = mutableListOf()
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),

        )
        Row(modifier = Modifier
            .wrapContentWidth()
            .align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                //Si se pulsa aqui el voton iniciar sesion desaparece y se añaden tres campos al formulario
                // Los campos son el nombre, apellidos y fecha de nacimiento
                val intent = Intent(context, RegistrarUsuarioActivity::class.java)
                //que no se vea la animación de transición
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                context.startActivity(intent)

            },Modifier.padding(end = 10.dp))
            {
                Text("Registrarse")
            }
            Button(onClick = {
                if(text_email.isEmpty() || text_password.isEmpty() || text_username.isEmpty()){
                 Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
//                }else if(){
                } else {
                    Usuarios.clear()
                    db_ref.child("Uno").child("Usuarios").get().addOnSuccessListener {
                        for (i in it.children) {
                            val usuario = i.getValue(UsuarioLogin::class.java)
                            if (usuario != null &&
                                usuario.username.toString().equals(text_username) &&
                               usuario.password.toString().equals(text_password) &&
                               usuario.email.toString().equals(text_email) ) {
                                Usuarios.add(usuario)
                            }

                        }
                        Log.d("Usuarios", Usuarios.toString())
                        Log.d("Usuarios", Usuarios.size.toString())
                        Log.d("Existe", existeUsuario(Usuarios, text_username).toString())
                        if(existeUsuario(Usuarios,text_username)){
                            //Se comprueba que tipo de usuario es para redirigir a la pantalla correspondiente
                            db_ref.child("Uno").child("Usuarios").child(text_username).get().addOnSuccessListener {
                                val usuario=Usuarios.find { it.username == text_username }
                                Log.d("Usuario", usuario.toString())
                                if (usuario != null) {
                                    if(usuario.tipo ==1 && usuario.username.toString().equals(text_username) && usuario.password.toString().equals(text_password) && usuario.email.toString().equals(text_email) ){
                                        val intent = Intent(context, MenuDelJuegoUsuarioActivity::class.java)
                                        context.startActivity(intent)

                                        }else if (usuario.tipo==2 &&  usuario.username.toString().equals(text_username) && usuario.password.toString().equals(text_password) && usuario.email.toString().equals(text_email)){
                                        val intent = Intent(context, MenuDelJuegoAdministradorActivity::class.java)
                                        //que no se vea la animación de transición
                                        var id_usuario: SharedPreferences=context.getSharedPreferences("username",0)
                                        id_usuario.edit().putString("username", text_username).apply()
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                        context.startActivity(intent)
                                    }else{
                                        Toast.makeText(context,"Username, Email o Contraseña incorrectos", Toast.LENGTH_SHORT).show()
                                    }






                                }

                            }



                        }else{
                            Toast.makeText(context, "El usuario no existe", Toast.LENGTH_SHORT).show()
                        }
                    }


                }

            },Modifier.padding(start = 10.dp))
            {
                Text("Iniciar sesion")
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    ProjectoFinalFranciscoComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column( Modifier
                .fillMaxSize()
                .background(colorResource(R.color.fondo)))
            {
                Registodelusuario(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}