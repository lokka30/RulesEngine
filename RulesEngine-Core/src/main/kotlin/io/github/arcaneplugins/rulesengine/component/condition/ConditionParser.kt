package io.github.arcaneplugins.rulesengine.component.condition

abstract class ConditionParser(
    val conditionId: String
) {
    
    abstract fun parse(settings: Map<String, Any>): Condition

}