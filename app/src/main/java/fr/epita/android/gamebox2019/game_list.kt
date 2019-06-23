package fr.epita.android.gamebox2019


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

interface gameListInteractionListener {
    fun onMyItemClicked(game: Game, playable: Boolean)
    fun onMyReturnButtonClicked()
}
class GameList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var gameList = view.findViewById<ListView>(R.id.gameList)
        val data = arrayListOf<Game>()



        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: WebServiceInterface= retrofit.create(WebServiceInterface::class.java)

        val wsCallBack : Callback<List<Game>> = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Log.w("TAG", "Webservice call failed")
            }

            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.code() == 200) {
                    var responseData = response.body()
                    if (responseData != null) {
                        Log.d("TAG", responseData.toString())
                        for (d in responseData) {
                            data.add(d)
                        }

                        var adapter = GameListAdapter(this@GameList.requireContext(), data)
                        gameList.setAdapter(adapter)

                        gameList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                            val selected_item = parent.getItemAtPosition(position)

                            (activity as MainActivity).onMyItemClicked(selected_item as Game,
                                selected_item.name == "Hangman"  || selected_item.name == "SlidingPuzzle" )
                        }

                    }
                }
            }

        }
        service.listGame().enqueue(wsCallBack);
    }
}
