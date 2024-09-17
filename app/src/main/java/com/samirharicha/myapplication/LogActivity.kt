package com.samirharicha.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.samirharicha.myapplication.databinding.ActivityLogBinding

class LogActivity : AppCompatActivity() {
    lateinit var binding:ActivityLogBinding
    var auth = FirebaseAuth.getInstance()
    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)


       database = FirebaseDatabase.getInstance().getReference("Clients")


        binding.btnLoge.setOnClickListener {
            val user = binding.editUsername.text.toString().trim()
            val mail = binding.editMail.text.toString().trim()
            val pass = binding.editpassword.text.toString().trim()
            Log.d("Loge","$mail $pass")
            logedIn(user,mail,pass)
        }
    }

    private fun logedIn(user:String,mail:String,pass:String){
        if (user.isNotEmpty() && mail.isNotEmpty() && pass.isNotEmpty()){
            auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener {task ->
                if(task.isSuccessful){
                    val userId = auth.currentUser?.uid
                    val client = Clients(userId.toString(),user,mail,pass )
                    Log.d("UserId",userId.toString())

                    userId?.let {

                        database.child("Client").child(it).setValue(client)
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
                    Log.e("Task","error message ${task.exception?.message}")
                    Toast.makeText(this,"try again later ${task.exception?.message}",Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Log.e("Loge","empty field")
            Toast.makeText(this,"empty field",Toast.LENGTH_LONG).show()
        }
    }
}