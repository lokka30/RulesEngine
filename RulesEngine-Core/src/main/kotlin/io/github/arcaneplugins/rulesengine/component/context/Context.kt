package io.github.arcaneplugins.rulesengine.component.context

import io.github.arcaneplugins.rulesengine.component.Rule
import java.util.*

open class Context {

    private val contextItems = mutableMapOf<String, Any?>(
        "rule-stack" to Stack<Rule>()
    )

    fun get(key: String, strict: Boolean = true): Any? {
        if(strict && !has(key)) {
            throw RuntimeException(
                """
                    Attempted to access '${key}' in context, but it is undefined. (For example, attempting to access a Player in context in a non-player-related context (e.g. 'on entity spawn') will cause this error.)
                """.trimIndent()
            )
        }

        return contextItems[key]
    }

    fun set(key: String, value: Any?) {
        contextItems[key] = value
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun has(key: String): Boolean {
        return contextItems.contains(key)
    }

    @Suppress("unused")
    fun of(input: Map<String, Any?>) {
        input.forEach { (k, v) -> set(k, v) }
    }

    /* Predefined getters and setters */

    fun getRule(strict: Boolean = true): Rule? {
        return get("rule", strict) as Rule?
    }

    fun setRule(rule: Rule?) {
        return set("rule", rule)
    }

    fun getRuleStack(): Stack<Rule> {
        @Suppress("UNCHECKED_CAST")
        return get("rule-stack", true)!! as Stack<Rule>
    }

}