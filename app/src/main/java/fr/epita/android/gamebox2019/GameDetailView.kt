package fr.epita.android.gamebox2019

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_game_detail_view.*
import kotlinx.android.synthetic.main.fragment_game_detail_view.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface gameDetailInteractionListener {
    fun onMyButtonWasClicked(name: String, gameName: String)
}
class GameDetailsView : Fragment() {
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(){}
    }

    var game_id : Int = 0;
    var playable : Boolean = false;

    fun setPlayableVar(playable : Boolean)
    {
        this.playable = playable
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.game_id = this.arguments!!.getInt("id")
        val rootView = inflater.inflate(R.layout.fragment_game_detail_view, container, false)
        Log.d("GAMEID", game_id.toString())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.previous).setOnClickListener {
            (activity as MainActivity).onMyReturnButtonClicked()
        }

        if (playable)
        {
            view.findViewById<LinearLayout>(R.id.playTheGame).visibility = View.VISIBLE
        }

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        Log.d("GAMEID", game_id.toString())
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val call = game_id?.let { service.getGameDetail(it) }

        call?.enqueue(object : Callback<GameDetail> {
            override fun onFailure(call: Call<GameDetail>, t: Throwable) {
                Log.w("MainActivity", "WS Error")
            }

            override fun onResponse(call: Call<GameDetail>, response: Response<GameDetail>) {
                if (response.code() == 200) {

                    val data = response.body()
                    game_name.text = data?.name
                    game_type.text = "Game type: " + data?.type
                    game_player.text = "Number of players: " + data?.players.toString()
                    activity?.let { Glide.with(it).load(data?.picture).into(game_img) }
                    game_desc.text = data?.description_en
                    view.findViewById<Button>(R.id.playButton).setOnClickListener {
                        (activity as MainActivity).onMyButtonWasClicked(view.findViewById<TextView>(R.id.playerName).text.toString(), game_name.text.toString())
                    }
                }
            }
        })
    }


    }