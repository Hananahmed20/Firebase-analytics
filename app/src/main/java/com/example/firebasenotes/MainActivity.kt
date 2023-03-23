package com.example.firebasenotes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var recview: RecyclerView
    private lateinit var arrayNote :ArrayList<notemodel>
    val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    private val screenStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_act)

         FirebaseAnalytics.getInstance(this)
            .setCurrentScreen(this, "Screen Name", "Screen Class Name")


//         val startTime = System.currentTimeMillis()
//        firebaseAnalytics.setCurrentScreen(this, "Screen Name", null);

        val endTime = System.currentTimeMillis()

        val screenTime = endTime - screenStartTime
        val params = Bundle()
        params.putLong("screen_Home", screenTime)
        FirebaseAnalytics.getInstance(this).logEvent("screen_view", params)
        Log.w("Hanan", "$screenTime")


        progressBar = findViewById(R.id.progressBar)
        recview = findViewById(R.id.recyclerView)
        recview.layoutManager = LinearLayoutManager(this)
        recview.setHasFixedSize(true)
        arrayNote = arrayListOf()
        progressBar.visibility = View.VISIBLE

        var db = FirebaseFirestore.getInstance()
//        val parentCollectionRef = db.collection("Notes")
//        val newDocRef = parentCollectionRef.document()
//        val subCollectionRef = newDocRef.collection("subCollection")
//        val data = hashMapOf(
//            "to do" to "study for quiz",
//            "doing" to "do assigment",
//            "done" to "print chapter"
//        )
//
//        subCollectionRef.document("newDocId").set(data)

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

//    override fun onStart() {
//        super.onStart()
//        analytics.setCurrentScreen(this, "Screen Name", null)
//    }
//
//    // Track screen view duration when the activity stops
//    override fun onStop() {
//        super.onStop()
//
//        // Get the current screen name
//        val currentScreen = analytics.setCurrentScreen(this,"home_act","home_act")
//
//        // Calculate the screen view duration
//        val endTime = System.currentTimeMillis()
//        val startTime = analytics.setSessionTimeoutDuration(Long.MIN_VALUE)
//        val screenViewDuration = endT - startTime
//
//        // Log the screen view duration event with Firebase Analytics
//        val params = Bundle().apply {
//            putLong("screen_home", screenViewDuration)
//        }
//        analytics.logEvent("screen_home", params)
//    }

}


