package fr.epita.android.gamebox2019


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


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
            imageBotLeft, imageBotMid, imageBotRight, imageMidLeft, imageMidMid,
            imageMidRight, imageTopLeft, imageTopMid, imageTopRight
        )
        var drawable: Int

        for (image in images) {
            drawable = drawables.random()
            Log.d("IMAGE", this.context?.resources?.getResourceEntryName(drawable))
            image.setImageResource(drawable)
            image.setOnTouchListener(SlidingTouchListener())
            image.setOnHoverListener(SlidingHoverListener())
            drawables.remove(drawable)
        }
    }
}