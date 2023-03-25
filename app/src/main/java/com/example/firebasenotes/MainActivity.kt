package com.example.firebasenotes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var recview: RecyclerView
    private lateinit var arrayNote :ArrayList<notemodel>
     private val screenStartTime: Long = 0
    private var analytics: FirebaseAnalytics = Firebase.analytics
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_act)
        analytics = Firebase.analytics
        progressBar = findViewById(R.id.progressBar)
        recview = findViewById(R.id.recyclerView)
        recview.layoutManager = LinearLayoutManager(this)
        recview.setHasFixedSize(true)
        arrayNote = arrayListOf()
        progressBar.visibility = View.VISIBLE

        screenTrack("Main_Activity","MainActivity")

        db.collection("Notes").get().addOnSuccessListener {
            if (!it.isEmpty){
                for (data in it.documents){
                    val note : notemodel? = data.toObject(notemodel::class.java)
                    if (note != null){
                        arrayNote.add(note)
                    }
                }
                Log.w("Hanan", "No Error")

                recview.adapter = MyAdapter(this,arrayNote)

            }
            progressBar.visibility = View.GONE

        }
            .addOnFailureListener { exception ->
                Log.w("Hanan", "Error getting documents.", exception)
            }
    }

     // Track screen view duration when the activity stops
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
        params.putLong("screen_Home", screenTime)
        FirebaseAnalytics.getInstance(this).logEvent("screen_Home", params)
        Log.w("Hanan", "$screenTime")

        val user = hashMapOf(
            "name page" to "MainActivity",
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


