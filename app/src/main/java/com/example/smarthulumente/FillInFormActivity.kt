package com.example.smarthulumente

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthulumente.databinding.ActivityFillInFormBinding
import com.example.smarthulumente.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FillInFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFillInFormBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityFillInFormBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.sendAddBtn.setOnClickListener {

                val accNo = binding.accNo.text.toString()
                val namesNsurname = binding.namesNsurname.text.toString()
                val streetAddres = binding.addressNo.text.toString()
                val phoneNo = binding.phoneNumber.text.toString()

                database = FirebaseDatabase.getInstance().getReference("Users")
                val user = UserRate(accNo,namesNsurname,streetAddres,phoneNo )
                database.child(accNo).setValue(user).addOnSuccessListener {

                    binding.accNo.text.clear()
                    binding.namesNsurname.text.clear()
                    binding.addressNo.text.clear()
                    binding.phoneNumber.text.clear()

                    Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

                }.addOnFailureListener{

                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


                }


            }

    }
}