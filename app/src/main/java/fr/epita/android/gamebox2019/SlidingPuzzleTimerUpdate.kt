package fr.epita.android.gamebox2019

import android.support.v4.app.Fragment
import android.widget.TextView
import java.util.*

class SlidingPuzzleTimerUpdate(v: TextView) : TimerTask() {
    private val valView = v
    private var time = 60

    override fun run() {
        time -= 1
        valView.text = time.toString()
    }
}