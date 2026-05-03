package org.example.project.ui.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.example.project.ui.login.AppColors
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    var isPinterestMode by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val galleryManager = remember { LocalGalleryManager() }
    var imageStack by remember {
        mutableStateOf(
            try {
                galleryManager.getImagesPaths()
            } catch (_: Exception) {
                emptyList<String>()
            }
        )
    }

    val swipeX = remember { Animatable(0f) }
    val swipeY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val swipeThreshold = 400f

    fun removeTopCard() {
        if (imageStack.isNotEmpty()) {
            imageStack = imageStack.drop(1)
            coroutineScope.launch {
                swipeX.snapTo(0f)
                swipeY.snapTo(0f)
            }
        }
    }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text("Log Out")
            },
            text = {
                Text("Are you sure you want to log out of UnpinIt?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = AppColors.PinterestRed)
                ) {
                    Text("Log Out")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = AppColors.TextDark)
                }
            },
            containerColor = AppColors.LightBackground
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Log Out",
                            tint = AppColors.TextDark
                        )
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(
                            text = if (isPinterestMode) "Pinterest" else "Gallery",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                            color = AppColors.TextDark,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Switch(
                            checked = isPinterestMode,
                            onCheckedChange = { isPinterestMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = AppColors.PinterestRed,
                                uncheckedThumbColor = AppColors.PinterestRed,
                                uncheckedTrackColor = AppColors.OutlineGray
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.LightBackground)
            )
        },
        containerColor = AppColors.LightBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (imageStack.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset { IntOffset(swipeX.value.roundToInt(), swipeY.value.roundToInt()) }
                            .graphicsLayer(
                                rotationZ = swipeX.value / 25f
                            )
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            if (swipeX.value > swipeThreshold) {
                                                swipeX.animateTo(1500f, tween(300))
                                                removeTopCard()
                                            } else if (swipeX.value < -swipeThreshold) {
                                                swipeX.animateTo(-1500f, tween(300))
                                                removeTopCard()
                                            } else {
                                                launch { swipeX.animateTo(0f) }
                                                launch { swipeY.animateTo(0f) }
                                            }
                                        }
                                    },
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        coroutineScope.launch {
                                            swipeX.snapTo(swipeX.value + dragAmount.x)
                                            swipeY.snapTo(swipeY.value + dragAmount.y)
                                        }
                                    }
                                )
                            }
                    ) {
                        AsyncImage(
                            model = imageStack.first(),
                            contentDescription = "Current Pin Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(24.dp))
                                .border(
                                    width = 2.dp,
                                    color = AppColors.OutlineGray,
                                    shape = RoundedCornerShape(24.dp)
                                )
                        )
                    }
                } else {
                    Text(
                        text = "You're all caught up!",
                        color = Color.DarkGray,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        if (imageStack.isNotEmpty()) {
                            coroutineScope.launch {
                                swipeX.animateTo(-1500f, tween(300))
                                removeTopCard()
                            }
                        }
                    },
                    modifier = Modifier.size(80.dp, 60.dp),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, AppColors.PinterestRed)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AppColors.PinterestRed)
                    }
                }

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.size(80.dp, 60.dp),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Info, contentDescription = "Stats", tint = Color.DarkGray)
                    }
                }

                OutlinedButton(
                    onClick = {
                        if (imageStack.isNotEmpty()) {
                            coroutineScope.launch {
                                swipeX.animateTo(1500f, tween(300))
                                removeTopCard()
                            }
                        }
                    },
                    modifier = Modifier.size(80.dp, 60.dp),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0xFF4CAF50))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Favorite, contentDescription = "Keep", tint = Color(0xFF4CAF50))
                    }
                }
            }
        }
    }
}