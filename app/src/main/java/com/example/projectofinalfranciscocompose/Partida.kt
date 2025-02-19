package com.example.projectofinalfranciscocompose

import java.io.Serializable

data class Partida(
    var id_partida:String?="",
    var nombre:String?="",
    var precio:Int?=0,
    var lista_jugadores:MutableList<String>?= mutableListOf(),
    var cantidad_jugadores:Int?=0,
    var tiempo_espera_partida:Int?=0,
    var id_creador:String?="",




    ):Serializable

