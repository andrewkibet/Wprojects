package com.kk.projectman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.kk.projectman.ui.ui.theme.ProjectManTheme

class ProjectList : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(
                            title = { Text(text = "All Projects") },
                            modifier = Modifier.fillMaxWidth()

                        )
                        ProjectListContent()

                    }}


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
            LazyColumn(Modifier.fillMaxSize()){
                items(projectListState.projectList){project ->
                    ProjectFields(project)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectFields(project: Project) {
    var projectType by remember { mutableStateOf(project.projectType) }
    var projectName by remember { mutableStateOf(project.projectName) }
    var tenderName by remember { mutableStateOf(project.tenderName) }
    var tenderPhoneNumber by remember { mutableStateOf(project.tenderPhoneNumber) }
    var tenderEmail by remember { mutableStateOf(project.tenderEmail) }
    var budget by remember { mutableStateOf(project.budget) }


    Column {
        TextField(
            value = projectType.orEmpty(),
            onValueChange = { projectType = it },
            label = { Text("Project Type") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        TextField(
            value = projectName.orEmpty(),
            onValueChange = { projectName = it },
            label = { Text("Project Name") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        TextField(
            value = tenderName.orEmpty(),
            onValueChange = { tenderName = it },
            label = { Text("Tender Name") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        TextField(
            value = tenderPhoneNumber.orEmpty(),
            onValueChange = { tenderPhoneNumber = it },
            label = { Text("Tender Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = tenderEmail.orEmpty(),
            onValueChange = { tenderEmail = it },
            label = { Text("Tender Email") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        TextField(
            value = budget.orEmpty(),
            onValueChange = { budget = it },
            label = { Text("Budget") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
    }}



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
        val tenderCollectionRef = firestore.collection("tenderp")
        val tenderProjectsTask = tenderCollectionRef.get()

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

        ProjectListContent()

}
