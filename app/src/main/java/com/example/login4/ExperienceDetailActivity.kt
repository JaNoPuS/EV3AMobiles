package com.example.login4

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExperienceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience_detail)

        // Obtener datos de la experiencia desde el intent
        val title = intent.getStringExtra("EXPERIENCE_TITLE") ?: "Título no disponible"
        val description = intent.getStringExtra("EXPERIENCE_DESCRIPTION") ?: "Descripción no disponible"

        // Configurar los TextView con los detalles de la experiencia
        findViewById<TextView>(R.id.detailTitleTextView).text = title
        findViewById<TextView>(R.id.detailDescriptionTextView).text = description
    }
}
