package com.example.projectofinalfranciscocompose

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
