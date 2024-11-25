package com.example.cookit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookit.R
import com.example.cookit.ui.theme.Sino1
import com.example.cookit.ui.theme.Sino2
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Singnscreen(modifier: Modifier = Modifier, navController: NavController, onSignInSuccess: () -> Unit) {
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

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
            // Sign-in Image
            Image(
                painter = painterResource(R.drawable.singninimg),
                contentDescription = "Sign-in Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(top = 16.dp)
            )

            // Title
            Text(
                text = "Welcome Back!",
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
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon") },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = Icons.Default.Lock,
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
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // Sign-In Button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // User signed in successfully
                                    if (auth.currentUser?.isEmailVerified == true) {
                                        message = "Sign-In Successful!"
                                        onSignInSuccess()
                                    } else {
                                        message = "Please verify your email before signing in."
                                    }
                                } else {
                                    message = "Sign-In Failed: ${task.exception?.message}"
                                }
                            }
                    } else {
                        message = "Please fill in all fields."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Sign In", color = Color.White, fontSize = 16.sp)
            }

            // Display message
            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.startsWith("Sign-In Successful")) Color.Green else Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
