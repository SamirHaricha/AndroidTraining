package com.samirharicha.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.samirharicha.myapplication.databinding.ActivitySingeInBinding

class SingeInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingeInBinding
    var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingeInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logeIn.setOnClickListener {
            val mail = binding.email.text.toString().trim()
            val pass = binding.password.text.toString().trim()
            if (mail.isNotEmpty()&&pass.isNotEmpty()){
                Log.d("Singe","In field full")
                auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener {task ->
                    Log.d("Singe","task creat")
                    if (task.isSuccessful){
                        Log.d("Singe","task creat succses")
                        val intent = Intent(this,HomeMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"field empty",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}