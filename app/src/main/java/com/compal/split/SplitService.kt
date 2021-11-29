package com.compal.split

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
private const val TAG = "Split service"

class SplitService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onInterrupt() {
        //TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"hugo, start GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN")
        performGlobalAction(GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN)

        return super.onStartCommand(intent, flags, startId)
    }
}