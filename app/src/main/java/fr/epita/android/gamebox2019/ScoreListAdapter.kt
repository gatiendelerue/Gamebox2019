package fr.epita.android.gamebox2019

import android.annotation.SuppressLint
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

        val rowView = layoutInflater.inflate(R.layout.list_item, parent, false)
        rowView.findViewById<TextView>(R.id.textView).text = "Game: " + currentItem.game + ", player:" + currentItem.player + ", score: " + currentItem.score

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