package movements

sealed class NextMoveDungeon(val nextMove: NextMoveDungeon) {
    object FindPendant : NextMoveDungeon(GoToTheEntry)
    object GoToTheEntry : NextMoveDungeon(DungeonFinished)
    object DungeonFinished : NextMoveDungeon(DungeonFinished)
}
