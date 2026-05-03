package org.example.project.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ui.login.AppColors

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateBackToLogin: () -> Unit
) {
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var confirmPasswordInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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

            Text(
                text = "UnpinIt",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.PinterestRed,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Create an\nAccount",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.TextDark,
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

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
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = AppColors.OutlineGray,
                    focusedBorderColor = AppColors.PinterestRed
                )
            )

            // Confirm Password Input
            OutlinedTextField(
                value = confirmPasswordInput,
                onValueChange = { confirmPasswordInput = it },
                placeholder = { Text("Confirm Password", color = Color.Gray) },
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

            // Display error message if validation fails
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Register Button
            Button(
                onClick = {
                    if (emailInput.isBlank() || passwordInput.isBlank()) {
                        errorMessage = "Please fill in all fields."
                    } else if (passwordInput != confirmPasswordInput) {
                        errorMessage = "Passwords do not match."
                    } else {
                        errorMessage = ""
                        onRegisterSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.PinterestRed),
                shape = RoundedCornerShape(50)
            ) {
                Text("Sign Up", fontSize = 16.sp, color = Color.White)
            }

            // Modern Inline Action Link using standard Text and LinkAnnotation
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray, fontSize = 14.sp)) {
                    append("Already have an account? ")
                }

                // Define the clickable behavior using the new LinkAnnotation API
                val loginLink = LinkAnnotation.Clickable(
                    tag = "login_action",
                    linkInteractionListener = {
                        onNavigateBackToLogin()
                    }
                )

                // Apply the link to the specific text portion
                withLink(loginLink) {
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.PinterestRed,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Log In")
                    }
                }
            }

            // A standard Text component now inherently handles the click logic
            Text(
                text = annotatedText,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}