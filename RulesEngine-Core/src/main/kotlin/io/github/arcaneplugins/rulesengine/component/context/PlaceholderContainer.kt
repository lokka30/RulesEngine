package io.github.arcaneplugins.rulesengine.component.context

import io.github.arcaneplugins.rulesengine.RulesEngine
import java.util.function.Function

class PlaceholderContainer(
    val rulesEngine: RulesEngine
) {

    val placeholders = mutableSetOf<Placeholder>()

    fun replace(input: String, context: Context): String {
        var output: String = input

        for(placeholder in placeholders) {
            val target = "%${placeholder.id}%"

            if(!output.contains(target)) continue

            output = output.replace(target, placeholder.resolve.apply(context))
        }

        return output
    }

    class Placeholder(
        val id: String,

        /**
         * Resolve a placeholder
         */
        val resolve: Function<Context, String>
    )

}