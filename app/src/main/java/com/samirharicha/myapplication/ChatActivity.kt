package com.samirharicha.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.samirharicha.myapplication.databinding.ActivityChatBinding
import com.samirharicha.myapplication.databinding.ActivitySingeInBinding

class ChatActivity : AppCompatActivity() {




    private lateinit var messagesRef: DatabaseReference
    private lateinit var adapter: ChatAdapter
    private lateinit var messageList: MutableList<Message>




    lateinit var binding: ActivityChatBinding
    var auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val providerReciverId = intent.getStringExtra("ID")
        Log.d("PROVIDERID",providerReciverId.toString())
        messagesRef = FirebaseDatabase.getInstance().getReference("MeeDz").child("Messages")
        val query = messagesRef.orderByChild("reciverId").equalTo(providerReciverId)

        messageList = mutableListOf()

        adapter = ChatAdapter(messageList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (postSnapshot in snapshot.children) {
                    val message = postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                adapter = ChatAdapter(messageList)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this@ChatActivity)
//                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // التعامل مع الخطأ
            }
        })

        // إرسال رسالة عند الضغط على زر الإرسال
        binding.sendButton.setOnClickListener {
            val messageText = binding.messageEditText.text.toString()
            if (messageText.isNotEmpty()) {
                val message = Message(senderId = auth.uid.toString(),providerReciverId.toString(), message = messageText, timestamp = System.currentTimeMillis())
                messagesRef.push().setValue(message).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.messageEditText.text.clear()
                    }
                }
            }
        }
    }
}