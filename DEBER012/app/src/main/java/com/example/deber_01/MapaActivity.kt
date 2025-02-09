package com.example.deber_01

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private var latitud: Double = 0.0
    private var longitud: Double = 0.0
    private var facultadNombre: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mapa)

        // Configurar edge-to-edge si tu layout tiene un contenedor con id "main"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibir los datos enviados desde MainActivity
        latitud = intent.getDoubleExtra("latitud", 0.0)
        longitud = intent.getDoubleExtra("longitud", 0.0)
        facultadNombre = intent.getStringExtra("nombre") ?: "Ubicación"

        // Obtiene el fragmento del mapa y solicita la notificación cuando esté listo
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Crea una posición LatLng con la latitud y longitud recibidas
        val ubicacion = LatLng(latitud, longitud)

        // Mueve la cámara a la ubicación con un zoom (por ejemplo, 15f)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))

        // Agrega un marcador en la ubicación con el nombre de la facultad
        googleMap.addMarker(
            MarkerOptions()
                .position(ubicacion)
                .title(facultadNombre)
        )
    }
}
