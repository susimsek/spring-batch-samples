package io.github.susimsek.springbatchsamples.gof.strategy

import org.springframework.stereotype.Component

@Component
class StrategyC : Strategy {

    override fun doStuff() {
        // implement algorithm c here
    }
    override val strategyName: StrategyName
        get() = StrategyName.StrategyC
}
