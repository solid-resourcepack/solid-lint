package io.github.solid.resourcepack.lint.localization

interface SolidTraceable<Ref> {
    fun getPath(): String
    fun walk(ref: Ref): Ref
}