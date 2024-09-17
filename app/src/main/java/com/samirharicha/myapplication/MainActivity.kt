package com.samirharicha.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.samirharicha.myapplication.databinding.ActivityMainBinding

class MainActivity() : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var auth = FirebaseAuth.getInstance()
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val name: String = binding.fulname.text.toString().trim()
//        val description:  String = binding.jobName.text.toString().trim()
//        val wilaya:String= binding.wilaya.text.toString().trim()
//        val number:String= binding.number.text.toString().trim()
//        val mail: String = binding.email.text.toString().trim()
//        val pass: String = binding.password.text.toString().trim()

        database = FirebaseDatabase.getInstance().getReference("MeeDz")

        binding.singeIn.setOnClickListener {
            Log.d("SignIn", "SignIn button clicked")
            signIn() }
        binding.database.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }
        binding.btnLoggeIn.setOnClickListener {
            val intent = Intent(this, SingeInActivity::class.java)
            startActivity(intent)
        }

    }
    private fun signIn() {
        Log.d("SignIn", "Started signIn function")


           // database = FirebaseDatabase.getInstance().getReference("Provider")
            val name: String = binding.fulname.text.toString().trim()
            val description:  String = binding.jobName.text.toString().trim()
            val wilaya:String= binding.wilaya.text.toString().trim()
            val number:String= binding.number.text.toString().trim()
            val mail: String = binding.email.text.toString().trim()
            val pass: String = binding.password.text.toString().trim()

            if (name.isNotEmpty() &&
                description.isNotEmpty() &&
                wilaya.isNotEmpty() &&
                number.isNotEmpty() &&
                mail.isNotEmpty() &&
                pass.isNotEmpty()){
                Log.d("SignIn", "field are full")
                auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener{task->
                    if (task.isSuccessful){
                        Log.d("SignIn", "SignIn successful")
                        val userId = auth.currentUser?.uid
                        val user_id = auth.currentUser?.uid.toString()
                        val provider = Provider(user_id,name,description ,wilaya,number,mail,pass )

                        userId?.let {

                            database.child("Provider").child(it).setValue(provider)
                                .addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                        // Redirect to login or main screen
                                        val intent =Intent(this,HomeMainActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(this, "Database error: ${dbTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    }else{
                        Log.e("SignIn", "${task.exception?.message}")
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }  else{
               Toast.makeText(this,"field empty",Toast.LENGTH_LONG).show()
            }




    }
}

