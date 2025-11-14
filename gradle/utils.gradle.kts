import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

// === 定义基础路径 ===
val docsDir = project.file("docs")
// TODO("坏文明")
val deployDir = project.file("deploy/MzLib")
val metaFile = project.file("docs/lib/meta.typ")

// === 文件工具函数 ===

fun findTypstInPath(): File? {
    val paths = System.getenv("PATH")?.split(File.pathSeparator) ?: return null
    val exeName = if (System.getProperty("os.name").startsWith("Windows", true)) "typst.exe" else "typst"
    return paths.map { File(it, exeName) }.firstOrNull { it.exists() && it.canExecute() }
}

fun copyFilesRecursively(srcDir: File, destDir: File, excludeExt: String? = null) {
    srcDir.walkTopDown()
        .filter { it.isFile && (excludeExt == null || it.extension != excludeExt) }
        .forEach { file ->
            val dest = destDir.resolve(file.relativeTo(srcDir))
            dest.parentFile.mkdirs()
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
}

lateinit var generateFileTree: (File, String, Boolean, Appendable) -> Unit

generateFileTree = { dir, indent, isRoot, output ->
    val dirName = dir.name
    if (isRoot) output.append("(") else output.append("${indent}\"$dirName\": (")

    val items = dir.listFiles()
        ?.filterNot { it.name.startsWith(".") }
        ?.sortedBy { it.name }
        ?: emptyList()

    var first = true
    for (item in items) {
        if (first) {
            output.appendLine()
            first = false
        } else output.appendLine(",")

        if (item.isDirectory) {
            generateFileTree(item, "$indent    ", false, output)
        } else {
            output.append("${indent}    \"${item.name}\": none")
        }
    }

    if (first) output.append(":)") else {
        output.appendLine()
        output.append("${indent})")
    }
}

// === 暴露给主脚本使用 ===
project.extra["docsDir"] = docsDir
project.extra["deployDir"] = deployDir
project.extra["metaFile"] = metaFile
project.extra["findTypstInPath"] = ::findTypstInPath
project.extra["copyFilesRecursively"] = ::copyFilesRecursively
project.extra["generateFileTree"] = generateFileTree
