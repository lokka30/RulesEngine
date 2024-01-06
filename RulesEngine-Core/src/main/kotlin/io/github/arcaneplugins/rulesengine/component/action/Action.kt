package io.github.arcaneplugins.rulesengine.component.action

import io.github.arcaneplugins.rulesengine.component.Rule
import io.github.arcaneplugins.rulesengine.component.context.Context

abstract class Action(
    val id: String,
    val parentRule: Rule,
) {

    abstract fun run(context: Context)

}