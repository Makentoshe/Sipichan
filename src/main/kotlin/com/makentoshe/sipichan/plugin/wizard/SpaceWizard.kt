package com.makentoshe.sipichan.plugin.wizard

import com.makentoshe.sipichan.plugin.wizard.source.BlankProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.source.ChatbotProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.source.ProjectSourceProvider
import com.makentoshe.sipichan.plugin.wizard.strategy.GradleSpaceModuleBuilderStrategy
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderProperties
import com.makentoshe.sipichan.plugin.wizard.strategy.SpaceModuleBuilderStrategy

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

class SpaceWizard(
    var projectType: ProjectType = ProjectType.BLANK,
    var buildSystem: BuildSystem = BuildSystem.Gradle("com.example", "untitled", "0.0.1")
) {

    fun strategy(): SpaceModuleBuilderStrategy {
        val properties = SpaceModuleBuilderProperties(buildSystem)
        return when (projectType) {
            ProjectType.BLANK -> {
                strategy(properties, BlankProjectSourceProvider(properties))
            }
            ProjectType.CHATBOT -> {
                strategy(properties, ChatbotProjectSourceProvider(properties))
            }
        }
    }

    private fun strategy(properties: SpaceModuleBuilderProperties, provider: ProjectSourceProvider) = when (buildSystem) {
        is BuildSystem.Gradle -> {
            GradleSpaceModuleBuilderStrategy(properties, provider)
        }
    }
}
