package domain

sealed class NearestDungeon {
    object First : NearestDungeon()
    object Second : NearestDungeon()
    object Third : NearestDungeon()
}