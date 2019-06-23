package fr.epita.android.gamebox2019

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

class MainActivity : AppCompatActivity(), gameDetailInteractionListener, gameListInteractionListener, hangmanInteractionListener {
    override fun onMyButtonWasClicked(name: String, gameName: String, gameId : Int) {
        var bundle = Bundle()
        var game: Fragment? = null
        bundle.putString("name", name)
        bundle.putInt("id", gameId)
        if (gameName == "Hangman") {
            game = Hangman()
        }
        else if (gameName == "SlidingPuzzle") {
            //TODO Add sliding puzzle game
        }

        if (game == null) {
            return
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        game.arguments = bundle

        fragmentTransaction.replace(R.id.main_container, game)
        fragmentTransaction.commit()
    }

    override fun onMyItemClicked(game: Game, playable: Boolean) {
        var bundle = Bundle()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val gameDetails = GameDetailsView()
        bundle.putInt("id", game.id)
        gameDetails.arguments = bundle
        gameDetails.setPlayableVar(playable)
        fragmentTransaction.replace(R.id.main_container, gameDetails)
        fragmentTransaction.commit()
    }

    override fun onMyReturnButtonClicked() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val GameList = GameList()
        fragmentTransaction.replace(R.id.main_container, GameList)

        fragmentTransaction.commit()
    }

    override fun displayScores() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val scoreList = ScoreList()
        fragmentTransaction.replace(R.id.main_container, scoreList)

        fragmentTransaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val GameList = GameList()

        fragmentTransaction.replace(R.id.main_container, GameList)
        fragmentTransaction.commit()
    }

}
