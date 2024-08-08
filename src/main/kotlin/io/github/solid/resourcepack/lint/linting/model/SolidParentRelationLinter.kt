package io.github.solid.resourcepack.lint.linting.model

import io.github.solid.resourcepack.lint.SolidLinter
import io.github.solid.resourcepack.lint.localization.SolidFileTraceable
import io.github.solid.resourcepack.lint.localization.SolidJsonTraceable
import io.github.solid.resourcepack.lint.localization.SolidLintingLocalization
import io.github.solid.resourcepack.lint.result.SolidLintingResult
import io.github.solid.resourcepack.material.SolidMaterial
import io.github.solid.resourcepack.material.SolidMaterialParent
import net.kyori.adventure.key.Key

class SolidParentRelationLinter(parent: SolidLinter, private val self: Key, private val target: Key) :
    SolidLinter(parent.resourcePack, parent.strict) {

    override fun lint(): List<SolidLintingResult> {
        if (target.namespace() == Key.MINECRAFT_NAMESPACE) {
            try {
                if(SolidMaterialParent.exists(target)) return listOf()
                SolidMaterial.from(target)
            } catch (e: Exception) {

                //Only error in strict mode, because there are some template minecraft parents that can not be linted
                return listOf(
                    SolidLintingResult.errorOnStrict(
                        this,
                        "Target model might not be minecraft material: $target",
                        localization = generateLocalization()
                    )
                )
            }
        } else if (resourcePack.model(target) == null) {
            return listOf(SolidLintingResult.error("Target model not found: $target", generateLocalization()))
        } else if (self == target) {
            return listOf(SolidLintingResult.error("Model cannot target itself: $target", generateLocalization()))
        }

        return listOf()
    }

    private fun generateLocalization(): SolidLintingLocalization<*> {
        val jsonLocalization = SolidLintingLocalization.json(SolidJsonTraceable("parent"))
        val fileLocalization = SolidLintingLocalization.file(SolidFileTraceable(self), jsonLocalization)
        return fileLocalization
    }

}