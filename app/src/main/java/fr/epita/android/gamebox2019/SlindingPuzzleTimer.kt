package fr.epita.android.gamebox2019

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class SlidingPuzzleTimer(private val listViews : Array<ImageView>, private val invisibles: Array<TextView>) : TimerTask() {

    override fun run() {
        for (view in listViews) {
            view.setOnTouchListener(EmptyOnTouchListener())
        }

        /*  TODO BROKEN
        for (invisible in invisibles) {
            invisible.visibility = View.VISIBLE
        }
        */

        // TODO Submit score here
    }
}