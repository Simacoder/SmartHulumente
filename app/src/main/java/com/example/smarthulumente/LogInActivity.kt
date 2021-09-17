package com.example.smarthulumente

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.smarthulumente.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog:ProgressDialog

    private var email = ""
    private var password =""


    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Cnfigure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        //Configure Progress
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //Init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handling click opn register
        binding.noAccount.setOnClickListener{
            startActivity(Intent(this, SingupActivity::class.java))
        }

        //handling login
        binding.loginBtn.setOnClickListener{

            validateData()

        }

    }

    private fun validateData() {
        //get data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalidate form
            binding.emailEt.error = "Invalide Email format"
        }
        else if(TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordEt.error ="Please enter password"
        }
        else{
            //data is valid
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()
                //get user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()
                //open profile
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                 //login failure
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if(user)
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!= null){
            //User login
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }
    }
}