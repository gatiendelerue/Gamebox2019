package fr.epita.android.gamebox2019

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

class SlidingTouchListener(upperView : View) : View.OnTouchListener {
    val parentView = upperView

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val X = event!!.rawX
        val Y = event.rawY
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("Puzzle", "Down on ${v!!.id}")
            }
            MotionEvent.ACTION_UP -> {
                Log.d("Puzzle", "Up")
                val adjacents = getAdjacent(v!!)

                for (adjacent in adjacents) {
                    val view = parentView.findViewById<ImageView>(adjacent)
                    if (isInView(X, Y, view)) {
                        swapImages(v as ImageView, view)
                    }
                }
            }

        }
        return true
    }

    private fun isInView(X: Float, Y: Float, view: View): Boolean {
        val pos = IntArray(2)
        view.getLocationInWindow(pos)

        val width = view.width
        val height = view.height

        if (X < pos[0] || X > pos[0] + width || Y < pos[1] || Y > pos[1] + height)
            return false
        return true
    }

    private fun getAdjacent(v: View): Array<Int> {
        when (v.id) {
            R.id.imagePuzzleTopLeft -> {
                return arrayOf(R.id.imagePuzzleMidLeft, R.id.imagePuzzleTopMid)
            }
            R.id.imagePuzzleTopMid -> {
                return arrayOf(R.id.imagePuzzleTopLeft, R.id.imagePuzzleTopRight, R.id.imagePuzzleMidMid)
            }
            R.id.imagePuzzleTopRight -> {
                return arrayOf(R.id.imagePuzzleTopMid, R.id.imagePuzzleMidRight)
            }
            R.id.imagePuzzleMidLeft -> {
                return arrayOf(R.id.imagePuzzleTopLeft, R.id.imagePuzzleMidMid, R.id.imagePuzzleBotLeft)
            }
            R.id.imagePuzzleMidMid -> {
                return arrayOf(
                    R.id.imagePuzzleTopMid, R.id.imagePuzzleMidRight, R.id.imagePuzzleMidLeft,
                    R.id.imagePuzzleBotMid
                )
            }
            R.id.imagePuzzleMidRight -> {
                return arrayOf(R.id.imagePuzzleBotRight, R.id.imagePuzzleTopRight, R.id.imagePuzzleMidMid)
            }
            R.id.imagePuzzleBotLeft -> {
                return arrayOf(R.id.imagePuzzleMidLeft, R.id.imagePuzzleBotMid)
            }
            R.id.imagePuzzleBotMid -> {
                return arrayOf(R.id.imagePuzzleMidMid, R.id.imagePuzzleBotLeft, R.id.imagePuzzleBotRight)
            }
            R.id.imagePuzzleBotRight -> {
                return arrayOf(R.id.imagePuzzleMidRight, R.id.imagePuzzleBotMid)
            }
        }
        return arrayOf()
    }

    private fun swapImages(v1: ImageView, v2: ImageView) {
        val temp = v2.drawable
        v2.setImageDrawable(v1.drawable)
        v1.setImageDrawable(temp)
    }
}