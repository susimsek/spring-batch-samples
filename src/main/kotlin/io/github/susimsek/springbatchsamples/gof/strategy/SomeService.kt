package io.github.susimsek.springbatchsamples.gof.strategy

import org.springframework.stereotype.Service

@Service
class SomeService(
    private val strategyFactory: StrategyFactory
) {

    fun findSome() {
        val strategy = strategyFactory.findStrategy(StrategyName.StrategyA)
        strategy.doStuff()
    }
}
