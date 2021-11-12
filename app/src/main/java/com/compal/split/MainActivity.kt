package com.compal.split

import android.app.ActivityManager
import android.app.ActivityOptions
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK
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

private const val TAG = "MyActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v(TAG, "split_hugo");
        aQQ()
        testSplit()
        /*if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)){
            Toast.makeText(
                MainActivity.this,
                "Device not support PIP mode !",
                Toast.LENGTH_SHORT).show();
        }else {
            enterPictureInPictureMode();
        }*/
    }
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
        val intent = Intent(this, AdjacentActivity::class.java).apply {
            setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK)
//            setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        }
        startActivity(intent, options.toBundle())

    }
}

fun aQQ(){
    Log.v(TAG, "funcall , split_hugo");
   // WindowManager.
    //Activity
}
fun getWindowingModeMethodName(): String {
    /*if(getCurrentApiVersopn() >= 28.0f )
        return "setLaunchWindowingMode"
    else*/
        return "setLaunchStackId"
}
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

