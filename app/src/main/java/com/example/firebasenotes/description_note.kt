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
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class description_note : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var recview: RecyclerView
    private lateinit var analytics: FirebaseAnalytics
    var db = FirebaseFirestore.getInstance()
    private val screenStartTime: Long = 0

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_note)

         analytics = Firebase.analytics
         screenTrack("description_note","description_note")

        var txt_titel : TextView = findViewById(R.id.txttitel)
        var txtdesc : TextView = findViewById(R.id.desc)
        var numberdesc : TextView = findViewById(R.id.numberdesc)
        val imagev : ImageView = findViewById(R.id.imageView)

         progressBar.visibility = View.VISIBLE
         recview.layoutManager = LinearLayoutManager(this)
        recview.setHasFixedSize(true)


         var titel_note = intent!!.getStringExtra("titel_note")

        db.collection("Not").whereEqualTo("titel",titel_note).get()
            .addOnSuccessListener {
               txt_titel.setText(it.documents.get(0).get("titel").toString())
                txtdesc.setText(it.documents.get(0).get("desc").toString())
                numberdesc.setText(it.documents.get(0).get("numberdesc").toString())
                Picasso.get().load(it.documents.get(0).get("image").toString()
                ).into(imagev)

                Log.w("Hanan", "No Error")
                progressBar.visibility = View.GONE

            }
            .addOnFailureListener { exception ->
                Log.w("Hanan", "Error getting documents.", exception)
            }
    }
    fun screenTrack(screenClass: String, screenName:String){
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass);
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        val endTime = System.currentTimeMillis()

        var screenTime = endTime - screenStartTime
        screenTime =screenTime/1000
        val params = Bundle()
        params.putLong("screen_desc", screenTime)
        FirebaseAnalytics.getInstance(this).logEvent("screen_desc", params)
        Log.w("Hanan", "$screenTime")

        val user = hashMapOf(
            "name page" to "description_note",
            "time" to screenTime
        )

        db.collection("screen_project")
            .add(user).addOnSuccessListener { documentReference ->
                Log.d("Hanan", "Document added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Hanan", "dont added", e)
            }
    }
}



