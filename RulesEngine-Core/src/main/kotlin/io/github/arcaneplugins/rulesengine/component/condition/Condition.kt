package io.github.arcaneplugins.rulesengine.component.condition

import io.github.arcaneplugins.rulesengine.component.Rule
import io.github.arcaneplugins.rulesengine.component.context.Context

abstract class Condition(
    val id: String,
    val parentRule: Rule,
) {

    abstract fun evaluate(context: Context): Boolean

}