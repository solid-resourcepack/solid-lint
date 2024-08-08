package io.github.solid.resourcepack.lint

import io.github.solid.resourcepack.lint.linting.model.SolidModelLinter
import io.github.solid.resourcepack.lint.linting.model.SolidParentRelationLinter
import io.github.solid.resourcepack.lint.linting.texture.SolidTextureLinter
import io.github.solid.resourcepack.lint.result.SolidLintingResult
import team.unnamed.creative.ResourcePack
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackReader
import java.io.File

fun main() {
    val linter = SolidLinterBuilder()
        .resourcePack(MinecraftResourcePackReader.minecraft().readFromDirectory(File("pack")))
        .strict(true)
        .build()
    linter.lint().forEach { it.printAll() }
}

open class SolidLinter(val resourcePack: ResourcePack, val strict: Boolean) {
    open fun lint(): List<SolidLintingResult> {
        val results = mutableListOf<SolidLintingResult>()
        resourcePack.models().forEach { model ->
            results.addAll(SolidModelLinter(this, model).lint())
        }
        resourcePack.textures().forEach { texture ->
            results.addAll(SolidTextureLinter(this, texture).lint())
        }
        return results
    }


}

class SolidLinterBuilder {
    private var resourcePack: ResourcePack? = null
    private var strict: Boolean = false

    fun resourcePack(resourcePack: ResourcePack) = apply { this.resourcePack = resourcePack }
    fun strict(strict: Boolean) = apply { this.strict = strict }

    fun build(): SolidLinter {
        return SolidLinter(resourcePack!!, strict)
    }
}