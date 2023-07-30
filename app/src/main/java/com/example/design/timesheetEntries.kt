package com.example.design

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class timesheetEntries : AppCompatActivity() {
    lateinit var uploadImage: ImageView
    lateinit var saveButton: Button
    lateinit var selectDate: EditText
    lateinit var selectStartTime: EditText
    lateinit var selectEndTime: EditText
    lateinit var selectDescription: EditText
    lateinit var selectCategory: EditText
    lateinit var imageURL: String
    lateinit var uri: Uri

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_entries)

        uploadImage = findViewById(R.id.uploadImage1)
        selectDate = findViewById(R.id.dateStartTime)
        selectStartTime = findViewById(R.id.dateDescription)
        selectEndTime = findViewById(R.id.endTimeEditText)
        selectDescription = findViewById(R.id.descriptionEditText)
        selectCategory = findViewById(R.id.categoryEditText)
        saveButton = findViewById(R.id.saveButton)

        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                uri = data?.data!!
                uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this@timesheetEntries, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }

        uploadImage.setOnClickListener { view ->
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        saveButton.setOnClickListener { view ->
            saveData()
        }


    }

    private fun saveData() {
        val storageReference =
            uri.lastPathSegment?.let {
                FirebaseStorage.getInstance().getReference().child("Android Images")
                    .child(it)
            }

        val builder = AlertDialog.Builder(this@timesheetEntries)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        if (storageReference != null) {
            storageReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isComplete);
                val urlImage = uriTask.result
                imageURL = urlImage.toString()
                uploadData()
                dialog.dismiss()
            }.addOnFailureListener { e ->
                dialog.dismiss()
            }
        }
    }

    private fun uploadData() {
        val date = selectDate.text.toString()
        val startTime = selectStartTime.text.toString()
        val endTime = selectEndTime.text.toString()
        val description = selectDescription.text.toString()
        val category = selectCategory.text.toString()
        val dataClass = TimesheetDataClass()

        dataClass.dateLabel = date
        dataClass.dateStartTime = startTime
        dataClass.dateEndTime = endTime
        dataClass.dateDescription = description
        dataClass.dateCategory = category
        dataClass.dateImage = imageURL

        val tutorialsReference = FirebaseDatabase.getInstance().getReference("Android Tutorials")
        tutorialsReference.child(title as String).setValue(dataClass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@timesheetEntries, "Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this@timesheetEntries, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // Example function to save the photo to a file
    private fun savePhotoToFile(bitmap: Bitmap?): File {
        // Create a file with a unique name to store the photo
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "IMG_$timeStamp.jpg"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(storageDir, fileName)

        // Save the bitmap to the file
        try {
            val outputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file
    }

    // Example function to get the file path from a content URI
    private fun getPathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }
}
