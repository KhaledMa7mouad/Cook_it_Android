package com.example.cookit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookit.R
import com.example.cookit.ui.theme.Sino1
import com.example.cookit.ui.theme.Sino2
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(modifier: Modifier = Modifier, navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Sino1, Sino2)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Signup Image
            Image(
                painter = painterResource(R.drawable.signupimg),
                contentDescription = "Signup Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            // Title
            Text(
                text = "Get Started Free",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Lock else Icons.Default.Lock,
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // Confirm Password Input
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Lock else Icons.Default.Lock,
                            contentDescription = "Toggle Confirm Password Visibility"
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // Sign Up Button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                        scope.launch {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Send email verification
                                        val user = auth.currentUser
                                        user?.sendEmailVerification()
                                            ?.addOnCompleteListener { emailTask ->
                                                if (emailTask.isSuccessful) {
                                                    message = "Verification email sent! Please check your inbox and then sign in."
                                                } else {
                                                    message = "Failed to send verification email: ${emailTask.exception?.message}"
                                                }
                                            }
                                    } else {
                                        message = "Sign-up failed: ${task.exception?.message}"
                                    }
                                }
                        }
                    } else {
                        message = "Please fill in all fields and ensure passwords match."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Sign Up", color = Color.White, fontSize = 16.sp)
            }

            // Message Display
            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Text(
                text = "Already have an account?",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        navController.navigate("signin")
                    },
                fontSize = 16.sp,

                textDecoration = TextDecoration.Underline, // Only needed once
                color = Color.White
            )

        }
    }
}
