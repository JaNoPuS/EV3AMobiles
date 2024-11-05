package com.example.login4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.login4.R

class ExperiencesHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiences_history)

        // Configura los botones "Ver" para abrir la actividad con los detalles de cada experiencia
        setupViewButtons()

        // Configuración de la barra de navegación inferior
        setupBottomNavigation()
    }

    private fun setupViewButtons() {
        // Lista de botones "Ver" en la actividad para cada experiencia
        val buttons = listOf(
            findViewById<Button>(R.id.viewButton1),
            findViewById<Button>(R.id.viewButton2),
            findViewById<Button>(R.id.viewButton3),
            findViewById<Button>(R.id.viewButton4)
        )

        // Configura el clic para cada botón "Ver"
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                // Título y descripción específicos para cada lugar
                val (title, description) = when (index) {
                    0 -> "Playa de Cancún" to "Disfruta de la arena blanca y aguas cristalinas de la Playa de Cancún."
                    1 -> "Montañas de los Andes" to "Explora las impresionantes montañas de los Andes y disfruta de su majestuosa vista."
                    2 -> "Selva Amazónica" to "Sumérgete en la biodiversidad de la selva amazónica y descubre su vida salvaje única."
                    3 -> "Ciudad Histórica" to "Recorre una ciudad llena de historia y cultura, con siglos de arquitectura y arte."
                    else -> "Experiencia" to "Descripción de la experiencia."
                }

                // Abre la actividad de detalles en pantalla completa
                val intent = Intent(this, ExperienceDetailActivity::class.java).apply {
                    putExtra("EXPERIENCE_TITLE", title)
                    putExtra("EXPERIENCE_DESCRIPTION", description)
                }
                startActivity(intent)
            }
        }
    }

    private fun setupBottomNavigation() {
        // Configuración de la barra de navegación inferior
        val bottomNavigationView =
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, ActivityWelcome::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.navigation_new_trip -> {
                    startActivity(Intent(this, CreateTripActivity::class.java))
                    finish()
                    true
                }

                R.id.navigation_logout -> {
                    finish()
                    true
                }

                else -> false
            }
        }
    }
}
