package io.github.solid.resourcepack.lint.linting.model

import io.github.solid.resourcepack.lint.SolidLinter
import io.github.solid.resourcepack.lint.localization.SolidFileTraceable
import io.github.solid.resourcepack.lint.localization.SolidJsonTraceable
import io.github.solid.resourcepack.lint.localization.SolidLintingLocalization
import io.github.solid.resourcepack.lint.result.SolidLintingResult
import io.github.solid.resourcepack.material.SolidMaterialTexture
import team.unnamed.creative.model.Model
import team.unnamed.creative.model.ModelTexture
import team.unnamed.creative.model.ModelTextures

class SolidTexturesRelationLinter(linter: SolidLinter, private val self: Model, private val textures: ModelTextures) :
    SolidLinter(linter.resourcePack, linter.strict) {
    override fun lint(): List<SolidLintingResult> {
        val results = mutableListOf<SolidLintingResult>()
        textures.layers().forEachIndexed { index, layer ->
            results.addAll(SolidTextureRelationLinter(this, self, "layer$index", layer).lint())
        }
        textures.particle()?.let { particle ->
            results.addAll(SolidTextureRelationLinter(this, self, "particle", particle).lint())
        }
        textures.variables().forEach { (layer, key) ->
            results.addAll(SolidTextureRelationLinter(this, self, layer, key).lint())
        }
        return results
    }
}

class SolidTextureRelationLinter(
    parent: SolidLinter,
    private val self: Model,
    private val layer: String,
    private val texture: ModelTexture
) : SolidLinter(parent.resourcePack, parent.strict) {
    override fun lint(): List<SolidLintingResult> {
        val results = mutableListOf<SolidLintingResult>()
        texture.key()?.let {
            if (resourcePack.texture(it) == null && !SolidMaterialTexture.exists(it)) {
                results.add(SolidLintingResult.errorOnStrict(this, "Texture not found: $it", generateLocalization()))
            }
        }
        return results
    }

    private fun generateLocalization(): SolidLintingLocalization<*> {
        val jsonLocalization = SolidLintingLocalization.json(SolidJsonTraceable("textures.$layer"))
        val fileLocalization = SolidLintingLocalization.file(SolidFileTraceable(self.key()), jsonLocalization)
        return fileLocalization
    }
}