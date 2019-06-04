package fr.epita.android.gamebox2019

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("game/list")
    fun listGame() : Call<List<Game>>

    @GET("game/details")
    fun getGameDetail(@Query("game_id") game_id: Int) : Call<GameDetail>

    @GET("game/score")
    fun getScore() : Call<List<ScoreGet>>

    @POST("/game/score")
    fun postScore(@Field("game_id") game_id : Int,
                  @Field("score") score : String,
                  @Field("player") player : String) : Call<Boolean>

}