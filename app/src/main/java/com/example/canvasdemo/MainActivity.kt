package com.example.canvasdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.canvasdemo.ui.theme.CanvasdemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasdemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TransformableCanvasScreen()
                }
            }
        }
    }
}

@Composable
fun TransformableCanvasScreen() {
    // 🔹 States controlled by sliders
    var scaleValue by remember { mutableStateOf(1f) }
    var rotationValue by remember { mutableStateOf(0f) }
    var translateX by remember { mutableStateOf(0f) }
    var translateY by remember { mutableStateOf(0f) }

    // ✅ Load the image here (inside Composable)
    val imageBitmap = ImageBitmap.imageResource(id = android.R.drawable.ic_menu_camera)

    Column(modifier = Modifier.fillMaxSize()) {
        // --- Canvas Area ---
        Canvas(
            modifier = Modifier
                .weight(1f) // take remaining screen
                .fillMaxWidth()
                .background(Color(0xFFEFEFEF))
        ) {
            // Apply transformations collectively
            scale(scale = scaleValue) {
                translate(left = translateX, top = translateY) {
                    rotate(degrees = rotationValue) {
                        drawAllMyShapes(imageBitmap)
                    }
                }
            }
        }

        // --- Controls (Sliders) ---
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Scale: ${String.format("%.2f", scaleValue)}")
            Slider(
                value = scaleValue,
                onValueChange = { scaleValue = it },
                valueRange = 0.5f..2f
            )

            Text("Rotation: ${rotationValue.toInt()}°")
            Slider(
                value = rotationValue,
                onValueChange = { rotationValue = it },
                valueRange = 0f..360f
            )

            Text("Translate X: ${translateX.toInt()} px")
            Slider(
                value = translateX,
                onValueChange = { translateX = it },
                valueRange = -200f..200f
            )

            Text("Translate Y: ${translateY.toInt()} px")
            Slider(
                value = translateY,
                onValueChange = { translateY = it },
                valueRange = -200f..200f
            )
        }
    }
}

// 🔹 Function that actually draws shapes
fun DrawScope.drawAllMyShapes(imageBitmap: ImageBitmap) {
    // Circle
    drawCircle(
        color = Color.Red,
        radius = 100f,
        center = Offset(200f, 200f)
    )

    // Rectangle
    drawRoundRect(
        color = Color.Blue,
        topLeft = Offset(100f, 400f),
        size = Size(300f, 150f),
        cornerRadius = CornerRadius(20f, 20f)
    )

    // Line
    drawLine(
        color = Color.Green,
        start = Offset(100f, 600f),
        end = Offset(400f, 800f),
        strokeWidth = 10f
    )

    // Path (triangle)
    val path = Path().apply {
        moveTo(500f, 200f)
        lineTo(700f, 400f)
        lineTo(300f, 400f)
        close()
    }
    drawPath(
        path = path,
        color = Color.Magenta,
        style = Fill
    )

    // Image (now passed from Composable)
    drawImage(
        image = imageBitmap,
        topLeft = Offset(500f, 500f)
    )
}
