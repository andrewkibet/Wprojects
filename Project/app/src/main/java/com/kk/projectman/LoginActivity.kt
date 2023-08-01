package com.kk.projectman
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
//import com.kk.projectman.ui.theme.ProjectTheme
import com.kk.projectman.ui.RegistrationActivity

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen() {
        val context = LocalContext.current
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val isRegisterClicked = remember { mutableStateOf(false) }

        var isLoading by remember { mutableStateOf(false) } // Added a state to track loading state


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))



            Button(
                onClick = {
                    // Handle login button click
                    loginUser(context, email, password)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Don't have Account?")
            Text(text = "Register Here?",
                fontStyle = FontStyle.Italic,
                color =if (isRegisterClicked.value) Color.Red else Color.Blue,
                modifier = Modifier.clickable {
                    isRegisterClicked.value = true
                    // Handle the click event here if needed
                    // Navigate to the login page
                    val intent = Intent(context, RegistrationActivity::class.java)
                    context.startActivity(intent)
                }
            )
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())


            }}
    }

    private fun loginUser(context: Context, email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(context, ImagesActivity::class.java)
                    context.startActivity(intent)
                    context.finish() // Optionally finish the current activity
                    // Login success, handle the authenticated user here
                } else {
                    // Login failed, handle the error here
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
        LoginActivity().LoginScreen()

}
