package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weather.ui.theme.WeatherTheme
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    var weather_url1 = ""
    var api_id1 = "6385efa0a50b4b63b83c2d6a311afaed"
    private lateinit var textView: TextView
    private lateinit var btVar1: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        btVar1 = findViewById(R.id.btVar1)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Log.e("lat", weather_url1)


        btVar1.setOnClickListener {

            Log.e("lat", "onClick")
            obtainLocation()

        }

    }

    @SuppressLint("MissingPermission")

    private fun obtainLocation() {

        Log.e("lat", "function")
        fusedLocationClient.lastLocation

            .addOnSuccessListener { location: Location? ->
                weather_url1 =
                    "https://api.weatherbit.io/v2.0/current?" + "lat=" + location?.latitude + "&lon=" + location?.longitude + "&key=" + api_id1

                Log.e("lat", weather_url1.toString())

                getTemp()

            }

    }


    @SuppressLint("SetTextI18n")
    fun getTemp() {

        val queue = Volley.newRequestQueue(this)
        val url: String = weather_url1
        Log.e("lat", url)
        val stringReq = StringRequest(Request.Method.GET, url,
            { response ->
                Log.e("lat", response.toString())
                val obj = JSONObject(response)
                val arr = obj.getJSONArray("data")
                Log.e("lat obj1", arr.toString())
                val obj2 = arr.getJSONObject(0)
                Log.e("lat obj2", obj2.toString())
                textView.text =
                    obj2.getString("temp") + " deg Celsius in " + obj2.getString("city_name")
            },
            { textView.text = "That didn't work!" })

        queue.add(stringReq)

    }
}