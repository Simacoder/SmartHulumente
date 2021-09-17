package com.example.smarthulumente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smarthulumente.databinding.ActivityReadDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReadDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadDataBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.readdataBtn.setOnClickListener {

            val accNo : String = binding.etaccNo.text.toString()

            if  (accNo.isNotEmpty()){

                readData(accNo)

            }else{

                Toast.makeText(this,"PLease enter the Account No",Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun readData(accNo: String) {

        database = FirebaseDatabase.getInstance().getReference("AccNo")
        database.child(accNo).get().addOnSuccessListener {

            if (it.exists()){

                val fullName = it.child("Full Name").value
                val  phoneNo= it.child("Phone number").value
                val strAddress = it.child("Street Address").value
                Toast.makeText(this,"Successfully Read",Toast.LENGTH_SHORT).show()
                binding.etaccNo.text.clear()
                binding.tvNameNsurname.text = fullName.toString()
                binding.tvPhoneNo.text = strAddress.toString()
                binding.tvstrAddress.text = phoneNo.toString()

            }else{

                Toast.makeText(this,"Acc Doesn't Exist",Toast.LENGTH_SHORT).show()


            }

        }.addOnFailureListener{

            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


        }


    }
}