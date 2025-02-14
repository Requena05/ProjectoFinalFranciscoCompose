package com.example.projectofinalfranciscocompose

import java.io.Serializable

data class Partida(
    var id_partida:String?="",
    var lista_jugadores:MutableList<UsuarioLogin>?= mutableListOf(),
    var tiempo_partida:Int?=0,
    var id_creador:String?="",




):Serializable
