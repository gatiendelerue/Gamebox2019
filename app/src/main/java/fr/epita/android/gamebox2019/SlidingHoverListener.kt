package fr.epita.android.gamebox2019

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

class SlidingHoverListener : View.OnHoverListener {
    override fun onHover(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_HOVER_ENTER -> {
                Log.d("Puzzle", "Hover enter ${v!!.id}")
                SlidingInformations.view_hover = v as ImageView?
            }
            MotionEvent.ACTION_HOVER_EXIT -> {
                Log.d("Puzzle", "Hover exit ${v!!.id}")
                SlidingInformations.view_hover = null
            }
        }
        return true
    }
}