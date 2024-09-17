package com.samirharicha.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class ProviderAdapter(private val providerList: List<Provider>,private val context: Context) :
    RecyclerView.Adapter<ProviderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.fullName)
        val adressTextView: TextView = itemView.findViewById(R.id.adress)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val btnMsg :Button =itemView.findViewById(R.id.msgbtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_template, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = providerList[position]
        holder.nameTextView.text = provider.fname
        holder.adressTextView.text = provider.wilaya
        holder.descriptionTextView.text = provider.description
        holder.btnMsg.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("ID",provider.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = providerList.size
}
