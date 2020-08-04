package com.jftech.nooglecalendar.views.gesture_recognizers
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

open class PanGestureListener(context: Context): View.OnTouchListener
{
    private val panDetector: GestureDetectorCompat

    init
    {
        panDetector = GestureDetectorCompat(context, PanGestureDetector())
    }

    override fun onTouch(view: View, motionEvent: MotionEvent?): Boolean
    {
        return panDetector.onTouchEvent(motionEvent)
    }

    open fun OnPanRight(): Boolean {return false}

    open fun OnPanLeft():Boolean {return false}

    inner class PanGestureDetector : GestureDetector.OnGestureListener
    {
        override fun onDown(e: MotionEvent): Boolean
        {
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean
        {
            //Spy check
            if (e1 == null || e2 == null) { return false; }
            var result = false
            if (distanceX > 0)
                result = OnPanRight()
            else if (distanceX < 0)
                result = OnPanLeft()
            return result
        }



        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean
        {
            //Spy check
            if (e1 == null || e2 == null) { return false; }
            var result = false
            val distanceX = e2.x - e1.x
            if (distanceX > 0)
                result = OnPanRight()
            else if (distanceX < 0)
                result = OnPanLeft()
            return result
        }

        override fun onShowPress(p0: MotionEvent?) {}

        override fun onSingleTapUp(p0: MotionEvent?): Boolean { return false}

        override fun onLongPress(p0: MotionEvent?) {}
    }
}