package fr.epita.android.gamebox2019

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide

class GameListAdapter(private val context: Context,
                      private val data: MutableList<Game>) : BaseAdapter()
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentItem : Game = getItem(position)
        val layoutInflater = LayoutInflater.from(context)

        val rowView = layoutInflater.inflate(R.layout.list_item, parent, false)
        rowView.findViewById<TextView>(R.id.gameName).text = currentItem.name
        Glide.with(rowView).load(currentItem.picture).into(rowView.findViewById(R.id.gameImage))
        return rowView
    }

    override fun getItem(position: Int): Game {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }


}