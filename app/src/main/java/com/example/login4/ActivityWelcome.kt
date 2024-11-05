package com.example.login4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.example.login4.ProfileActivity




class ActivityWelcome : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        auth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        )

        // Mostrar el nombre y foto del usuario
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        val userProfileImage = findViewById<ImageView>(R.id.userProfileImage)
        val user = auth.currentUser
        welcomeTextView.text = user?.displayName
        val photoUrl = user?.photoUrl

        // Cargar la foto de perfil con Picasso
        if (photoUrl != null) {
            Picasso.get()
                .load(photoUrl)
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .into(userProfileImage)
        } else {
            userProfileImage.setImageResource(R.drawable.ic_profile_placeholder)
        }

        // Obtener datos del Intent
        val tripName = intent.getStringExtra("EXTRA_TRIP_NAME") ?: "Nombre no disponible"
        val tripDestination = intent.getStringExtra("EXTRA_TRIP_DESTINATION") ?: "Destino no disponible"
        val tripBudget = intent.getStringExtra("EXTRA_TRIP_BUDGET") ?: "Presupuesto no disponible"
        val tripDescription = intent.getStringExtra("EXTRA_TRIP_DESCRIPTION") ?: "Descripción no disponible"

        // Mostrar datos del viaje
        findViewById<TextView>(R.id.tripNameTextView).text = "Nombre: $tripName"
        findViewById<TextView>(R.id.tripDestinationTextView).text = "Destino: $tripDestination"
        findViewById<TextView>(R.id.tripBudgetTextView).text = "Presupuesto: $tripBudget"
        findViewById<TextView>(R.id.tripDescriptionTextView).text = "Descripción: $tripDescription"

        // Acción para el botón Publicar
        findViewById<Button>(R.id.publishButton).setOnClickListener {
            Toast.makeText(this, "Tus Amigos Ya Pueden Ver Tu Experiencia", Toast.LENGTH_SHORT).show()
        }

        // Acción para el botón Historial de Experiencias
        val historyButton = findViewById<Button>(R.id.historyButton)
        historyButton.setOnClickListener {
            val intent = Intent(this, ExperiencesHistoryActivity::class.java)
            startActivity(intent)
        }

        // Acción para el botón Mis Amigos
        val friendsButton = findViewById<Button>(R.id.friendsButton)
        friendsButton.setOnClickListener {
            val intent = Intent(this, FriendsActivity::class.java)
            startActivity(intent)
        }

// Acción para el botón Mi Perfil
        val profileButton = findViewById<Button>(R.id.profileButton)
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java) // Nombre correcto de la clase
            startActivity(intent)
        }


        // Configuración de BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Toast.makeText(this, "Estás en Inicio", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navigation_new_trip -> {
                    startActivity(Intent(this, CreateTripActivity::class.java))
                    true
                }
                R.id.navigation_logout -> {
                    signOut()
                    true
                }
                else -> false
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
