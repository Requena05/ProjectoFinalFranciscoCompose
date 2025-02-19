package com.example.projectofinalfranciscocompose

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectofinalfranciscocompose.Util.Companion.escribirUsuario
import com.example.projectofinalfranciscocompose.Util.Companion.existeUsuario
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import com.google.firebase.FirebaseApp

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.text.format
import androidx.compose.material3.DatePicker as DatePicker


lateinit var db_ref: DatabaseReference

class RegistrarUsuarioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        db_ref = FirebaseDatabase.getInstance().reference


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
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

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
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
        var text_name by remember { mutableStateOf("") }
        var text_username by remember { mutableStateOf("") }
        var text_email by remember { mutableStateOf("") }
        var text_password by remember { mutableStateOf("") }




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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),

            )

            var showDatePicker by remember { mutableStateOf(false) }
            var selectedDate by remember { mutableStateOf("") }
            val datePickerState = rememberDatePickerState()

            Column (
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
                Button(onClick = { showDatePicker = true }, modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    border = ButtonDefaults.outlinedButtonBorder) {
                    Text("Seleccionar Fecha de Nacimiento", color = Color.Black)
                }
                if (selectedDate.isNotEmpty()) {
                    Text(
                        text = "Fecha seleccionada: $selectedDate",
                        modifier = Modifier
                            .background(Color.White)
                            .border(1.dp, Color.Black)
                            .padding(16.dp),
                        fontSize = 18.sp,
                    )
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        Button(onClick = {
                            showDatePicker = false
                            val selectedDateMillis = datePickerState.selectedDateMillis
                            if (selectedDateMillis != null) {
                                val formatter = SimpleDateFormat("dd/MM/yyyy")
                                selectedDate = formatter.format(java.util.Date(selectedDateMillis))
                            }
                        }) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDatePicker = false }) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier.padding(bottom = 10.dp),
                        title = { Text("Fecha de Nacimiento") }
                    )

                }
        }


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
        //Necesito un Spinner para seleccionar el tipo de usuario
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf("Elige el tipo de Usuario") }
        val options = listOf("Usuario Normal", "Usuario Administrador")
        var tipo=0
        Column(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = { },
                    label = { Text("Tipo de Usuario") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                selectedOptionText = it
                                expanded = false
                                Log.d("Tipo", selectedOptionText)

                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,


                        )

                    }
                }
            }
        }
        Row(modifier = Modifier
            .wrapContentWidth()
            .align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                Log.d("Tipo22", selectedOptionText)

                if (selectedOptionText.equals("Usuario Administrador")){
                    tipo = 2
                }else {
                tipo = 1
                }


                if(text_email.isEmpty() || text_password.isEmpty() || text_username.isEmpty() || text_name.isEmpty() || selectedDate.isEmpty()){
                    Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()

                }else if(text_password.length <8){
                        Toast.makeText(context, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
                }else if(!text_email.contains("@") && !text_email.contains(".") ) {
                    Toast.makeText(context, "El email no es válido", Toast.LENGTH_SHORT).show()

                }else if(text_name.length < 3){
                    Toast.makeText(context, "El nombre debe tener al menos 3 caracteres", Toast.LENGTH_SHORT).show()
                }else{

                         if(existeUsuario(Usuarios, text_username,)){
                             Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                         }else{
                             val usuario = UsuarioLogin(text_name, text_username, text_email, text_password, selectedDate,tipo)
                            escribirUsuario(db_ref, text_username,usuario)
                             if(tipo==1){
                                 var sp: SharedPreferences = context.getSharedPreferences("comun", 0)
                                 var db_ref = FirebaseDatabase.getInstance().getReference()
                                 var user = sp.getString("username", null)
                                 Util.CrearMazo(db_ref, user.toString(), Mazo(user.toString()))
                             }


                             val intent = Intent(context, MainActivity::class.java)
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
    db_ref = FirebaseDatabase.getInstance().reference
    var contexto=LocalContext.current
    FirebaseApp.initializeApp(contexto.applicationContext)
    ProjectoFinalFranciscoComposeTheme {

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(

                Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.fondo))){
                ModoregistroUsuario(
                    modifier = Modifier.padding(innerPadding)
                )

            }
        }
    }
}