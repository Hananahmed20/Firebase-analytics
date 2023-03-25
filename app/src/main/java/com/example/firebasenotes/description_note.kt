package com.example.firebasenotes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics


class description_note : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    var txttitel : TextView = findViewById(R.id.txttitel)
    var doing : TextView = findViewById(R.id.doing)
    var done : TextView = findViewById(R.id.done)
    var numberdesc : TextView = findViewById(R.id.numberdesc)
    val imagev : ImageView = findViewById(R.id.imageView)
    private val screenStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_note)
        progressBar.visibility = View.VISIBLE

        val endTime = System.currentTimeMillis()

        val screenTime = endTime - screenStartTime
        val params = Bundle()
        params.putLong("screen_Home", screenTime)
        FirebaseAnalytics.getInstance(this).logEvent("screen_desc", params)
        Log.w("Hanan", "$screenTime")
        val intent = intent
        val txtt = intent.getStringExtra("titel")
        val doingg = intent.getStringExtra("doing")
        val donee = intent.getStringExtra("done")
        val numberdescc = intent.getStringExtra("numberdesc")


        txttitel.text = txtt.toString()
        doing.text = doingg.toString()
        done.text = donee.toString()
        numberdesc.text = numberdescc.toString()

//        txttitel.setText(txt)
        //        val extras = intent.extras
//        if (extras != null) {
//            val value = extras.getString("titel")
            //The key argument here must match that used in the other activity
        }
//        val g = intent.extras
//        txttitel.text =  g?.get("titel").toString()
//        doing.text = g?.get("doing").toString()
//        done.text = g?.get("done").toString()
//        numberdesc.text = g?.get("numberdesc").toString()



}