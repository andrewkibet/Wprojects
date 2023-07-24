package com.kiplele.project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
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
                )
                {
                    UiContent()
                }
            }
        }
    }
}

// ... (previous code)

// ... (previous code)

@Composable
fun UiContent() {
    val context = LocalContext.current

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

                Image(
                    painter = painterResource(id = R.drawable.walpaper),
                    contentDescription = "Image",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(200.dp)
                        .aspectRatio(1f)
                )

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
        Text(
            text = "Projects",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        // Left-align the "Stakeholders" text
        Text(
            text = "Stakeholders",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        // Left-align the "News" text
        Text(
            text = "News",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Projects",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .clickable {
                    val intent = Intent(context, ProjectList::class.java)
                    context.startActivity(intent)
                }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectTheme {
        UiContent()
    }
}
