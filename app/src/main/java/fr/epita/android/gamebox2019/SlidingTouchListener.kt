package fr.epita.android.gamebox2019

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fr.epita.android.gamebox2019.slidingCompanion.blankView

@SuppressLint("StaticFieldLeak")
object slidingCompanion {
    var blankView : ImageView? = null
}

class SlidingTouchListener(private val parentView: View, private var numberViewMap : MutableMap<Int, ImageView>,
                            private val viewsExpected : Array<ImageView>) :
                            View.OnTouchListener {

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val X = event!!.rawX
        val Y = event.rawY

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("Puzzle", "Down on ${v!!.id}")
            }
            MotionEvent.ACTION_UP -> {
                Log.d("PuzzleMap", numberViewMap.toString())
                Log.d("Puzzle", "Up (blank view is ${blankView!!.id})")

                val adjacent = getAdjacent(blankView!!)
                for (i in adjacent.indices)
                {
                    adjacent[i] = parentView.findViewById<ImageView>(adjacent[i] as Int)
                }

                if (v == blankView)
                {
                    for (view in adjacent) {
                        if (isInView(X, Y, view as ImageView))
                        {
                            swapImages(v as ImageView, blankView!!)
                            blankView = view
                        }
                    }
                }
                else {
                    for (view in adjacent) {
                        if (v == view && isInView(X, Y, blankView!!)) {
                            swapImages(v as ImageView, blankView!!)
                            blankView = v
                        }
                    }
                }
                if (playerHasWon())
                {
                    val a = SlidingPuzzle()
                    a.sendScore("win")
                    Log.d("Puzzle", "Player won")
                }
            }

        }
        return true
    }

    private fun playerHasWon() : Boolean {

        for (i in 0..8) {
            if (numberViewMap[i + 1] != viewsExpected[i]) {
                return false
            }
        }
        return true
    }

    private fun isInView(X: Float, Y: Float, view: ImageView): Boolean {
        val pos = IntArray(2)
        view.getLocationInWindow(pos)

        val width = view.width
        val height = view.height

        if (X < pos[0] || X > pos[0] + width || Y < pos[1] || Y > pos[1] + height)
            return false
        return true
    }

    private fun getAdjacent(v: View): Array<Any> {
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

        // swapping images
        val temp = v2.drawable
        v2.setImageDrawable(v1.drawable)
        v1.setImageDrawable(temp)

        // updating the map

        val mapKey1 = numberViewMap.filterKeys { key -> numberViewMap[key] == v1 }
        val mapKey2 = numberViewMap.filterKeys { key -> numberViewMap[key] == v2 }
        val key1 = mapKey1.entries.first().key
        val key2 = mapKey2.entries.first().key

        Log.d("PuzzleSwap", "Swap key $key1 and $key2")
        val tempVal = numberViewMap[key1]
        numberViewMap[key1] = numberViewMap[key2]!!
        numberViewMap[key2] = tempVal!!

    }
}


class EmptyOnTouchListener() : View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
    }
}