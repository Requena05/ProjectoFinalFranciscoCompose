package com.example.projectofinalfranciscocompose.ui.theme

import java.io.Serializable

data class Partida(
    var id_partida:String?="",
    var lista_jugadores:ArrayList<String>?=ArrayList(),
    var max_jugadores:Int?=0,
    //variable para hacer una cuenta regresiva para cerrar la partida
    var tiempo:Int?=0,
):Serializable
