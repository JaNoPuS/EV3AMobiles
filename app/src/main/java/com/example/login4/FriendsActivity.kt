package com.example.login4

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        // Configuración de la barra de navegación inferior
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Navega a la actividad principal (inicio)
                    startActivity(Intent(this, ActivityWelcome::class.java))
                    true
                }
                R.id.navigation_new_trip -> {
                    // Navega a la actividad para crear un nuevo viaje
                    startActivity(Intent(this, CreateTripActivity::class.java))
                    true
                }
                R.id.navigation_logout -> {
                    // Lógica para cerrar sesión
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
