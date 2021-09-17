package com.example.smarthulumente

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "SmartHulumente"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS ) !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS),111)
        }
        else
            recievedMsg()
        val button: Button = findViewById<Button>(R.id.sendSms)
        val editTextPhone: EditText = findViewById<EditText>(R.id.editTextPhone)
        val editTextTextMultiLine2: EditText = findViewById<EditText>(R.id.editTextTextMultiLine2)
         button.setOnClickListener{
             var sms = SmsManager.getDefault()
             sms.sendTextMessage(editTextPhone.text.toString(), "ME", editTextTextMultiLine2.text.toString(), null, null)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if( requestCode== 111 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
            recievedMsg()
    }

    private fun recievedMsg() {

        var br = object: BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for(sms :SmsMessage in  Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                        val editTextPhone: EditText = findViewById<EditText>(R.id.editTextPhone)
                        val editTextTextMultiLine2: EditText = findViewById<EditText>(R.id.editTextTextMultiLine2)

                        editTextPhone.setText(sms.originatingAddress)
                        editTextTextMultiLine2.setText(sms.displayMessageBody)

                    }
                }

            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))

    }

}










