package io.github.susimsek.springbatchsamples.gof.strategy

interface Strategy {

    fun doStuff()

    val strategyName: StrategyName
}
