package io.github.susimsek.springbatchsamples.gof.strategy

import org.springframework.stereotype.Component

@Component
class StrategyA : Strategy {

    override fun doStuff() {
        // implement algorithm A here
    }

    override val strategyName: StrategyName
        get() = StrategyName.StrategyA
}
