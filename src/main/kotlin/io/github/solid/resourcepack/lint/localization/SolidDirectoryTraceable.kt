package io.github.solid.resourcepack.lint.localization

import net.kyori.adventure.key.Key
import java.nio.file.Path
import kotlin.io.path.isDirectory

class SolidDirectoryTraceable(path: Key): SolidFileTraceable(path) {
    override fun walk(ref: Path): Path {
        val path = super.walk(ref)
        if(!path.isDirectory()) throw IllegalArgumentException("Path is not a directory")
        return path
    }
}
