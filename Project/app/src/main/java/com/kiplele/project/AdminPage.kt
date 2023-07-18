package com.kiplele.project

//import BackgroundImage
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.kiplele.project.ui.theme.ProjectTheme
import java.io.ByteArrayOutputStream
import java.util.UUID

class AdminPage : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                AdminPageContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPageContent() {
    val context = LocalContext.current
    var selectedProjectType by remember { mutableStateOf("") }
    var projectName by remember { mutableStateOf("") }
    var tenderName by remember { mutableStateOf("") }
    var tenderPhoneNumber by remember { mutableStateOf("") }
    var tenderEmail by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    val projectTypes = listOf("Health", "Road", "Agriculture", "Schools")


    Box(modifier = Modifier.fillMaxSize()) {
        // BackgroundImage()


        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()){

            }
            DropdownMenu(
                expanded = false, // Set to true when the dropdown is open
                onDismissRequest = { /* Handle dismiss request */ },
                modifier = Modifier.fillMaxWidth()
            ) {
/* projectTypes.forEach { projectType ->
     DropdownMenuItem(
         onClick = {
             selectedProjectType = projectType // Update the selected project type when clicked
         }
     ) {
         Text(projectType)
     }
 }

 */
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = projectName,
                onValueChange = { projectName = it },
                label = { Text("Project Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = tenderName,
                onValueChange = { tenderName = it },
                label = { Text("Tender Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = tenderPhoneNumber,
                onValueChange = { tenderPhoneNumber = it },
                label = { Text("Tender Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = tenderEmail,
                onValueChange = { tenderEmail = it },
                label = { Text("Tender Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = budget,
                onValueChange = { budget = it },
                label = { Text("Budget") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Save the details in Firestore
                    saveDetailsToFirestore(
                        context,
                        selectedProjectType,
                        projectName,
                        tenderName,
                        tenderPhoneNumber,
                        tenderEmail,
                        budget,

                        )
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
        }

    }


}



private fun saveDetailsToFirestore(
    context: Context,
    projectType: String,
    projectName: String,
    tenderName: String,
    tenderPhoneNumber: String,
    tenderEmail: String,
    budget: String
) {
    val firestore = FirebaseFirestore.getInstance()

    val data = hashMapOf(
        "projectType" to projectType,
        "projectName" to projectName,
        "tenderName" to tenderName,
        "tenderPhoneNumber" to tenderPhoneNumber,
        "tenderEmail" to tenderEmail,
        "budget" to budget
    )



    firestore.collection("adminProjects")
        .add(data)
        .addOnSuccessListener {
            // Data saved successfully
            Toast.makeText(context, "Details saved successfully", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            // Error occurred while saving data
            Toast.makeText(context, "Error saving details", Toast.LENGTH_SHORT).show()
        }
}

@Preview(showBackground = true)
@Composable
fun AdminPageContentPreview() {
    ProjectTheme {
        AdminPageContent()
    }
}
