package com.example.firebasenotes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class description_note : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var analytics: FirebaseAnalytics
    var db = Firebase.firestore

    var txttitel : TextView = findViewById(R.id.txttitel)
    var desc : TextView = findViewById(R.id.desc)
    var numberdesc : TextView = findViewById(R.id.numberdesc)
    val imagev : ImageView = findViewById(R.id.imageView)
    private val screenStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_note)
        screenTrack("description_note","description_note")

        progressBar.visibility = View.VISIBLE
        analytics = Firebase.analytics

        var titel=intent!!.getStringExtra("titel")
        db.collection("Notes").whereEqualTo("titel",titel).get().addOnSuccessListener {
            txttitel.setText(it.documents.get(0).get("titel").toString())
            desc.setText(it.documents.get(0).get("desc").toString())
            numberdesc.setText(it.documents.get(0).get("numnumberdesc").toString())
            Picasso.get().load(it.documents.get(0).get("image").toString()).into(imagev)
            progressBar.visibility = View.GONE
        }
                .addOnFailureListener {
                    Log.w("Hanan", "Error getting documents.", it)
                }


        }
    fun selectContent(contentId : String, contentName: String, contentType: String){
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT){
            param(FirebaseAnalytics.Param.ITEM_ID, contentId);
            param(FirebaseAnalytics.Param.ITEM_NAME, contentName);
            param(FirebaseAnalytics.Param.CONTENT_TYPE, contentType);
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