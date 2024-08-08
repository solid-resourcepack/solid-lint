package io.github.solid.resourcepack.lint.linting.model

import io.github.solid.resourcepack.lint.SolidLinter
import io.github.solid.resourcepack.lint.result.SolidLintingResult
import team.unnamed.creative.model.Model

class SolidModelLinter(parent: SolidLinter, private val self: Model): SolidLinter(parent.resourcePack, parent.strict) {
    override fun lint(): List<SolidLintingResult> {
        val results = mutableListOf<SolidLintingResult>()
        self.parent()?.let {
            results.addAll(SolidParentRelationLinter(this, self.key(), it).lint())
        }
        results.addAll(SolidTexturesRelationLinter(this, self, self.textures()).lint())

        return results
    }
}