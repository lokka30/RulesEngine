package io.github.arcaneplugins.rulesengine.component

import io.github.arcaneplugins.rulesengine.RulesEngine
import io.github.arcaneplugins.rulesengine.component.action.Action
import io.github.arcaneplugins.rulesengine.component.condition.Condition
import io.github.arcaneplugins.rulesengine.component.context.Context

@Suppress("MemberVisibilityCanBePrivate")
open class Rule(
    val id: String,
    @Suppress("unused") val description: String?,
    val triggers: Set<String> = mutableSetOf(),
    val conditions: List<Condition> = mutableListOf(),
    val actions: List<Action> = mutableListOf(),
    private val platform: RulesEngine,
) {

    fun run(context: Context) {
        try {
            if(!conditions.all { it.evaluate(context) }) return
        } catch (ex: Exception) {
            platform.logS(
                """
                    Unable to run rule with id='${id}': Unable to evaluate this rule's conditions.
                    
                    ${RulesEngine.PLZ_CHECK_CONFIG_MSG}
                    
                    ${RulesEngine.exToDebuggingDetailsStr(ex)}
                """.trimIndent()
            )
            return
        }

        try {
            actions.forEach { it.run(context) }
        } catch (ex: Exception) {
            platform.logS(
                """
                    Unable to run rule with id='${id}': Unable to run this rule's actions.
                    
                    ${RulesEngine.PLZ_CHECK_CONFIG_MSG}
                    
                    ${RulesEngine.exToDebuggingDetailsStr(ex)}
                """.trimIndent()
            )
            return
        }
    }

}
