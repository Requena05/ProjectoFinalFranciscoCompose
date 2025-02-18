package com.example.projectofinalfranciscocompose

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

data class Carta(
    var id:String?="",
    var numero:Int?=0,
    var Precio: Int?=0,
    var Nombre:String?="",
    var descripcion:String?="",
    var id_creador:String?="",
    var imagen:String?="",
    var publicada:Boolean?=false


): Serializable {
}
