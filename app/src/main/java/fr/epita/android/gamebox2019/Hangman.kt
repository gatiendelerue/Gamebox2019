package fr.epita.android.gamebox2019


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Hangman : Fragment() {

    var word : String? = null
    var wordToFind : String? = null
    var usedLetter =  mutableListOf<String>()
    var tried = 0

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val wordlist = context?.assets?.open("words.txt")?.bufferedReader()?.readLines()
            word = wordlist?.random()?.toLowerCase()
            Log.d("Word", word)
            wordToFind= ""

            while (wordToFind!!.length != word!!.length)
            {
                wordToFind += "_"

            }

            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_hangman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateLetter(view)
        view.findViewById<Button>(R.id.submitButton).setOnClickListener {
            Log.d("Button", "Submit button pressed")
            var tmp = view.findViewById<TextView>(R.id.letter)
            var held_str : String = tmp.text.toString().toLowerCase()
            Log.d("Input", held_str)
            tmp.onEditorAction(EditorInfo.IME_ACTION_DONE)

            if (held_str.length != 1 || usedLetter.contains(held_str)) {
                tmp.text = ""
               return@setOnClickListener
            }
            if (!(word?.contains(held_str))!!)
            {
                tried += 1
            }
            usedLetter.add(held_str)
            updateHangedMan(view)
            updateUsedLetter(view)
            updateLetter(view)
            tmp.text = ""
        }
    }

    fun updateLetter(view : View){
        val text = view.findViewById<TextView>(R.id.wordText)
        var tmp = ""
        for (i in this.word?.toList()!!) {
            if (usedLetter.contains(i.toString())) {
                tmp += i
            } else {
                tmp += "_"
            }
            tmp += " "
        }
        text.text = tmp
    }
    fun updateUsedLetter(view : View) {
        val text = view.findViewById<TextView>(R.id.usedLetterText)
        var tmp = "Used letter: "
        for(i in usedLetter)
            tmp += i + " "
        text.text = tmp
    }
    fun updateHangedMan(view: View) {
        val image = view.findViewById<ImageView>(R.id.hangedGuy)
        var ressource = R.drawable.hg0
        if (tried == 1)
            ressource = R.drawable.hg1
        else if (tried == 2)
            ressource = R.drawable.hg2
        else if (tried == 3)
            ressource = R.drawable.hg3
        else if (tried == 4)
            ressource = R.drawable.hg4
        else if (tried == 5)
            ressource = R.drawable.hg5
        else if (tried == 6)
            ressource = R.drawable.hg6
        else if (tried == 7)
            ressource = R.drawable.hg7
        else if (tried == 8)
            ressource = R.drawable.hg8
        else if (tried == 9)
            ressource = R.drawable.hg9
        else if (tried == 10)
            ressource = R.drawable.hg10
        else if (tried == 11)
            ressource = R.drawable.hg11

        image.setImageResource(ressource)
    }
}
