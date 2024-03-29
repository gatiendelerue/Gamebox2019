package fr.epita.android.gamebox2019

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_score_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScoreList : Fragment() {
    private var state : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        state = this.arguments!!.getString("state").toString()
        return inflater.inflate(R.layout.fragment_score_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scoreList = view.findViewById<ListView>(R.id.scoreList)
        Log.d("TAG", scoreList.toString())
        val data = arrayListOf<Score>()

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val gameId = this.arguments!!.getInt("id")

        val wsCallBack : Callback<List<Score>> = object : Callback<List<Score>> {
            override fun onFailure(call: Call<List<Score>>, t: Throwable) {
                Log.w("TAG", "Webservice call failed")
            }

            override fun onResponse(call: Call<List<Score>>, response: Response<List<Score>>) {
                if (response.code() == 200) {
                    val responseData = response.body()

                    if (responseData != null) {

                        Log.d("TAG", responseData.toString())

                        for (d in responseData) {
                            if (d.game_id == gameId)
                                data.add(d)
                        }

                        var adapter = ScoreListAdapter(this@ScoreList.requireContext(), data)
                        Log.d("TAG", adapter.toString())

                        title.text = "Scores for " + data[0].game

                        val score : String
                        if (state == "win")
                            score = "You won the game"
                        else if (state =="loose")
                            score = "You lost the game"
                        else
                            score = "Game draw"

                        Toast.makeText(context, score, Toast.LENGTH_LONG).show()

                        scoreList.setAdapter(adapter)

                    }
                }
            }

        }

        buttonMainMenu.setOnClickListener {
            (activity as MainActivity).onMyReturnButtonClicked()
        }

        service.getScore().enqueue(wsCallBack)
    }
}
