package io.github.arcaneplugins.rulesengine.component.action

abstract class ActionParser(
    val actionId: String
) {

    abstract fun parse(settings: Map<String, Any>): Action

}