package com.example.firebasenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class notes : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var recview: RecyclerView
    private lateinit var arrayNote: ArrayList<notedata>
    private val screenStartTime: Long = 0
//    val id_note = notedata()
    private lateinit var analytics: FirebaseAnalytics
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)
        analytics = Firebase.analytics
        progressBar = findViewById(R.id.progressBar)
        recview = findViewById(R.id.recyclerView)
        recview.layoutManager = LinearLayoutManager(this)
        recview.setHasFixedSize(true)
        arrayNote = arrayListOf()
        progressBar.visibility = View.VISIBLE
        var intent_id = intent.getIntExtra("id", 0)

        screenTrack("Notes","notes")


//        db.collection("Notes").document("kTiYUa5eEXq0j9rP2b7b").collection("a").get()
           db.collection("Notes").get() .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        if (intent_id == data.getLong("id")?.toInt()) {
                        val note: notedata? = data.toObject(notedata::class.java)
                        if (note != null) {
                            arrayNote.add(note)
                        }
                    }}
                    Log.w("Hanan", "No Error")

                    recview.adapter = notesadapter(this ,arrayNote)

                }
                progressBar.visibility = View.GONE

            }
            .addOnFailureListener { exception ->
                Log.w("Hanan", "Error getting documents.", exception)
            }



//        val intent = intent
//        val txtt = intent.getStringExtra("titel")
//        val doingg = intent.getStringExtra("doing")
//        val donee = intent.getStringExtra("done")
//        val numberdescc = intent.getStringExtra("numberdesc")
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
        params.putLong("screen_notes", screenTime)
        FirebaseAnalytics.getInstance(this).logEvent("screen_notes", params)
        Log.w("Hanan", "$screenTime")

        val user = hashMapOf(
            "name page" to "notes",
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

//        db.collection("Notes").document("a").collection("c1")
//            .addSnapshotListener { snaphot, exception ->
//                    if (exception != null) {
//                        Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG)
//                            .show()
//                    } else {
//                        if (snaphot != null) {
//                            if (!snaphot.isEmpty) {
//                                val documents = snaphot.documents
//                                for (document in documents) {
//                                    val imgUrl = document.get("image") as String
//                                    val titel = document.get("titel") as String
//
//                                    val c = notedata(
//                                        imgUrl,
//                                        titel,
//                                    )
//                                    arrayNote.add(c)
//
//        collection("2").document(
//                "tfHfqIMGyCt6FnvUaTxf").get()
//        db.collection("Notes").document("newDocId")
//            .collection("subCollection").get()
//        var parentCollectionRef = db.collection("Notes")
//        var newDocRef = parentCollectionRef.document(
//                "kTiYUa5eEXq0j9rP2b7b")
//        var subCollectionRef = newDocRef.collection("subCollection")
//        subCollectionRef.document("newDocId").get()
//                db.collection("Notes").get()
////            .addOnSuccessListener { result ->
////                for (document in result) {
////                    if(document.id==collectionRef.id) {
////                        Log.d("Hanan", "${document.id} => ${document.data}")
////                        val note: notedata = document.toObject(notedata::class.java)
////                        arrayNote.add(note)
////                        recview.adapter = notesadapter(this, arrayNote)
////                        progressBar.visibility = View.GONE
////
////                }
////            }
////            .addOnFailureListener { exception ->
////                Log.d("Hanan", "Error getting documents: ", exception)
//                                }
//        val query = db.collection("Notes").document(
//                "kTiYUa5eEXq0j9rP2b7b").collection("subCollection").document("newDocId")
//                query.get()
//        db.collection("Notes").document("cu").collection("c1").get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("Hanan", "${document.id} => ${document.data}")
//                    recview.adapter = MyAdapter(this,arrayNote)
//
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("Hanan", "Error getting documents: ", exception)
//            }


//        val parentCollectionRef = db.collection("Notes")
//        val newDocRef = parentCollectionRef.document()
//        val subCollectionRef = newDocRef.collection("subCollection")
//        subCollectionRef.document("newDocId").get()

//        val data = hashMapOf(
//            "to do" to "study for quiz",
//            "doing" to "do assigment",
//            "done" to "print chapter"
//        )

//        db.collection("Notes").document("newDocId")

//




