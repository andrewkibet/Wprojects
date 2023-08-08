package com.kk.projectman
import android.content.Intent
import android.graphics.Insets.add
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.ui.tooling.data.EmptyGroup.name
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


import com.kk.projectman.R

class Tendepreneurs : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var button: Button
    private lateinit var imageview: ImageView
    private  lateinit var chat: EditText
    private lateinit var phone: EditText

    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tendepreneurs)

        fab = findViewById(R.id.fab)
        imageview = findViewById(R.id.img3)
        button = findViewById(R.id.btn)

        chat = findViewById(R.id.chating)
        phone = findViewById(R.id.pnnumber)

        // Initialize Firebase Firestore and Firebase Storage
        firebaseFirestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().reference

        // Set a click listener to the FAB
        fab.setOnClickListener {
            // Launch image picker to select an image
            pickImage.launch("image/*")
        }
        button.setOnClickListener {
            // Upload the displayed image to Firebase Storage
            if (imageUri != null) {
                uploadImageToStorage()
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Activity result contract for image picking
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Image picked, save the URI
            imageUri = uri
            displayImagePreview()
            // Upload the image to Firestore Storage
            uploadImageToStorage()
        } else {
            // Image picking was canceled or failed
            Toast.makeText(this, "Image picking failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayImagePreview() {
        if (imageUri != null) {
            // Show the ImageView and set the image resource to the selected image
            imageview.visibility = View.VISIBLE
            imageview.setImageURI(imageUri)
        } else {
            // Hide the ImageView if no image is selected
            imageview.visibility = View.GONE
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
        val firestore = FirebaseFirestore.getInstance()
        val chatText = chat.text.toString()
        val phoneNumber = phone.text.toString()

        // Create a data object with the fields you want to save to Firestore
        val data = hashMapOf(
            "imageUrl" to imageUrl,
            "chatText" to chatText,
            "phoneNumber" to phoneNumber
        )

        // Add the data to the "tenderp" collection in Firestore
        firestore.collection("tenderp")
            .add(data)
            .addOnSuccessListener {
                // Data added successfully
                Toast.makeText(this, "Data added to Firestore", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Error occurred while adding data
                Toast.makeText(this, "Error adding data to Firestore", Toast.LENGTH_SHORT).show()
            }
    }

}
