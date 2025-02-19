package com.example.projectofinalfranciscocompose
import android.os.Parcelable
import java.io.Serializable


data class UsuarioLogin(
    val nombre: String?="",
    val username: String?="",
    val email: String?="",
    val password: String?="",
    val fechaNacimiento: String?="",
    val tipo: Int?=0,
    //tipo 1 será un jugador y tipo 2 será un administrador

    ): Serializable

