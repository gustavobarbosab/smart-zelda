package movements

sealed class NextMoveHyrule(val buttonText: String, val nextMove: NextMoveHyrule) {
    object FindFirstNearestDungeon : NextMoveHyrule("Ir para segunda dungeon", FindSecondNearestDungeon)
    object FindSecondNearestDungeon : NextMoveHyrule("Ir para terceira dungeon", FindThirdNearestDungeon)
    object FindThirdNearestDungeon : NextMoveHyrule("Voltar para casa", GoToLinkHouse)
    object GoToLinkHouse : NextMoveHyrule("Ir para lost wood", GoToLostWoods)
    object GoToLostWoods : NextMoveHyrule("Fim!", GameFinished)
    object GameFinished : NextMoveHyrule("Fim!", GameFinished)
}
