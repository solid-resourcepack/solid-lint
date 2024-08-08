package io.github.solid.resourcepack.lint.localization

import net.kyori.adventure.key.Key
import java.nio.file.Path

open class SolidFileTraceable(
    private val path: Key,
    private val type: SolidFileType = SolidFileType.MODEL,
): SolidTraceable<Path> {
    override fun getPath(): String {
        return "assets/${path.namespace()}/${type.name.lowercase()}s/${path.value()}"
    }

    override fun walk(ref: Path): Path {
        return ref.resolve(Path.of(getPath()))
    }
}

enum class SolidFileType {
    MODEL,
    TEXTURE,
}