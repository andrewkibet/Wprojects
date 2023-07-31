package com.kiplele.project
/*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kiplele.project.ui.theme.ProjectTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UiContent()
                }
            }
        }
    }
}

@Composable
fun UiContent() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var fetchedImageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val imageUri: Uri? = null // Set your image URI here

    // Fetch the image from Firebase Storage
    LaunchedEffect(Unit) {
        fetchedImageBitmap = fetchImageFromFirebase(imageUri)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally // Center-align the elements vertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center // Center-align the elements horizontally
        ) {
            // Center-align the "Title", "Image", and "Image Name"
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Title",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )

                fetchedImageBitmap?.let { bitmap ->
                    Image(bitmap = bitmap, contentDescription = "Image")
                }

                Text(
                    text = "Image Name",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Left-align the "Projects" text
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Text(
                    text = "Projects",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            val intent = Intent(context, ProjectList::class.java)
                            context.startActivity(intent)
                        }
                )
            }

            item {
                Text(
                    text = "Stakeholders",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            item {
                Text(
                    text = "News",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            item {
                Text(
                    text = "Admin",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            val intent = Intent(context, AdminPage::class.java)
                            context.startActivity(intent)
                        }
                )
            }
        }
    }
}

private const val MAX_IMAGE_SIZE_BYTES = 4 * 1024 * 1024 // 4MB (Adjust as needed)

private suspend fun fetchImageFromFirebase(imageUri: Uri?): ImageBitmap? {
    if (imageUri == null) return null

    val firebaseStorage = Firebase.storage
    val storageRef = firebaseStorage.reference
    val imageRef = storageRef.child("project_images/${System.currentTimeMillis()}_${imageUri.lastPathSegment}")

    return try {
        val byteArray = imageRef.getBytes(MAX_IMAGE_SIZE_BYTES).await()
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        // Handle the error if fetching the image fails
        null
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectTheme {
        UiContent()
    }
}
*/