package io.github.solid.resourcepack.lint.linting.texture

import io.github.solid.resourcepack.lint.SolidLinter
import io.github.solid.resourcepack.lint.result.SolidLintingResult
import net.kyori.adventure.key.Key
import team.unnamed.creative.texture.Texture

class SolidTextureLinter(parent: SolidLinter, private val self: Texture): SolidLinter(parent.resourcePack, parent.strict) {
    override fun lint(): List<SolidLintingResult> {

        return listOf()
    }
}