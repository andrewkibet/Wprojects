package com.kiplele.project
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.kiplele.project.ui.theme.ProjectTheme

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            ProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen() {
        val context = LocalContext.current

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

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

            LoginButton(
                email = email,
                password = password,
                onLoginClick = { loginUser(context, email, password) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegistrationLink(
                isRegisterClicked = { isRegisterClicked -> loginUser(context, email, password, isRegisterClicked) }
            )
        }
    }

    @Composable
    private fun LoginButton(email: String, password: String, onLoginClick: () -> Unit) {
        Button(
            onClick = { onLoginClick() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Login")
        }
    }

    @Composable
    private fun RegistrationLink(isRegisterClicked: (Boolean) -> Unit) {
        val context = LocalContext.current
        Text(
            text = "Don't have an account?",
            modifier = Modifier.clickable {
                // Handle the click event here if needed
                // Navigate to the registration page
                val intent = Intent(context, RegistrationActivity::class.java)
                context.startActivity(intent)

                // Notify the state that registration is clicked
                isRegisterClicked(true)
            }
        )
    }

    private fun loginUser(context: Context, email: String, password: String, isRegisterClicked: Boolean = false) {
        if (isRegisterClicked) {
            return // Do nothing if the user clicked on the registration link
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(context, MainActivity::class.java)
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
    ProjectTheme {
        LoginActivity().LoginScreen()
    }
}
