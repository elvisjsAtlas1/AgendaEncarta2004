package com.example.agenda10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.agenda10.ui.theme.Agenda10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Agenda10Theme {
                MyAppConHamburguesa()
            }
        }
    }
}

@Composable
fun MyAppConHamburguesa() {
    var selectedScreen by remember { mutableStateOf("inicio") }
    var menuAbierto by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.fillMaxSize()) {
            // Menu vertical izquierdo
            if (menuAbierto) {
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight()
                        .background(Color.LightGray)
                        .padding(8.dp)
                ) {
                    IconButton(onClick = { menuAbierto = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar menú")
                    }

                    Button(
                        onClick = { selectedScreen = "inicio" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Inicio")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Inicio")
                    }

                    Button(
                        onClick = { selectedScreen = "agenda" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = "Agenda")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agenda")
                    }

                    Button(
                        onClick = { selectedScreen = "enciclopedia" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Build, contentDescription = "Enciclopedia")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enciclopedia")
                    }

                    Button(
                        onClick = { selectedScreen = "actividades" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.List, contentDescription = "Actividades")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Actividades")
                    }

                    Button(
                        onClick = { selectedScreen = "estadistica" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Estadística")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Estadística")
                    }
                }
            }

            // Contenido principal
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                when(selectedScreen) {
                    "inicio" -> PantallaInicio()
                    "agenda" -> PantallaAgenda()
                    "enciclopedia" -> PantallaEnciclopedia()
                    "actividades" -> PantallaActividades()
                    "estadistica" -> PantallaEstadistica()
                }
            }
        }

        // Botón hamburguesa siempre visible en la esquina superior izquierda
        if (!menuAbierto) {
            IconButton(
                onClick = { menuAbierto = true },
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
            }
        }

        // --- Espacio para que el contenido no quede debajo del botón ---
        Spacer(modifier = Modifier.height(56.dp)) // Ajusta según tamaño del botón
    }
}


// ========== Tus pantallas ==========



// ========== BOTON DE INICIO FUNCIONALIDAD ==========

@Composable
fun PantallaInicio() {
    Text(text = "Bienvenido a la pantalla de Inicio")
}




// ========== BOTON DE AGENDA FUNCIONALIDAD ==========

data class Curso(
    val nombre: String,
    val docente: String,
    val dia: Int,       // 0 = lunes, 1 = martes...
    val horaInicio: Int,
    val horaFin: Int
)

// Ejemplo de data class para actividad
data class Actividad(
    val diaEntrega: String,
    val descripcion: String,
    val cursoRelacionado: String,
    val sesionCurso: String,
    val materiales: String
)

//Agregar en la enciclopedia la informacion necesaria

data class CarpetaEnciclopedia(
    val nombreCurso: String,
    val sesiones: MutableList<String> = mutableListOf() // más adelante pondrás documentos, imágenes, etc.
)


@Composable
fun PantallaAgenda() {
    var cursos by remember { mutableStateOf(listOf<Curso>()) }
    var actividades by remember { mutableStateOf(listOf(
        Actividad("Lunes", "Entrega tarea 1", "Matemática", "Sesión 1", "Documento PDF"),
        Actividad("Martes", "Estudio capítulo 2", "Física", "Sesión 2", "Video"),
        Actividad("Miércoles", "Laboratorio", "Química", "Sesión 1", "Imagen")
    )) }

    var mostrarDialogo by remember { mutableStateOf(false) }
    var nombreCurso by remember { mutableStateOf("") }
    var docenteCurso by remember { mutableStateOf("") }
    var diaCurso by remember { mutableStateOf("0") }   // 0 = lunes
    var horaInicio by remember { mutableStateOf("8") }
    var horaFin by remember { mutableStateOf("10") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // Botón para abrir formulario de agregar curso
        Button(onClick = { mostrarDialogo = true }) {
            Text("Crear Curso")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cuadro del horario
        Text("Horario de Cursos", style = MaterialTheme.typography.titleMedium)
        HorarioCuadro(cursos)

        Spacer(modifier = Modifier.height(24.dp))

        // Aquí iría tu bloque de actividades
        // Actividades Semanales con scroll horizontal y vertical
        Text("Actividades Semanales", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .border(1.dp, Color.Gray)
                .horizontalScroll(rememberScrollState()) // Scroll horizontal
        ) {
            Column {
                // Encabezado
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(vertical = 4.dp)
                ) {
                    Text("Día", modifier = Modifier.width(80.dp), fontWeight = FontWeight.Bold)
                    Text("Descripción", modifier = Modifier.width(200.dp), fontWeight = FontWeight.Bold)
                    Text("Curso", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Bold)
                    Text("Sesión", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Bold)
                    Text("Materiales", modifier = Modifier.width(150.dp), fontWeight = FontWeight.Bold)
                }

                // Lista scrollable vertical
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    actividades.forEach { actividad ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .border(0.5.dp, Color.Gray),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(actividad.diaEntrega, modifier = Modifier.width(80.dp))
                            Text(actividad.descripcion, modifier = Modifier.width(200.dp))
                            Text(actividad.cursoRelacionado, modifier = Modifier.width(120.dp))
                            Text(actividad.sesionCurso, modifier = Modifier.width(120.dp))
                            Text(actividad.materiales, modifier = Modifier.width(150.dp))
                        }
                    }
                }
            }
        }
    }

    // Diálogo para crear curso
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Nuevo Curso") },
            text = {
                Column {
                    OutlinedTextField(value = nombreCurso, onValueChange = { nombreCurso = it }, label = { Text("Nombre") })
                    OutlinedTextField(value = docenteCurso, onValueChange = { docenteCurso = it }, label = { Text("Docente") })
                    OutlinedTextField(value = diaCurso, onValueChange = { diaCurso = it }, label = { Text("Día (0=Lun,1=Mar,2=Mi,3=J,4=V)") })
                    OutlinedTextField(value = horaInicio, onValueChange = { horaInicio = it }, label = { Text("Hora Inicio (ej: 8)") })
                    OutlinedTextField(value = horaFin, onValueChange = { horaFin = it }, label = { Text("Hora Fin (ej: 10)") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    cursos = cursos + Curso(
                        nombre = nombreCurso,
                        docente = docenteCurso,
                        dia = diaCurso.toIntOrNull() ?: 0,
                        horaInicio = horaInicio.toIntOrNull() ?: 8,
                        horaFin = horaFin.toIntOrNull() ?: 10
                    )
                    mostrarDialogo = false
                    nombreCurso = ""
                    docenteCurso = ""
                    diaCurso = "0"
                    horaInicio = "8"
                    horaFin = "10"
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )

    }

}


@Composable
fun HorarioCuadro(cursos: List<Curso>) {
    val dias = listOf("L", "Ma", "Mi", "J", "V")
    val horas = (0..23).map { "${it}:00" }

    // Scroll vertical y horizontal
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)
        .horizontalScroll(rememberScrollState())
        .verticalScroll(rememberScrollState())
    ) {
        Column {
            // Encabezado con días
            Row {
                Box(modifier = Modifier.width(50.dp)) { Text("") } // esquina vacía
                dias.forEach { dia ->
                    Box(modifier = Modifier.width(80.dp), contentAlignment = Alignment.Center) {
                        Text(dia)
                    }
                }
            }

            // Filas de horas
            horas.forEachIndexed { index, hora ->
                Row {
                    Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center) {
                        Text(hora)
                    }
                    dias.indices.forEach { diaIndex ->
                        // Verificar si hay curso en esta celda
                        val curso = cursos.find { it.dia == diaIndex && index in it.horaInicio until it.horaFin }
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(40.dp)
                                .border(1.dp, Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            if (curso != null) {
                                Text(curso.nombre, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}









// ========== BOTON DE ENCICLOPEDIA FUNCIONALIDAD ==========

@Composable
fun PantallaEnciclopedia() {
    Text(text = "Enciclopedia de contenidos")
}

// ========== BOTON DE ACTIVIDADES FUNCIONALIDAD ==========

@Composable
fun PantallaActividades() {
    Text(text = "Listado de Actividades")
}

// ========== BOTON DE ESTADISTICA FUNCIONALIDAD ==========

@Composable
fun PantallaEstadistica() {
    Text(text = "Tus Estadísticas")
}