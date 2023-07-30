package com.example.design

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class catergories : AppCompatActivity() {

    lateinit var saveButton: Button
    lateinit var uploadMin: EditText
    lateinit var uploadDesc: EditText
    lateinit var uploadMax: EditText
    lateinit var uploadName: EditText
    lateinit var uploadBud: EditText
    lateinit var imageURL: String
    lateinit var uri: Uri
    lateinit var uploadImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catergories)

        uploadImage = findViewById(R.id.uploadImage)
        uploadDesc = findViewById(R.id.descriptionEditText)
        uploadMin = findViewById(R.id.startTimeEditText)
        uploadMax = findViewById(R.id.endTimeEditText)
        uploadName = findViewById(R.id.dateEditText)
        uploadBud = findViewById(R.id.categoryEditText)
        saveButton = findViewById(R.id.saveButton)

      val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                uri = data?.data!!
                uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this@catergories, "No Image Selected", Toast.LENGTH_SHORT).show()
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
    fun saveData() {
        val storageReference =
            uri.lastPathSegment?.let {
                FirebaseStorage.getInstance().getReference().child("Android Images")
                    .child(it)
            }
        val builder = AlertDialog.Builder(this@catergories)
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

    fun uploadData() {
        val title = uploadName.text.toString()
        val desc = uploadDesc.text.toString()
        val min = uploadMin.text.toString().toInt()
        val Max = uploadMax.text.toString().toInt()
        val bud = uploadBud.text.toString().toInt()
        val dataClass = DataClass()

        dataClass.dataName = title
        dataClass.dataDesc = desc
        dataClass.dataMin = min
        dataClass.dataMax = Max
        dataClass.dataBudget = bud
        dataClass.dataImage = imageURL


        val tutorialsReference = FirebaseDatabase.getInstance().getReference("Android Tutorials")
        tutorialsReference.child(title).setValue(dataClass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@catergories, "Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this@catergories, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }




}

    /*var photo: ImageView? = null
    var Save: Button? = null
    var ptitle: EditText? = null
    var pdesc: EditText? = null
    var ploc: EditText? = null
    var pbud: EditText? = null
    var imageURL: String? = null
    var uri: Uri? = null
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_GALLERY = 2
    }

    private var photographPath: String? = null
    private val catergories = mutableListOf<CategoryEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catergories)

        val saveButton: Button = findViewById(R.id.saveButton)

        // Handle save button click
        saveButton.setOnClickListener {
            saveCategoryEntry()
        }
    }

    private fun takePhoto() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")

        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Select an option")
        builder.setItems(options) { dialog, item ->
            when (item) {
                0 -> {
                    // Take Photo
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                    }
                }

                1 -> {
                    // Choose from Gallery
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "image/*"
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Image"),
                        REQUEST_IMAGE_GALLERY
                    )
                }

                2 -> {
                    // Cancel
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun saveCategoryEntry() {
        val dateEditText: TextView = findViewById(R.id.dateEditText)
        val startTimeEditText: EditText = findViewById(R.id.startTimeEditText)
        val endTimeEditText: EditText = findViewById(R.id.endTimeEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val categoryEditText: EditText = findViewById(R.id.categoryEditText)

        val date = dateEditText.text.toString()
        val startTime = startTimeEditText.text.toString()
        val endTime = endTimeEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val category = categoryEditText.text.toString()

        // Validate the input fields (e.g., check if required fields are filled)
        if (date.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty() && description.isNotEmpty() && category.isNotEmpty()) {
            val timesheetEntry =
                CategoryEntry(date, startTime, endTime, description, category, photographPath)

            catergories.add(timesheetEntry) // Add the timesheet entry to the list
            val intent = Intent(this, AddCatergory::class.java)
            intent.putExtra("category_entry", ArrayList(catergories))
            startActivity(intent)
            // Save the timesheet entry to a database or perform any other necessary operations

            finish() // Close the activity after saving the timesheet entry
        } else {
            Toast.makeText(this, "Please fill in all the required fields.", Toast.LENGTH_SHORT)
                .show()
        }
        val sharedPreferences = getSharedPreferences("CategoryEntries", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Generate a unique key for the category entry
        val entryKey = UUID.randomUUID().toString()

        // Save the category entry data using the entryKey as the key in SharedPreferences
        editor.putString("$entryKey.date", date)
        editor.putString("$entryKey.startTime", startTime)
        editor.putString("$entryKey.endTime", endTime)
        editor.putString("$entryKey.description", description)
        editor.putString("$entryKey.category", category)
        editor.putString("$entryKey.photographPath", photographPath)

        // Apply the changes
        editor.apply()

        Toast.makeText(this, "Category entry saved successfully.", Toast.LENGTH_SHORT).show()

        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    // Image captured from camera
                    val thumbnail = data?.extras?.get("data") as Bitmap?
                    // Save the thumbnail to a file or do further processing

                    // Example: Save the photo to a file and store the file path
                    val photoFile = savePhotoToFile(thumbnail)
                    photographPath = photoFile.absolutePath
                }

                REQUEST_IMAGE_GALLERY -> {
                    // Image selected from gallery
                    val selectedImageUri = data?.data
                    // Handle the selected image URI

                    // Example: Get the selected image path and store it
                    val photoPath = selectedImageUri?.let { getPathFromUri(it) }
                    photographPath = photoPath
                }
            }
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
    private fun retrieveCategoryEntries(): List<CategoryEntry> {
        val sharedPreferences = getSharedPreferences("CategoryEntries", Context.MODE_PRIVATE)
        val categoryEntries = mutableListOf<CategoryEntry>()

        // Retrieve all keys from SharedPreferences
        val entryKeys = sharedPreferences.all.keys

        // Iterate through each key and fetch the corresponding category entry data
        for (key in entryKeys) {
            val entryDate = sharedPreferences.getString("$key.date", "")
            val entryStartTime = sharedPreferences.getString("$key.startTime", "")
            val entryEndTime = sharedPreferences.getString("$key.endTime", "")
            val entryDescription = sharedPreferences.getString("$key.description", "")
            val entryCategory = sharedPreferences.getString("$key.category", "")
            val entryPhotographPath = sharedPreferences.getString("$key.photographPath", "")

            // Create a CategoryEntry object and add it to the list
            if (entryDate != null && entryStartTime != null && entryEndTime != null &&
                entryDescription != null && entryCategory != null && entryPhotographPath != null
            ) {
                val categoryEntry = CategoryEntry(
                    entryDate, entryStartTime, entryEndTime,
                    entryDescription, entryCategory, entryPhotographPath
                )
                categoryEntries.add(categoryEntry)
            }
        }

        return categoryEntries
    }
}*/