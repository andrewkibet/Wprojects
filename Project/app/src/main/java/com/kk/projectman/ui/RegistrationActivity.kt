package com.kk.projectman.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.kk.projectman.LoginActivity
import com.kk.projectman.R

//import com.kiplele.project.ui.theme.ProjectTheme

class RegistrationActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegistrationScreen()
                }

        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RegistrationScreen() {
        val context = LocalContext.current as Activity
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var selectedRole by remember { mutableStateOf("") }
        // val roleOptions = listOf("MCA", "Citizen", "Tenderer", "ProjectAdmin")


        // MutableState variables to track field validity
        var isNameValid by remember { mutableStateOf(true) }
        var isEmailValid by remember { mutableStateOf(true) }
        var isPasswordValid by remember { mutableStateOf(true) }

        Box(modifier = Modifier.fillMaxSize()) {
            BackgroundImage()
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = { Text(text = "Ward Projects") },
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        // isError = !isNameValid, // Set error state based on validity
                        //errorMessage = if (!isNameValid) "Please enter your name" else null
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation() // Hide password characters

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Handle registration button click
                            if (name.isNotEmpty() && email.isNotEmpty() && password.length >= 6) {
                                registerUser(context, email, password, selectedRole)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please fill in all fields and ensure the password is at least 6 characters long.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Register")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Already have an Account?")
                    val isRegisterClicked = remember{ mutableStateOf(false) }
                    Text(text = "Login Here.",
                        fontStyle = FontStyle.Italic,
                        color =if (isRegisterClicked.value) Color.Red else Color.Blue,
                        modifier = Modifier.clickable {
                            isRegisterClicked.value = true
                            // Handle the click event here if needed
                            // Navigate to the login page
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }

    private fun registerUser(
        context: Activity,
        email: String,
        password: String,
        role: String
    ) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {

                    // Registration success, handle the registered user here
                    Toast.makeText(
                        context,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Navigate to the login page
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                    context.finish() // Optionally finish the current activity
                } else {
                    // Registration failed, handle the error here
                    Toast.makeText(
                        context,
                        "Registration Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    @Composable
    fun BackgroundImage() {
        Image(
            painter = painterResource(id = R.drawable.walpaper),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun RegistrationScreenPreview() {

            RegistrationActivity().RegistrationScreen()

    }
}