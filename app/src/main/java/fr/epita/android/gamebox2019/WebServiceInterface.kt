package fr.epita.android.gamebox2019

import retrofit2.Call
import retrofit2.http.*

interface WebServiceInterface {
    @GET("game/list")
    fun listGame() : Call<List<Game>>

    @GET("game/details")
    fun getGameDetail(@Query("game_id") game_id: Int) : Call<GameDetail>

    @GET("game/scores")
    fun getScore() : Call<List<Score>>

    @POST("game/score")
    @FormUrlEncoded
    fun postScore(@Field("game_id") game_id : Int,
                  @Field("score") score : String,
                  @Field("player") player : String) : Call<Boolean>

}