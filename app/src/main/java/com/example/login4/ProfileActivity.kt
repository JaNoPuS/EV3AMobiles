package com.example.login4

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        // Mostrar la foto y el nombre del usuario de Google
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)

        val user = auth.currentUser
        if (user != null) {
            nameTextView.text = user.displayName ?: "Usuario"
            emailTextView.text = user.email ?: "Correo no disponible"
            val photoUrl = user.photoUrl
            if (photoUrl != null) {
                Picasso.get()
                    .load(photoUrl)
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .error(R.drawable.ic_profile_placeholder)
                    .into(profileImageView)
            } else {
                profileImageView.setImageResource(R.drawable.ic_profile_placeholder)
            }
        }

        // Configurar el RecyclerView con el resumen de experiencias
        val experiencesRecyclerView = findViewById<RecyclerView>(R.id.experiencesRecyclerView)
        experiencesRecyclerView.layoutManager = LinearLayoutManager(this)

        val experiences = listOf(
            Experience("Cancún - Playa Acapulco", "Día soleado en la playa", "Detalles de la experiencia en Cancún..."),
            Experience("Los Andes - Montañas Nevadas", "Aventura en la nieve", "Detalles de la experiencia en Los Andes..."),
            Experience("Brasil - Selva Amazónica", "Exploración de la biodiversidad", "Detalles de la experiencia en el Amazonas..."),
            Experience("Valparaíso - Ciudad Histórica", "Recorrido cultural", "Detalles de la experiencia en Valparaíso...")
        )


        // Configuración de la barra de navegación inferior
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Navega a la pantalla de bienvenida
                    val intent = Intent(this, ActivityWelcome::class.java)
                    startActivity(intent)
                    finish() // Cierra esta actividad
                    true
                }
                R.id.navigation_new_trip -> {
                    // Navega a la pantalla de creación de nuevo viaje
                    val intent = Intent(this, CreateTripActivity::class.java)
                    startActivity(intent)
                    finish() // Cierra esta actividad
                    true
                }
                R.id.navigation_logout -> {
                    // Cierra sesión y regresa a la pantalla principal
                    auth.signOut()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Cierra esta actividad
                    true
                }
                else -> false
            }
        }
    }
}
