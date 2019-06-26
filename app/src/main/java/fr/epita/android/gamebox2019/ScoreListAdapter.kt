package fr.epita.android.gamebox2019

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ScoreListAdapter(private val context: Context,
                       private val data: MutableList<Score>) : BaseAdapter()
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentItem : Score = getItem(position)
        val layoutInflater = LayoutInflater.from(context)

        val rowView = layoutInflater.inflate(R.layout.score_list_element, parent, false)

        rowView.findViewById<TextView>(R.id.playerName).text = "Name: " + currentItem.player + ","
        rowView.findViewById<TextView>(R.id.gameDate).text = "date: " + currentItem.date.split("T")[0] + ","

        val gameState = rowView.findViewById<TextView>(R.id.gameState)
        if (currentItem.score == "win")
            gameState.text = "game won"
        else if (currentItem.score =="loose")
            gameState.text = "game lost"
        else
            gameState.text = "game drawn"

        rowView.id = position
        return rowView
    }

    override fun getItem(position: Int): Score {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }
}