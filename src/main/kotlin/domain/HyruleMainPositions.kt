package domain

class HyruleMainPositions {

    lateinit var linkHouse: Pair<Int, Int>
    lateinit var lostWoods: Pair<Int, Int>
    lateinit var firstDungeon: Pair<Int, Int>
    lateinit var secondDungeon: Pair<Int, Int>
    lateinit var thirdDungeon: Pair<Int, Int>

    val nonVisitedDungeons: MutableList<Pair<Int, Int>> by lazy {
        mutableListOf(firstDungeon, secondDungeon, thirdDungeon)
    }

    fun dungeonVisited(dungeonPosition: Pair<Int, Int>) {
        nonVisitedDungeons.remove(dungeonPosition)
    }

    lateinit var currentPosition: Pair<Int, Int>

    fun printResume() {
        println("\nA casa do link é em: $linkHouse")
        println("A primeira dungeon está em: $firstDungeon")
        println("A segunda dungeon está em: $secondDungeon")
        println("A terceira dungeon está em: $thirdDungeon")
        println("Lost Wood está  em: $lostWoods")
        println("-------------------------------------------------------")
    }

    fun startCurrentPosition() {
        currentPosition = linkHouse
    }
}