package com.example.projectofinalfranciscocompose

import android.util.Log
import com.google.firebase.database.DatabaseReference

class Util {
    companion object {
        fun existeUsuario(Usuarios: MutableList<UsuarioLogin>, username: String): Boolean {
            return Usuarios.any{ it.username.toString() ==username }
        }
        fun escribirUsuario(db_ref: DatabaseReference, id: String, Usuarios: UsuarioLogin) {
            db_ref.child("Uno").child("Usuarios").child(id).setValue(Usuarios)
        }

        fun EscribirCarta(db_ref: DatabaseReference, id: String, carta: Carta) {

            db_ref.child("Uno").child("Tienda").child(id).setValue(carta)
        }
        fun borrarCarta(db_ref: DatabaseReference, id: String) {
            db_ref.child("Uno").child("Tienda").child(id).removeValue()
        }
        fun PublicarCarta(db_ref: DatabaseReference, id: String, carta: Carta) {
            if (carta.publicada == true) {
                db_ref.child("Uno").child("Publicacion").child(id).setValue(carta)
            }


        }
        fun BorrarPublicacion(db_ref: DatabaseReference, id: String) {
            db_ref.child("Uno").child("Publicacion").child(id).removeValue()
        }
        fun EditarCarta(db_ref: DatabaseReference, id: String, carta: Carta) {
            db_ref.child("Uno").child("Tienda").child(id).removeValue()
            db_ref.child("Uno").child("Tienda").child(id).setValue(carta)

        }
        fun CrearPartida(db_ref: DatabaseReference, id: String, partida: Partida) {
            db_ref.child("Uno").child("Partidas").child(id).setValue(partida)
        }




        fun CrearMazo(db_ref: DatabaseReference, id: String, mazo: Mazo) {
            db_ref.child("Uno").child("Mazos").child(id).setValue(mazo)
        }

        fun AgregarCartasalmazo(db_ref: DatabaseReference, id: String, carta: Carta) {
            db_ref.child("Uno").child("Mazos").child(id).child("lista_cartas").child(carta.id_creador.toString()).setValue(carta)
            //si ya existe la lista dentro de la base de datos se añade la carta

            Log.d("carta",carta.toString())
        }
        fun BorrarCartasalmazo(db_ref: DatabaseReference, id: String, carta: Carta) {
            //Tenemos que añadir la carta a publicacion
            db_ref.child("Uno").child("Publicacion").child(carta.id.toString()).setValue(carta)
            //Eliminamos la carta del mazo
            db_ref.child("Uno").child("Mazos").child(id).child("lista_cartas").child(carta.id.toString()).removeValue()
        }

    }
}