package movements

sealed class NextMoveDungeon(val textButton: String, val nextMove: NextMoveDungeon) {
    object FindPendant : NextMoveDungeon("Voltar",GoToTheEntry)
    object GoToTheEntry : NextMoveDungeon("Fim",DungeonFinished)
    object DungeonFinished : NextMoveDungeon("Fim",DungeonFinished)
}
