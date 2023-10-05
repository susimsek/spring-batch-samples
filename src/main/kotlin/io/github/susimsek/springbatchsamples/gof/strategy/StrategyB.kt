package io.github.susimsek.springbatchsamples.gof.strategy

import org.springframework.stereotype.Component

@Component
class StrategyB : Strategy {

    override fun doStuff() {
        // implement algorithm B here
    }

    override val strategyName: StrategyName
        get() = StrategyName.StrategyB
}
