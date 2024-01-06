package io.github.arcaneplugins.rulesengine

import io.github.arcaneplugins.rulesengine.component.Rule
import io.github.arcaneplugins.rulesengine.component.action.ActionParser
import io.github.arcaneplugins.rulesengine.component.condition.ConditionParser
import io.github.arcaneplugins.rulesengine.component.context.Context
import io.github.arcaneplugins.rulesengine.component.context.PlaceholderContainer

@Suppress("MemberVisibilityCanBePrivate")
abstract class RulesEngine {

    val rules: MutableList<Rule> = mutableListOf()

    /**
     * Access the `PlaceholderContainer` for this `RulesEngine` instance.
     *
     * This val must be abstract, so that the implementation of `RulesEngine` can specify a reference to itself in the
     * constructor of [PlaceholderContainer].
     */
    abstract val placeholderCtr: PlaceholderContainer

    private val actionParsers: MutableList<ActionParser> = mutableListOf()
    private val conditionParsers: MutableList<ConditionParser> = mutableListOf()

    @Suppress("MemberVisibilityCanBePrivate")
    fun load() {
        loadPlaceholders()

        loadInternalActionParsers()
        loadExternalActionParsers()

        loadInternalConditionParsers()
        loadExternalConditionParsers()

        loadRules()
    }

    @Suppress("unused")
    fun reload() {
        rules.clear()
        actionParsers.clear()
        conditionParsers.clear()
        placeholderCtr.placeholders.clear()

        load()
    }

    @Suppress("unused")
    fun runRulesWithTrigger(trigger: String, context: Context) {
        rules
            .filter { it.triggers.contains(trigger) }
            .forEach { it.run(context) }
    }

    private fun loadPlaceholders() {
        loadInternalPlaceholders()
        loadExternalPlaceholders()
        loadCustomPlaceholders()
    }

    protected abstract fun loadInternalPlaceholders()

    protected abstract fun loadExternalPlaceholders()

    protected abstract fun loadCustomPlaceholders()

    protected abstract fun loadInternalActionParsers()

    protected abstract fun loadExternalActionParsers()

    protected abstract fun loadInternalConditionParsers()

    protected abstract fun loadExternalConditionParsers()

    protected abstract fun loadRules()

    /**
     * Resolve placeholders in a given string `input`, using any native method(s) such as
     * PlaceholderAPI for Bukkit, or a system provided by the server software, or any other suitable APIs.
     */
    abstract fun resolveNativePlaceholders(input: String): String

    /**
     * Log an Info message
     */
    @Suppress("unused")
    abstract fun logI(msg: String)

    /**
     * Log a Warning message
     */
    @Suppress("unused")
    abstract fun logW(msg: String)

    /**
     * Log a Severe message
     */
    abstract fun logS(msg: String)

    companion object {
        const val PLZ_CHECK_CONFIG_MSG = """
            ===== NOTICE for Server Administrators =====
            This error may look quite daunting - try these steps so you can resolve it:
            
            - See if there's anything you may understand in the Exception Message below.
            - Check your config file(s) for any syntax errors. You can use the Online YAML Parser tool to help with this.
            - Check the Wiki to see if this is a common issue or question that has been answered already.
            - If all of the above fails, contact the correct support channel for this plugin, as described on the resource page. 
        """

        fun exToDebuggingDetailsStr(ex: Exception): String {
            return """
                ====== Debugging Details for Developers =====
                - Exception message: ${ex.message}
                - Exception stack trace: [see below]
                ${ex.stackTraceToString()}
            """.trimIndent()
        }
    }

}