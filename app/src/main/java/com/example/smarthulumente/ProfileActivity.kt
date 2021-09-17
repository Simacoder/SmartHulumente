package com.example.smarthulumente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.smarthulumente.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Configure the  Actiona Bar
        actionBar = supportActionBar!!
        actionBar.title ="Profile"

        //Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handling the click
        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        //checked users in logged in
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //User is logged in, get user info
            val email = firebaseUser.email
            //set to textView
            binding.emailTv.text = email

        }
        else{
            //User is not logged in
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }
}