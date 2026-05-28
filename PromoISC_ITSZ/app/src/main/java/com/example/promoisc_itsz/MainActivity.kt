package com.example.promoisc_itsz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF5F5F5)) {
                    NavHost(navController = navController, startDestination = "inicio") {
                        composable("inicio") { PantallaPrincipal(navController) }

                        // Ruta corregida para manejar el nombre de la imagen
                        composable("detalle/{titulo}/{contenido}/{imagenRes}") { backStack ->
                            val tit = backStack.arguments?.getString("titulo") ?: ""
                            val cont = backStack.arguments?.getString("contenido") ?: ""
                            val imgResName = backStack.arguments?.getString("imagenRes") ?: ""

                            val contexto = LocalContext.current
                            val imgResId = if (imgResName.isNotEmpty()) {
                                contexto.resources.getIdentifier(imgResName, "drawable", contexto.packageName)
                            } else 0

                            PantallaDetalle(navController, tit, cont, imgResId)
                        }
                        composable("encuesta") { PantallaEncuesta(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val contexto = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.mounstro))

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Títulos Centrados
            Text(
                text = "INGENIERÍA EN SISTEMAS",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF003366),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "ITS Zongolica",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB8860B),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(200.dp).padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Secciones con paso de imagen corregido
            BotonNavegacion(navController, "¿Qué aprenderás?", "Domina programación, IA, Ciberseguridad, Redes y Software.", "programacion")
            BotonNavegacion(navController, "Campo Laboral", "Google, Microsoft, Startups o tu propia Consultoría.", "g")
            BotonNavegacion(navController, "Especialidades", "Desarrollo Móvil, Cloud Computing y Ciencia de Datos.", "desarrollo")
            BotonNavegacion(navController, "Campus", "Zongolica, Orizaba, Tezonapa, Nogales, Soledad Atzompa y Acultzingo.", "a")
            BotonNavegacion(navController, "Otras Carreras", "Gestión Empresarial, Forestal, Desarrollo Comunitario e Innovación Agrícola.", "o")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("encuesta") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B396A))
            ) {
                Text("Realizar Test Vocacional", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://zongolica.tecnm.mx/?oferta-educativa=ing-en-sistemas-computacionales"))
                    contexto.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB8860B))
            ) {
                Text("Página oficial", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SECCIÓN DEL CÓDIGO QR ---
            ElevatedCard(
                modifier = Modifier.padding(horizontal = 8.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Escanea para la pag oficial",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Image(
                        painter = painterResource(id = R.drawable.qr),
                        contentDescription = "Código QR Plan de Estudios",
                        modifier = Modifier
                            .size(150.dp) // Tamaño del QR
                            .padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

        }


        Box(
            modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ExtendedFloatingActionButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:2786880820"))
                    contexto.startActivity(intent)
                },
                icon = { Icon(Icons.Filled.Call, contentDescription = null) },
                text = { Text("Llámanos") },
                containerColor = Color(0xFF1B396A),
                contentColor = Color.White
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
fun BotonNavegacion(navController: NavHostController, titulo: String, contenido: String, imagenResName: String) {
    ElevatedCard(
        onClick = { navController.navigate("detalle/$titulo/$contenido/$imagenResName") },
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = titulo, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), color = Color(0xFF003366))
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(navController: NavHostController, titulo: String, contenido: String, imagenResId: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imagenResId != 0) {
                Image(
                    painter = painterResource(id = imagenResId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = contenido, fontSize = 20.sp, lineHeight = 30.sp, color = Color.DarkGray, textAlign = TextAlign.Justify)
        }
    }
}

@Composable
fun PantallaEncuesta(navController: NavHostController) {
    var preguntaActual by remember { mutableStateOf(0) }
    var puntuacion by remember { mutableStateOf(0) }
    val preguntas = listOf(
        "¿Te gusta resolver problemas lógicos?",
        "¿Te interesa saber cómo funcionan las apps?",
        "¿Te gustaría crear tu propio software?",
        "¿Te atrae la Inteligencia Artificial?",
        "¿Te ves trabajando en tecnología en el futuro?"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp).statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (preguntaActual < preguntas.size) {
            Text("Pregunta ${preguntaActual + 1} de ${preguntas.size}", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(preguntas[preguntaActual], fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { puntuacion++; preguntaActual++ }, modifier = Modifier.fillMaxWidth()) { Text("Sí") }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(onClick = { preguntaActual++ }, modifier = Modifier.fillMaxWidth()) { Text("No") }
        } else {
            val porcentaje = (puntuacion.toFloat() / preguntas.size.toFloat() * 100).toInt()
            Text("¡Resultado Final!", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color(0xFF003366))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Respuestas buenas: $puntuacion de ${preguntas.size}", fontSize = 18.sp)
            Text("Tu afinidad es del:", fontSize = 18.sp)
            Text("$porcentaje%", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB8860B))

            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.popBackStack() }) { Text("Volver al Inicio") }
        }
    }
}