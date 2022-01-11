version = generateVersion(ConfigureApp.version)

fun generateVersion(version: String): String {
    val branchName = getBranchName()
    val isDevelopBranch = "develop" == branchName || "master" == branchName
    if (isDevelopBranch) {
        return version
    }
    val sb: StringBuilder = StringBuilder()
    sb.append(version)
    sb.append("-")
    sb.append(branchName)
    sb.append("-")
    sb.append("SNAPSHOT")
    return sb.toString()
}

fun getBranchName(): String {
    val process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD")
    val sb: StringBuilder = StringBuilder()
    while (true) {
        val valueTemp = process.inputStream.read()
        if (valueTemp == -1) break
        sb.append(valueTemp.toChar())
    }
    return sb.toString().trim().replace("\n", "")
}

tasks.register("printVersion") {
    doLast {
        println("Here - version: " + ConfigureApp.version)
        println("Here - generateVersion: " + generateVersion(ConfigureApp.version))
        println("Here - absolutePath: ${projectDir.absolutePath}") // {PATH}\Mapi\maps
        println("Here - projectDir: ${project.projectDir}") // {PATH}\Mapi\maps
        println("Here - buildDir: $buildDir") // {PATH}\Mapi\maps\build
    }
}
