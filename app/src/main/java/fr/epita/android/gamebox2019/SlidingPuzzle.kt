package fr.epita.android.gamebox2019


import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timerTask


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */


class SlidingPuzzle : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sliding_puzzle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageTopLeft: ImageView = view.findViewById(R.id.imagePuzzleTopLeft)
        val imageTopMid: ImageView = view.findViewById(R.id.imagePuzzleTopMid)
        val imageTopRight: ImageView = view.findViewById(R.id.imagePuzzleTopRight)
        val imageMidLeft: ImageView = view.findViewById(R.id.imagePuzzleMidLeft)
        val imageMidMid: ImageView = view.findViewById(R.id.imagePuzzleMidMid)
        val imageMidRight: ImageView = view.findViewById(R.id.imagePuzzleMidRight)
        val imageBotLeft: ImageView = view.findViewById(R.id.imagePuzzleBotLeft)
        val imageBotMid: ImageView = view.findViewById(R.id.imagePuzzleBotMid)
        val imageBotRight: ImageView = view.findViewById(R.id.imagePuzzleBotRight)

        val drawables: MutableList<Int> = mutableListOf(
            R.drawable.number_1, R.drawable.number_2, R.drawable.number_3,
            R.drawable.number_4, R.drawable.number_5, R.drawable.number_6,
            R.drawable.number_7, R.drawable.number_8, R.drawable.blank
        )

        val images: Array<ImageView> = arrayOf(
            imageTopLeft, imageTopMid, imageTopRight, imageMidLeft, imageMidMid,
            imageMidRight, imageBotLeft, imageBotMid, imageBotRight
            )
        var image: ImageView
        var blankView: ImageView? = null

        val numberViewMap: MutableMap<Int, ImageView> = mutableMapOf()
        var i = 0
        val imagesCopy = images.copyOf().toMutableList()

        for (drawable in drawables) {
            i += 1
            image = imagesCopy.random()
            Log.d("IMAGE", this.context?.resources?.getResourceEntryName(drawable))
            if (drawable == R.drawable.blank) {
                blankView = image
            }

            // The key match the number on the square
            numberViewMap[i] = image
            image.setImageResource(drawable)

            imagesCopy.remove(image)
        }

        for (imageView in images) {
            Log.d("Puzzle", "numberViewMap :$numberViewMap")
            imageView.setOnTouchListener(SlidingTouchListener(view, numberViewMap, images))
        }

        slidingCompanion.blankView = blankView


        val timer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTick(p0: Long) {

                view.findViewById<TextView>(R.id.textRemainingTimeCount).text = (p0/1000).toString()
            }
        }.start()
        val timerUpdateTask = SlidingPuzzleTimerUpdate(view.findViewById(R.id.textRemainingTimeCount))
        val timerTask = SlidingPuzzleTimer(
            images, arrayOf(
                view.findViewById(R.id.textGameOver),
                view.findViewById(R.id.textWinOrLoose)
            )
        )

    }
}