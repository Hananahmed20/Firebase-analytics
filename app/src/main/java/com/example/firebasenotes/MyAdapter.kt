package com.example.firebasenotes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class MyAdapter(var activity: Activity, private val NoteList: ArrayList<notemodel>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private lateinit var analytics: FirebaseAnalytics

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var txttitel : TextView = itemView.findViewById(R.id.txttitel)
        val imagev : ImageView = itemView.findViewById(R.id.imageView)
//        val cardView: CardView = itemView.findViewById(R.id.card)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_style, parent,false)
        return  ViewHolder(itemView)
    }



    override fun onBindViewHolder(holder:  ViewHolder, position: Int) {
        user_event()
        val  ItemsViewModel  = NoteList[position]
        analytics = Firebase.analytics
        holder.txttitel.text = ItemsViewModel.titel
        var urimg :String?
        urimg = ItemsViewModel.image
         Picasso.get().load(urimg).into(holder.imagev)

         holder.itemView.setOnClickListener {
            val i = Intent(activity, notes::class.java)
             i.putExtra("id",ItemsViewModel.id)
             Log.d("H","error"+ItemsViewModel.id)
            activity.startActivity(i)
        }
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
