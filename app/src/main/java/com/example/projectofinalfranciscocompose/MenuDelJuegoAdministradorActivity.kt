package com.example.projectofinalfranciscocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
//import para poder alinear el contenido del Scafold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectofinalfranciscocompose.ui.theme.ProjectoFinalFranciscoComposeTheme
import kotlinx.coroutines.launch

class MenuDelJuegoAdministradorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectoFinalFranciscoComposeTheme {
                MenuDelAdministrador()
            }
        }
    }
}

@Composable
fun MenuDelAdministrador( modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .background(colorResource(R.color.gray))
                    .padding(16.dp),
                //Cambiar el color del fondo del drawer
                drawerContainerColor = colorResource(R.color.black)
            ) {
                // Drawer content
            }
        },
        modifier = Modifier.background(colorResource(R.color.fondo))
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = {},
                    icon = { Icon(ImageBitmap.imageResource(R.drawable.menus), contentDescription = " ", modifier = Modifier.padding(10.dp)) },
                    onClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    }
                    ,modifier = Modifier.width(100.dp))
            }
        ) { contentPadding ->
            Text(
                text = "menu del juego",modifier=Modifier.padding(contentPadding)
            )
        }
    }


}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview4() {
    ProjectoFinalFranciscoComposeTheme {
        MenuDelAdministrador()
    }
}