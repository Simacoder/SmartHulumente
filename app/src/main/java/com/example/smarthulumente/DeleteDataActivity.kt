package com.example.smarthulumente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smarthulumente.databinding.ActivityDeleteDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteDataBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deletedataBtn.setOnClickListener{
            var accNo = binding.etaccNo.text.toString()
            if(accNo.isNotEmpty())
                deleteData(accNo)
            else
                Toast.makeText(this,"Please enter the Account no: ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteData(accNo: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(accNo).removeValue().addOnSuccessListener {
            binding.etaccNo.text.clear()

            Toast.makeText(this,"Successfully deleted ", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
        }

    }
}