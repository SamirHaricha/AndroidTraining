package com.samirharicha.myapplication

import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.samirharicha.myapplication.databinding.ActivityHomeMainBinding

class HomeMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeMainBinding
    lateinit var ref: DatabaseReference
    lateinit var providerList: MutableList<Provider>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("Singe","In HomeMainPage")

        providerList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("MeeDz").child("Provider")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Singe","In Fun onDataChange")
              if (snapshot!!.exists()){
                  Log.d("Singe","snapshot exist")
                  providerList.clear()
                  for (e in snapshot!!.children){

                      Log.d("Singe","In for children")
                      val provide = e.getValue(Provider::class.java)
                      providerList.add(provide!!)
                  }
                  Log.d("Singe","In adapter transform")
                  val myAdapter = ProviderAdapter(providerList,this@HomeMainActivity)
                  binding.chatRecyclerView.adapter = myAdapter
                  binding.chatRecyclerView.layoutManager = LinearLayoutManager(this@HomeMainActivity)
                  binding.chatRecyclerView.setHasFixedSize(true)
              }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


    }
}