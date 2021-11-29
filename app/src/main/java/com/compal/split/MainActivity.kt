package com.compal.split

import android.app.ActivityManager
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsAnimation
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.internal.ContextUtils.getActivity
import java.lang.reflect.Method
import android.app.Activity
import android.content.Intent.*
import android.provider.Settings
import android.view.View
import android.widget.Button


private const val TAG = "MyActivity"
class MainActivity : AppCompatActivity() {
    var primaryIntent: Intent? = null
    var secondaryIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  Log.v(TAG, "split_hugo");




        if (isInMultiWindowMode) {
            Log.v(TAG, "in split mode hugo");
            //testSplit()
            startSplit()

        } else {
            Log.v(TAG, "start split mode hugo");
            setContentView(R.layout.activity_shortcut)
            val accessibilityOptionsButton = findViewById<View>(
                R.id.accessibilityOptionsButton
            ) as Button
            accessibilityOptionsButton.setOnClickListener {
                val accessibilityIntent = Intent()
                accessibilityIntent.action = Settings.ACTION_ACCESSIBILITY_SETTINGS
                startActivity(accessibilityIntent)
            }
        }

    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        super.onMultiWindowModeChanged(isInMultiWindowMode)
        if (isInMultiWindowMode) {
            Log.d(
                TAG, "hugo onMultiWindowModeChanged() called with: isInMultiWindowMode = ["
                        + isInMultiWindowMode
                        + "]"
            )
            startSplit()
        }
    }

    override fun onResume() {
        if (!isInMultiWindowMode)
            // This should trigger the activity being re created and this time
            // onCreate should do thunderbirdsAreGo
            // should should should.
            startService(Intent(this, SplitService::class.java))
        //
        super.onResume()
    }

    private fun startSplit() {
        //TODO("Not yet implemented")

        /* Left/Top side apk */
        //val  pkg2 = "com.android.launcher3"
        //val  pkg2 = "com.android.calendar" // not support split mode
        val  pkg2 = "com.android.settings"

        /* Right/bottom side apk */
        //val  pkg1 = "com.ionitech.airscreen" // not support split mode
        //val  pkg1 = "uk.co.keepawayfromfire.screens"
        val  pkg1 = "com.liskovsoft.videomanager"

        primaryIntent = packageManager.getLaunchIntentForPackage(pkg1)
        secondaryIntent = packageManager.getLaunchIntentForPackage(pkg2)
        if (primaryIntent == null ) {
            //goHome()
            Log.d(TAG,"primaryIntent is NULL hugo");
            return
        }
        if ( secondaryIntent == null) {
            //goHome()
                Log.d(TAG,"secondaryIntent is NULL hugo");
            return
        }

        primaryIntent!!.addFlags(FLAG_ACTIVITY_LAUNCH_ADJACENT)
        primaryIntent!!.addFlags(FLAG_ACTIVITY_NEW_TASK)
        //primaryIntent!!.addFlags(FLAG_ACTIVITY_MULTIPLE_TASK)
        primaryIntent!!.addFlags(FLAG_ACTIVITY_TASK_ON_HOME)
        primaryIntent!!.addFlags(FLAG_ACTIVITY_NO_ANIMATION) //started intent order

        // show intent detials
        Log.d("hugo primaryIntent URI", primaryIntent!!.toUri(0));

        secondaryIntent!!.addFlags(FLAG_ACTIVITY_LAUNCH_ADJACENT)
        secondaryIntent!!.addFlags(FLAG_ACTIVITY_NEW_TASK)
        //secondaryIntent!!.addFlags(FLAG_ACTIVITY_MULTIPLE_TASK)
        secondaryIntent!!.addFlags(FLAG_ACTIVITY_NO_ANIMATION) //started intent order

        // show intent detials
        Log.d("hugo secondaryIntent URI", secondaryIntent!!.toUri(0));

        startActivities(arrayOf(secondaryIntent!!, primaryIntent!!))
        //startActivities(new Intent[]{secondaryIntent, primaryIntent});
        finishAndRemoveTask()
    }

    /*
    @SuppressLint("InlinedApi")
public static Intent multiWindowIntent(Activity activity, Intent intent) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && activity.isInMultiWindowMode()) {
    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT |
        Intent.FLAG_ACTIVITY_NEW_TASK |
        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
  }else{

  Log.d(TAG," apk version is lower than Build.VERSION_CODES.N" )
  }
  return intent;
}
     */

    /*
    public fun enterPipMode(){
        try{
            getActivity().enterPictureInPictureMode();
        }
    }*/
    private fun testSplit(){

       // var cl =javaClass

        val boundss = Rect(400,600,1080,1100)
        val options = ActivityOptions.makeBasic()
        try {
            //val method = cl.getMethod(getWindowingModeMethodName(), Int::class.java)
            val method = options.javaClass.getMethod(getWindowingModeMethodName(), Int::class.java)
            method.setAccessible(true)
            method.invoke(options, 3 )
            /* argment readme
            https://lixiaogang03.github.io/2020/08/14/Android-StackId/
            FREEFORM_WORKSPACE_STACK_ID = 2
            // 占用屏幕专用区域的堆栈ID
            /** ID of stack that occupies a dedicated region of the screen. */
            public static final int DOCKED_STACK_ID = FREEFORM_WORKSPACE_STACK_ID + 1;

            // 画中画栈
            /** ID of stack that always on top (always visible) when it exist. */
            public static final int PINNED_STACK_ID = DOCKED_STACK_ID + 1;

            // recents app所在的栈
            /** ID of stack that contains the Recents activity. */
            public static final int RECENTS_STACK_ID = PINNED_STACK_ID + 1;
            */
        } catch (e: NumberFormatException) {
            //throw new IllegalStateException(e)
        }
       options.setLaunchBounds(boundss)
       // val bundle: Bundle = options.toBundle()

     /*   val intent =  Intent(this, AdjacentActivity.class)
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or  Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent, options.toBundle())*/

        //add launcher3 to start split screem.


        // start AdjacentActivity.java in split mode.
        val intent = Intent(this, AdjacentActivity::class.java).apply {
            setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK)
//            setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        }

        startActivity(intent, options.toBundle())

    }
}

fun getWindowingModeMethodName(): String {
    /*if(getCurrentApiVersopn() >= 28.0f )
        return "setLaunchWindowingMode"
    else*/
        return "setLaunchStackId"
}
/*
fun android-screen-rotation-onconfigurationchanged(){
        http://androchen.logdown.com/posts/2015/12/30/android-screen-rotation-onconfigurationchanged
}
*/
/*
public boolean toggleSplitScreenMode(int metricsDockAction, int metricsUndockAction) {
    if (!mSplitScreenOptional.isPresent()) {
        return false;
    }

    final LegacySplitScreen legacySplitScreen = mSplitScreenOptional.get();
    if (legacySplitScreen.isDividerVisible()) {
        if (legacySplitScreen.isMinimized() && !legacySplitScreen.isHomeStackResizable()) {
            // Undocking from the minimized state is not supported
            return false;
        }

        legacySplitScreen.onUndockingTask();
        if (metricsUndockAction != -1) {
            mMetricsLogger.action(metricsUndockAction);
        }
        return true;
    }

    if (legacySplitScreen.splitPrimaryTask()) {
        if (metricsDockAction != -1) {
            mMetricsLogger.action(metricsDockAction);
        }
        return true;
    }

    return false;
}*/

