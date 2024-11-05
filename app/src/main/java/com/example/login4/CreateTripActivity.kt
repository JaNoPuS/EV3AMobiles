package com.example.login4

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CreateTripActivity : AppCompatActivity() {

    private lateinit var selectImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var selectedImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_trip)

        // Configuración del BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    finish()
                    true
                }
                R.id.navigation_logout -> {
                    Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()
                    finish()
                    true
                }
                else -> false
            }
        }

        // Configuración del Spinner para seleccionar la moneda
        val currencySpinner = findViewById<Spinner>(R.id.currencySpinner)
        val currencyOptions = arrayOf("CLP", "USD")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencySpinner.adapter = adapter

        // Configuración del botón "Guardar Experiencia"
        val saveTripButton = findViewById<Button>(R.id.saveTripButton)
        saveTripButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.nameEditText).text.toString()
            val destination = findViewById<EditText>(R.id.destinationEditText).text.toString()
            val budget = findViewById<EditText>(R.id.budgetEditText).text.toString()
            val currency = currencySpinner.selectedItem.toString()
            val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()

            if (name.isNotEmpty() && destination.isNotEmpty() && budget.isNotEmpty() && description.isNotEmpty()) {
                // Crear Intent para enviar datos a ActivityWelcome
                val intent = Intent(this, ActivityWelcome::class.java).apply {
                    putExtra("EXTRA_TRIP_NAME", name)
                    putExtra("EXTRA_TRIP_DESTINATION", destination)
                    putExtra("EXTRA_TRIP_BUDGET", "$currency $budget")
                    putExtra("EXTRA_TRIP_DESCRIPTION", description)
                }

                startActivity(intent) // Iniciar ActivityWelcome con los datos
                finish() // Finalizar CreateTripActivity después de guardar
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuración del botón "Abrir Google Maps"
        val selectLocationButton = findViewById<Button>(R.id.openMapsButton)
        selectLocationButton.setOnClickListener {
            openGoogleMaps()
        }

        // ImageView para mostrar la imagen seleccionada (opcional)
        selectedImageView = findViewById(R.id.selectedImageView)

        // Configuración del botón "Subir Fotos" usando ActivityResultLauncher
        selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val selectedImageUri: Uri? = result.data?.data
                if (selectedImageUri != null) {
                    handleSelectedImageUri(selectedImageUri)
                }
            }
        }

        val uploadPhotosButton = findViewById<Button>(R.id.uploadPhotosButton)
        uploadPhotosButton.setOnClickListener {
            openGooglePhotos()
        }
    }

    private fun openGoogleMaps() {
        val destination = findViewById<EditText>(R.id.destinationEditText).text.toString()
        val gmmIntentUri = Uri.parse("geo:0,0?q=$destination")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "Google Maps no está instalado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGooglePhotos() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.setPackage("com.google.android.apps.photos")

        if (intent.resolveActivity(packageManager) != null) {
            selectImageLauncher.launch(intent)
        } else {
            Toast.makeText(this, "Google Fotos no está instalado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSelectedImageUri(uri: Uri) {
        val resolver: ContentResolver = contentResolver
        try {
            selectedImageView.setImageURI(uri)
            Toast.makeText(this, "Foto seleccionada: $uri", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
        }
    }
}
