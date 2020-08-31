package com.nelkinda.training.gameoflife

internal typealias Cell = Point<*>

@Suppress("TooManyFunctions")
data class Universe constructor(
        private val rules: Rules = ConwayRules,
        private val life: Set<Cell>,
) {
    operator fun inc() = copy(life = survivingCells() + bornCells())

    private fun survivingCells() = life.filter { it.survives() }.toSet()
    private fun bornCells() = deadNeighborsOfLivingCells().filter { it.born() }.toSet()
    private fun deadNeighborsOfLivingCells() = life.flatMap { it.deadNeighbors() }

    private fun Cell.survives() = rules.survives(countLiveNeighbors())
    private fun Cell.born() = rules.born(countLiveNeighbors())
    private fun Cell.isAlive() = this in life
    private fun Cell.isDead() = this !in life
    private fun Cell.deadNeighbors() = neighbors { it.isDead() }
    private fun Cell.liveNeighbors() = neighbors { it.isAlive() }
    private fun Cell.countLiveNeighbors() = liveNeighbors().count()

    override fun toString() = "Universe{$rules\n$life}"
}
