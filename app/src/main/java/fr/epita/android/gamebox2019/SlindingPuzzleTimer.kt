package fr.epita.android.gamebox2019

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class SlidingPuzzleTimer(private val listViews : Array<ImageView>, private val invisibles: Array<TextView>) : TimerTask() {

    override fun run() {
        Log.d("Puzzle", "Time limit: player loose")
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