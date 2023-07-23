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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiplele.project.ui.theme.ProjectTheme
import com.kiplele.project.ui.theme.Typography
import com.kiplele.project.ui.ui.theme.Typography

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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                       // verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Title",
                           // style = MaterialTheme.typography.h4,
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
                         //   style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Projects")
                        Text(text = "Stakeholders")
                        Text(text = "News")
                        Text(text = "Projects",

                            modifier = Modifier
                                .padding(16.dp)
                                .size(200.dp)
                                .aspectRatio(1f)
                                .clickable { val intent = Intent(this@MainActivity, ProjectList::class.java)
                                    startActivity(intent) }
                        )



                    }
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectTheme {
        MainActivity()
    }
}
