package com.example.smarthulumente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smarthulumente.databinding.ActivityUpdateDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateDataBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateBtn.setOnClickListener {

            val accNo = binding.accNo.text.toString()
            val fullName = binding.fullNames.text.toString()
            val phoneNo = binding.phoneContact.text.toString()
            val strAdress = binding.strAddresses.text.toString()

            updateData(accNo,fullName,phoneNo,strAdress)

        }

    }

    private fun updateData(accNo: String, fullNames: String, phoneNo: String, strAddress: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        val user = mapOf<String,String>(
            "fullName" to fullNames,
            "phoneNo" to phoneNo,
            "strAddress" to strAddress
        )

        database.child(accNo).updateChildren(user).addOnSuccessListener {

            binding.accNo.text.clear()
            binding.fullNames.text.clear()
            binding.phoneContact.text.clear()
            binding.strAddresses.text.clear()
            Toast.makeText(this,"Successfuly Updated",Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{

            Toast.makeText(this,"Failed to Update",Toast.LENGTH_SHORT).show()

        }}
}