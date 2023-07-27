package com.kiplele.project

//import BackgroundImage
import android.content.Context
import android.content.Intent

import android.app.Activity

import android.graphics.BitmapFactory
import android.graphics.Paint.Align


import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.Role.Companion.Image

import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.kiplele.project.ui.theme.ProjectTheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class AdminPage : ComponentActivity() {

    private var selectedImageUri: Uri? = null

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }
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

    var selectedProjectType by remember { mutableStateOf("") }
    var projectName by remember { mutableStateOf("") }
    var tenderName by remember { mutableStateOf("") }
    var tenderPhoneNumber by remember { mutableStateOf("") }
    var tenderEmail by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    val projectTypes = listOf("Health", "Road", "Agriculture", "Schools")


    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }



    Box(modifier = Modifier.fillMaxSize()) {
        // BackgroundImage()


        Column(
            modifier = Modifier
                .padding(16.dp),
                //.verticalScroll(rememberScrollState()),

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

            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }

                bitmap.value?.let { btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp)
                            .padding(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Pick Image")
            }

            Button(onClick = {selectUpload()},
                   modifier = Modifier.align(Alignment.Start))
            {
                Text(text = "Upload")

            }

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

fun selectUpload() {
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
