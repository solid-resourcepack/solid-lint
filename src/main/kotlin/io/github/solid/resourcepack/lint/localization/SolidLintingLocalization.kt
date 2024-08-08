package io.github.solid.resourcepack.lint.localization

interface SolidLintingLocalization<Trace : SolidTraceable<*>> {
    fun getLocalizationLevel(): SolidLintingLocalizationLevel
    fun getTrace(): Trace
    fun getFollowingLocalizations(): List<SolidLintingLocalization<*>>

    companion object {
        fun directory(trace: SolidDirectoryTraceable, vararg following: SolidLintingLocalization<*>): SolidLintingLocalization<SolidDirectoryTraceable> {
            return object : SolidLintingLocalization<SolidDirectoryTraceable> {
                override fun getLocalizationLevel(): SolidLintingLocalizationLevel {
                    return SolidLintingLocalizationLevel.DIRECTORY
                }

                override fun getTrace(): SolidDirectoryTraceable {
                    return trace
                }

                override fun getFollowingLocalizations(): List<SolidLintingLocalization<*>> {
                    return listOf(*following)
                }
            }
        }

        fun file(trace: SolidFileTraceable, vararg following: SolidLintingLocalization<*>): SolidLintingLocalization<SolidFileTraceable> {
            return object : SolidLintingLocalization<SolidFileTraceable> {
                override fun getLocalizationLevel(): SolidLintingLocalizationLevel {
                    return SolidLintingLocalizationLevel.FILE
                }

                override fun getTrace(): SolidFileTraceable {
                    return trace
                }

                override fun getFollowingLocalizations(): List<SolidLintingLocalization<*>> {
                    return listOf(*following)
                }
            }
        }

        fun json(trace: SolidJsonTraceable, vararg following: SolidLintingLocalization<*>): SolidLintingLocalization<SolidJsonTraceable> {
            return object : SolidLintingLocalization<SolidJsonTraceable> {
                override fun getLocalizationLevel(): SolidLintingLocalizationLevel {
                    return SolidLintingLocalizationLevel.JSON
                }

                override fun getTrace(): SolidJsonTraceable {
                    return trace
                }

                override fun getFollowingLocalizations(): List<SolidLintingLocalization<*>> {
                    return listOf(*following)
                }
            }
        }
    }
}

enum class SolidLintingLocalizationLevel {
    DIRECTORY,
    FILE,
    JSON;
}