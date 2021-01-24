package com.makentoshe.sipichan.plugin.configuration

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.makentoshe.sipichan.plugin.IconProvider
import com.makentoshe.sipichan.plugin.StringsBundle
import javax.swing.Icon

// TODO may be should be some part of default kotlin run configuration
// https://github.com/JetBrains/kotlin/blob/master/idea/idea-jvm/src/org/jetbrains/kotlin/idea/run/KotlinRunConfigurationType.java
class SpaceApplicationRunConfigurationType : ConfigurationType {

    companion object {
        const val id = "SpaceApplicationConfiguration"
    }

    override fun getDisplayName(): String {
        return StringsBundle.string("space.configuration.name")
    }

    override fun getConfigurationTypeDescription(): String {
        return StringsBundle.string("space.configuration.description")
    }

    override fun getIcon(): Icon {
        return IconProvider.logo
    }

    override fun getId(): String {
        return Companion.id
    }

    override fun getConfigurationFactories(): Array<ConfigurationFactory> {
        return arrayOf(SpaceApplicationRunConfigurationFactory(this))
    }
}
