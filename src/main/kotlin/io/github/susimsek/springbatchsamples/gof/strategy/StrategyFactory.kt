package io.github.susimsek.springbatchsamples.gof.strategy

import org.springframework.stereotype.Component

@Component
class StrategyFactory(
    strategySet: Set<Strategy>
) {

    private lateinit var strategies: Map<StrategyName, Strategy>

    init {
        createStrategy(strategySet)
    }

    fun findStrategy(strategyName: StrategyName): Strategy {
        return strategies[strategyName] ?: throw IllegalArgumentException("No such strategy.")
    }

    private fun createStrategy(strategySet: Set<Strategy>) {
        strategies = strategySet.associateBy { it.strategyName }
    }
}
