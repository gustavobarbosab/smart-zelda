package domain

data class DungeonFound(
    val nearestDungeon: NearestDungeon,
    val greatPath: Path,
    val position: Pair<Int,Int>
)