package com.n

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import kotlinx.coroutines.*
import com.n.LOCATION_PERMISSION_REQUEST_CODE


class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var locManager: LocManager
    private lateinit var telegramManager: TelegramManager

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        locManager = LocManager(this)
        telegramManager = TelegramManager(this)

        // Resto de tu código...
    }

    override fun onLocationChanged(location: Location) {
        // Implementación del método onLocationChanged
        // Aquí puedes realizar las acciones que deseas realizar cuando la ubicación cambia
    }

    // Resto de tus métodos y funciones...
}
