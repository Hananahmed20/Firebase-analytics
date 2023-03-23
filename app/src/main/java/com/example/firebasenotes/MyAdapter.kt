package com.example.firebasenotes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class MyAdapter(var activity: Activity, private val NoteList: ArrayList<notemodel>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class  ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) ,View.OnClickListener{
        var txttitel : TextView = itemView.findViewById(R.id.txttitel)
        val imagev : ImageView = itemView.findViewById(R.id.imageView)
        val cardView: CardView = itemView.findViewById(R.id.card)
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }



    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_style, parent,false)
        return  ViewHolder(itemView)
    }



    override fun onBindViewHolder(holder:  ViewHolder, position: Int) {


        val  ItemsViewModel  = NoteList[position]
        holder.txttitel.text = ItemsViewModel.titel
        var urimg :String?
        urimg = ItemsViewModel.image
         Picasso.get().load(urimg).into(holder.imagev)

         holder.itemView.setOnClickListener {
            val intent = Intent(activity, notes::class.java)
            activity.startActivity(intent)



        }
}
    override fun getItemCount(): Int {
        return NoteList.size
    }


}