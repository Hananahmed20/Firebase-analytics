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

class notesadapter (var activity: Activity, private val NoteList: ArrayList<notedata>) : RecyclerView.Adapter<notesadapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var txttitel: TextView = itemView.findViewById(R.id.txttitel)
        var doing: TextView = itemView.findViewById(R.id.doing)
        var done: TextView = itemView.findViewById(R.id.done)
        var number: TextView = itemView.findViewById(R.id.numberdesc)

        val imagev: ImageView = itemView.findViewById(R.id.imageView)
         override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ItemsViewModel = NoteList[position]
        holder.txttitel.text = ItemsViewModel.titel
        holder.doing.text = ItemsViewModel.titel
        holder.done.text = ItemsViewModel.done
        holder.number.text = ItemsViewModel.numberdesc

        var urimg: String?
        urimg = ItemsViewModel.image
        Picasso.get().load(urimg).into(holder.imagev)

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, description_note::class.java)
            activity.startActivity(intent)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_description_note, parent, false)
        return MyViewHolder(view)    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

}