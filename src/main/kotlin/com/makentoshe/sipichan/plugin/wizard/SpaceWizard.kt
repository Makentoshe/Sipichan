package com.makentoshe.sipichan.plugin.wizard

enum class ProjectType {
    BLANK, CHATBOT
}

sealed class BuildSystem {
    abstract val group: String
    abstract val artifact: String
    abstract val version: String

    data class Gradle(
        override val group: String,
        override val artifact: String,
        override val version: String
    ) : BuildSystem()
}

class SpaceWizard2 {

    class Builder {

        var projectType: ProjectType = ProjectType.BLANK

        var buildSystem: BuildSystem = BuildSystem.Gradle("com.example", "untitled", "0.0.1")

//        var properties = GradleSpaceModuleBuilderProperties("com.example", "untitled", "0.0.1")

        fun build(): SpaceWizard2 {
            return SpaceWizard2()
        }

//        fun strategy(): SpaceModuleBuilderStrategy = when (projectType) {
//            ProjectType.BLANK -> when (buildSystem) {
//                is BuildSystem.Gradle -> {
//                    GradleSpaceModuleBuilderStrategy
//                }
//            }
//        }
    }
}
