package org.example.project.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Temporary mock user data
object MockUser {
    const val EMAIL = "test"
    const val PASSWORD = "123"
}

// Brand Colors based on the document UI
object AppColors {
    val PinterestRed = Color(0xFFC95A66)
    val LightBackground = Color(0xFFFDFDFD)
    val TextDark = Color(0xFF1E1E1E)
    val OutlineGray = Color(0xFFE0E0E0)
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onPinterestConnect: () -> Unit
) {
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppColors.LightBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Title Placeholder (Replace with Logo Image later)
            Text(
                text = "UnpinIt",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.PinterestRed,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Connect to\nUnpinIt",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.TextDark,
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Pinterest OAuth Button (UC-3: Connect Pinterest)
            Button(
                onClick = onPinterestConnect,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.PinterestRed),
                shape = RoundedCornerShape(50)
            ) {
                Text("Continue with Pinterest", fontSize = 16.sp, color = Color.White)
            }

            // Divider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = AppColors.OutlineGray)
                Text(
                    text = "or",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Gray
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = AppColors.OutlineGray)
            }

            // Email Input
            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                placeholder = { Text("Email", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = AppColors.OutlineGray,
                    focusedBorderColor = AppColors.PinterestRed
                )
            )

            // Password Input
            OutlinedTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                placeholder = { Text("Password", color = Color.Gray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = AppColors.OutlineGray,
                    focusedBorderColor = AppColors.PinterestRed
                )
            )

            if (loginError) {
                Text(
                    text = "Invalid credentials.",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Log In Button
            Button(
                onClick = {
                    if (emailInput == MockUser.EMAIL && passwordInput == MockUser.PASSWORD) {
                        loginError = false
                        onLoginSuccess()
                    } else {
                        loginError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.PinterestRed),
                shape = RoundedCornerShape(50)
            ) {
                Text("Log In", fontSize = 16.sp, color = Color.White)
            }

            // Sign Up Button
            OutlinedButton(
                onClick = onNavigateToSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.PinterestRed),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AppColors.PinterestRed)
            ) {
                Text("Sign Up", fontSize = 16.sp)
            }
        }
    }
}