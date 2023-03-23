package com.example.firebasenotes

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore


class description_note : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var arrayNote: ArrayList<notedata>
    private lateinit var recview: RecyclerView

//    var txttitel : TextView = findViewById(R.id.txttitel)
//    var doing : TextView = findViewById(R.id.doing)
//    var done : TextView = findViewById(R.id.done)
//    var numberdesc : TextView = findViewById(R.id.numberdesc)
    val imagev : ImageView = findViewById(R.id.imageView)
    private val screenStartTime: Long = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_note)
        recview = findViewById(R.id.rView)

        progressBar.visibility = View.VISIBLE
        arrayNote = arrayListOf()
        recview.layoutManager = LinearLayoutManager(this)
        recview.setHasFixedSize(true)
        val endTime = System.currentTimeMillis()

        val screenTime = endTime - screenStartTime
        val params = Bundle()
        params.putLong("screen_desc", screenTime)
        FirebaseAnalytics.getInstance(this).logEvent("screen_desc", params)
        Log.w("Hanan", "$screenTime")
        val intent = intent
//        val txtt = intent.getStringExtra("titel")
//        val doingg = intent.getStringExtra("doing")
//        val donee = intent.getStringExtra("done")
//        val numberdescc = intent.getStringExtra("numberdesc")
        var db = FirebaseFirestore.getInstance()

        db.collection("Not").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){

                        val note : notedata? = data.toObject(notedata::class.java)
                        if (note != null){
                            arrayNote.add(note)
                        }
                    }
                    Log.w("Hanan", "No Error")

                    recview.adapter = notesadapter(this,arrayNote)

                }
                progressBar.visibility = View.GONE

            }
            .addOnFailureListener { exception ->
                Log.w("Hanan", "Error getting documents.", exception)
            }
    }

}
//        txttitel.text = txtt.toString()
//        doing.text = doingg.toString()
//        done.text = donee.toString()
//        numberdesc.text = numberdescc.toString()

//        txttitel.setText(txt)
        //        val extras = intent.extras
//        if (extras != null) {
//            val value = extras.getString("titel")
            //The key argument here must match that used in the other activity

//        val g = intent.extras
//        txttitel.text =  g?.get("titel").toString()
//        doing.text = g?.get("doing").toString()
//        done.text = g?.get("done").toString()
//        numberdesc.text = g?.get("numberdesc").toString()



