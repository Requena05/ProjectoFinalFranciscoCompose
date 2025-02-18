package com.example.projectofinalfranciscocompose

import java.io.Serializable

data class Mazo(
    var id_propietario:String?=" ",
    var lista_cartas:MutableList<Carta>?= mutableListOf()

    ):Serializable {

}
