package com.example.projectofinalfranciscocompose
import android.os.Parcelable
import java.io.Serializable


data class UsuarioLogin(
    val nombre: String?="",
    val username: String?="",
    val email: String?="",
    val password: String?="",
    val fechaNacimiento: String?="",
    val id: String?=""
    ): Serializable

