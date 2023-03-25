package com.example.firebasenotes

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

     class notesadapter(var context: Context, private val NoteList: ArrayList<notedata>) : RecyclerView.Adapter<notesadapter.MyViewHolder>() {
         private lateinit var analytics: FirebaseAnalytics

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txttitel: TextView = itemView.findViewById(R.id.txttitel)

        val imagev: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ItemsViewModel = NoteList[position]
        analytics = Firebase.analytics
        holder.txttitel.text = ItemsViewModel.titel

        var urimg: String?
        urimg = ItemsViewModel.image
        Picasso.get().load(urimg).into(holder.imagev)
         holder.itemView.setOnClickListener {
             user_event()
            var intent = Intent(context, description_note::class.java)
             intent.putExtra("id_n",ItemsViewModel.id)
             Log.d("H","error"+ItemsViewModel.id)
             intent.putExtra("titel_note", holder.txttitel.text)
             context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_style, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return NoteList.size
    }
         fun user_event(){
             analytics.logEvent("image") {
                 param("name_image","b1.png")
                 param("select1","selected")
             }
         }
}