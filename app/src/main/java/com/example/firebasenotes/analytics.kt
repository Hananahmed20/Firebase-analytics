package com.example.firebasenotes

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class analytics {

    private var analytics: FirebaseAnalytics = Firebase.analytics

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
}