package fr.epita.android.gamebox2019

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

@SuppressLint("StaticFieldLeak")
object SlidingInformations {
    var actionDown = false
    var actionHovering = false
    var view_down : ImageView? = null   // XXX possible static field leak (theoretically prevented by reset)
    var view_hover : ImageView? = null  // XXX possible static field leak

    private fun areDifferent() : Boolean {
        if (view_down == null || view_hover == null)
            return false
        return view_down != view_hover
    }

    private fun areAdjacent() : Boolean {
        if (!areDifferent())
            return false
        when (view_down!!.id) {
            R.id.imagePuzzleTopLeft -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleMidLeft, R.id.imagePuzzleTopMid) -> return true
                }
            }
            R.id.imagePuzzleTopMid -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleTopLeft, R.id.imagePuzzleTopRight, R.id.imagePuzzleMidMid) -> return true
                }
            }
            R.id.imagePuzzleTopRight -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleTopMid, R.id.imagePuzzleMidRight) -> return true
                }
            }
            R.id.imagePuzzleMidLeft -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleTopLeft, R.id.imagePuzzleMidMid, R.id.imagePuzzleBotLeft) -> return true
                }
            }
            R.id.imagePuzzleMidMid -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleTopMid, R.id.imagePuzzleMidRight, R.id.imagePuzzleMidLeft,
                                    R.id.imagePuzzleBotMid) -> return true
                }
            }
            R.id.imagePuzzleMidRight -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleBotRight, R.id.imagePuzzleTopRight, R.id.imagePuzzleMidMid) -> return true
                }
            }
            R.id.imagePuzzleBotLeft -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleMidLeft, R.id.imagePuzzleBotMid) -> return true
                }
            }
            R.id.imagePuzzleBotMid -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleMidMid, R.id.imagePuzzleBotLeft, R.id.imagePuzzleBotRight) -> return true
                }
            }
            R.id.imagePuzzleBotRight -> {
                when (view_hover!!.id) {
                    in arrayOf(R.id.imagePuzzleMidRight, R.id.imagePuzzleBotMid) -> return true
                }
            }
        }
        return false
    }

    fun swapImages() {
        if (areDifferent() && areAdjacent()) {
            val temp = view_down?.drawable
            view_down?.setImageDrawable(view_hover?.drawable)
            view_hover?.setImageDrawable(temp)
        }
    }

    fun reset() {
        actionDown = false
        actionHovering = false
        view_down = null
        view_hover = null
    }
}

class SlidingTouchListener : View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("Puzzle", "Down on ${v!!.id}")
                SlidingInformations.view_down = v as ImageView?
            }
            MotionEvent.ACTION_UP -> {
                Log.d("Puzzle", "Up")
                SlidingInformations.swapImages()    // swap images if they are different and adjacent
                SlidingInformations.view_down = null
            }

        }
        return true
    }
}