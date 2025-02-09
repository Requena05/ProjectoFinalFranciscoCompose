package com.example.projectofinalfranciscocompose

import com.google.firebase.database.DatabaseReference

class Util {
    companion object {
        fun existeUsuario(Usuarios: MutableList<UsuarioLogin>, username: String): Boolean {
            return Usuarios.any{ it.username.toString() ==username }
        }
        fun escribirUsuario(db_ref: DatabaseReference, id: String, Usuarios: UsuarioLogin) {
            db_ref.child("Uno").child("Usuarios").child(id).setValue(Usuarios)
        }
        fun existecarta(Cartas: MutableList<Carta>, id: String): Boolean {
            return Cartas.any{ it.id.toString() ==id }

        }
        fun escribirCarta(db_ref: DatabaseReference, id: String, Cartas: Carta) {
            db_ref.child("Uno").child("Tienda").child("Carta").child(id).setValue(Cartas)
        }

    }
}