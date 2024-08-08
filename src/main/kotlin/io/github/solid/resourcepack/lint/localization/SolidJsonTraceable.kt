package io.github.solid.resourcepack.lint.localization

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject


class SolidJsonTraceable(
    private val path: String
) : SolidTraceable<JsonElement> {
    override fun getPath(): String {
        return path
    }

    override fun walk(ref: JsonElement): JsonElement {
        val parts = path.split("[.\\[\\]]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var result: JsonElement? = ref

        for (part in parts) {
            val key = part.trim { it <= ' ' }
            if (key.isEmpty()) continue

            if (result == null) {
                result = JsonNull.INSTANCE
                break
            }

            if (result.isJsonObject) {
                result = (result as JsonObject)[key]
            } else if (result.isJsonArray) {
                val ix = key.toInt() - 1
                result = (result as JsonArray)[ix]
            } else break
        }

        return result!!
    }
}