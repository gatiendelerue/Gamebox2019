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
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.concurrent.timerTask


interface slidingPuzzleInteractionListener {
    fun displayScores(gameId: Int, gameState: String)
}

class SlidingPuzzle : Fragment() {
    var name : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        name = this.arguments?.getString("name").toString()
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


        object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                sendScore("loose")
                Log.d("Puzzle", "Player lost")
            }

            override fun onTick(p0: Long) {
                view.findViewById<TextView>(R.id.textRemainingTimeCount).text = (p0/1000).toString()
            }
        }.start()
    }

    fun sendScore(score: String){
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: WebServiceInterface= retrofit.create(WebServiceInterface::class.java)

        val gameId = this.arguments!!.getInt("id")

        val wsCallBack : Callback<Boolean> = object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.w("TAG", "Webservice call failed")
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                Log.d("Received", response.message())
                if (response.isSuccessful)
                {
                    Log.d("Post", "Successful")
                    (activity as MainActivity).displayScores(gameId, score)
                }
            }
        }

        Log.d("Send", "Send score " + score + " for game id " + this.arguments!!.getInt("id") + " for player " + name)
        service.postScore(this.arguments!!.getInt("id"), score, this.name).enqueue(wsCallBack)
    }
}