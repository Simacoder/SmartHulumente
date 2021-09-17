package com.example.smarthulumente

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<Button>(R.id.getLocation).setOnClickListener{
            fetchLocation()

        }



        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "SmartHulumente"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val button2 = findViewById<Button>(R.id.takePicture)
        button2.setOnClickListener{
            val intent = Intent(this, TakePictureActivity2::class.java )
            startActivity(intent)
        }

        val buttonReport : Button = findViewById<Button>(R.id.report)
        buttonReport.setOnClickListener{
            val intent = Intent(this, ReportActivity::class.java )
            startActivity(intent)
        }

        val buttonSend: Button = findViewById<Button>(R.id.registerBtn)

        buttonSend.setOnClickListener{
            val intent = Intent( this, FillInFormActivity::class.java)
            startActivity(intent)
        }

        val buttonRead: Button = findViewById<Button>(R.id.readBtn)

        buttonRead.setOnClickListener{
            val intent = Intent( this, ReadDataActivity::class.java)
            startActivity(intent)
        }

        val buttonUpdateMe: Button = findViewById<Button>(R.id.updateDataBtn)

        buttonUpdateMe.setOnClickListener{
            val intent = Intent( this, UpdateDataActivity::class.java)
            startActivity(intent)
        }

        val buttonDeleteMe: Button = findViewById<Button>(R.id.deleteMessaBtn)

        buttonDeleteMe.setOnClickListener{
            val intent = Intent( this, DeleteDataActivity::class.java)
            startActivity(intent)
        }



    }


    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED

        ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }

        task.addOnSuccessListener {
            if(it != null){
                Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
            }
        }

    }

}