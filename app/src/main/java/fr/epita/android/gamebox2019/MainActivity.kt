package fr.epita.android.gamebox2019

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

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
