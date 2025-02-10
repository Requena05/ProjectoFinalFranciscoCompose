package com.example.projectofinalfranciscocompose

import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

data class Carta(
    var id:String?="",
    var numero:Int?=0,
    var Colores:String?="",
    var Tipo:String?="",
    var Precio:Int?=0,
    var Nombre:String?="",
    var descripcion:String?="",
    var id_creador:String?="",


): Serializable
{
    init {
        var  db_ref = FirebaseDatabase.getInstance().getReference()

//        id=db_ref.child("Uno").child("Tienda").child("Carta")
    }
}