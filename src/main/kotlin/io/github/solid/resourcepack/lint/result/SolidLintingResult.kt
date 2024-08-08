package io.github.solid.resourcepack.lint.result

import io.github.solid.resourcepack.lint.SolidLinter
import io.github.solid.resourcepack.lint.localization.SolidLintingLocalization

interface SolidLintingResult {
    fun type(): SolidLintingResultType {
        return SolidLintingResultType.WARNING
    }

    fun message(): String? {
        return null
    }

    fun localization(): SolidLintingLocalization<*>? {
        return null
    }

    fun printTrace(localization: SolidLintingLocalization<*>?) {
        localization?.let {
            println("Trace: ${it.getTrace().getPath()} (${it.getLocalizationLevel()})")
            it.getFollowingLocalizations().forEach(::printTrace)
        }
    }

    fun printAll() {
        println(type())
        printTrace(localization())
        println(message())
    }

    companion object {

        fun error(message: String, localization: SolidLintingLocalization<*>): SolidLintingResult {
            return object : SolidLintingResult {
                override fun type(): SolidLintingResultType {
                    return SolidLintingResultType.ERROR
                }

                override fun message(): String {
                    return message
                }

                override fun localization(): SolidLintingLocalization<*> {
                    return localization
                }
            }
        }

        fun warning(message: String, localization: SolidLintingLocalization<*>): SolidLintingResult {
            return object : SolidLintingResult {
                override fun type(): SolidLintingResultType {
                    return SolidLintingResultType.WARNING
                }

                override fun message(): String {
                    return message
                }

                override fun localization(): SolidLintingLocalization<*> {
                    return localization
                }
            }
        }
        fun errorOnStrict(linter: SolidLinter, message: String, localization: SolidLintingLocalization<*>): SolidLintingResult {
            return object : SolidLintingResult {
                override fun type(): SolidLintingResultType {
                    return if(linter.strict) SolidLintingResultType.ERROR else SolidLintingResultType.WARNING
                }

                override fun message(): String {
                    return "$message (if you want to skip this error, set strict to false)"
                }

                override fun localization(): SolidLintingLocalization<*> {
                    return localization
                }
            }
        }

    }
}

enum class SolidLintingResultType {
    ERROR,
    WARNING
}