import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kk.projectman.R

class Tendepreneurs : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton

    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tendepreneurs)

        fab = findViewById(R.id.fab)

        // Initialize Firebase Firestore and Firebase Storage
        firebaseFirestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().reference

        // Set a click listener to the FAB
        fab.setOnClickListener {
            // Launch image picker to select an image
            pickImage.launch("image/*")
        }
    }

    // Activity result contract for image picking
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Image picked, save the URI
            imageUri = uri
            // Upload the image to Firestore Storage
            uploadImageToStorage()
        } else {
            // Image picking was canceled or failed
            Toast.makeText(this, "Image picking failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageToStorage() {
        if (imageUri != null) {
            val imageRef = storageRef.child("images/${imageUri?.lastPathSegment}")

            imageRef.putFile(imageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    // Image uploaded successfully, get the download URL
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        // The download URL of the image
                        val imageUrl = downloadUri.toString()

                        // Now you can use the imageUrl to store it in Firestore or perform any other action
                        // For example, you can save the URL to Firestore as follows:
                        saveImageUrlToFirestore(imageUrl)
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle the error if the image upload fails
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveImageUrlToFirestore(imageUrl: String) {
        // Here you can save the imageUrl to Firestore along with other data
        // For example, you can use firebaseFirestore.collection("tendepreneurs").document().set(...)
        // to store the imageUrl and other data in Firestore.
        // Implement the logic according to your use case.
    }
}
