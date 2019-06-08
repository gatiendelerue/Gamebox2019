package fr.epita.android.gamebox2019

class Game {
    // Game id to be referenced in other methods
    val id : Int = 0
    // Game short name
    val name : String = ""
    // An URL for an image representing the game
    val picture : String = ""

    @Override
    override fun toString(): String {
        return "id : $id. name : $name, picture url : $picture"
    }
}