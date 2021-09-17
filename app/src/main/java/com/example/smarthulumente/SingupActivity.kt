package com.example.smarthulumente

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.smarthulumente.databinding.ActivitySingupBinding
import com.google.firebase.auth.FirebaseAuth

class SingupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingupBinding

    //ActionBAr
    private lateinit var actionBar: ActionBar

    //Prgressiondialog
    private lateinit var progressDialog: ProgressDialog

    //Firebase Auth
    private lateinit var firebaseauth: FirebaseAuth
    private var email =""
    private var password =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Action Bar //enabled back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progresionDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase Auth
        firebaseauth = FirebaseAuth.getInstance()

        //handling signing up
        binding.signUpBtn.setOnClickListener{
            validateData()
        }

    }

    private fun validateData() {
        //get data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email fomart"
        }
        else if(TextUtils.isEmpty(password)){
            // password ins't entered
            binding.passwordEt.error = "Please enter password"
        }
        else if(password.length<6){
            //Password length is less then six
            binding.passwordEt.error ="Password must atleast be six characters long"
        }
        else {
            //data is valid
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        // show progress
        progressDialog.show()
        //create an account
        firebaseauth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //sign up success
                progressDialog.dismiss()
                // get current user
                val firebaseUser = firebaseauth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener{ e->
                //sign up failed
                progressDialog.dismiss()
                Toast.makeText(this, "Sign up  failed due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go bac to previous event

        return super.onSupportNavigateUp()
    }

}