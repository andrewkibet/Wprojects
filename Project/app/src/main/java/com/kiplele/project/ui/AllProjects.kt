import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kiplele.project.ui.theme.ProjectTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllProjects : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ProjectListContent()
                }
            }
        }
    }
}

@Composable
fun ProjectListContent() {
    val projectListState = rememberProjectListState()

    when {
        projectListState.isLoading -> {
            // Show loading indicator
            Text(text = "Loading...")
        }
        projectListState.error != null -> {
            // Show error message
            Text(text = "Error: ${projectListState.error}")
        }
        else -> {
            // Display the fetched data
            Column {
                projectListState.projectList.forEach { project ->
                    Text(project.toString())
                }
            }
        }
    }
}

@Composable
fun rememberProjectListState(): ProjectListState {
    val projectListState = remember { ProjectListState() }

    LaunchedEffect(Unit) {
        projectListState.fetchData()
    }

    return projectListState
}

class ProjectListState {
    var isLoading by mutableStateOf(true)
        private set
    var projectList by mutableStateOf(emptyList<Project>())
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun fetchData() {
        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection("adminProjects")

        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                val projects = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Project::class.java)
                }

                projectList = projects
                isLoading = false
            }
            .addOnFailureListener { exception ->
                error = "Error fetching data: ${exception.message}"
                isLoading = false
            }
    }
}

data class Project(
    val projectType: String? = null,
    val projectName: String? = null,
    val tenderName: String? = null,
    val tenderPhoneNumber: String? = null,
    val tenderEmail: String? = null,
    val budget: String? = null
)

@Preview(showBackground = true)
@Composable
fun ProjectListContentPreview() {
    ProjectTheme {
        ProjectListContent()
    }
}
