package com.makentoshe.sipichan.plugin.configuration

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project

class SpaceApplicationRunConfigurationFactory(type: SpaceApplicationRunConfigurationType): ConfigurationFactory(type) {

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return SpaceApplicationRunConfiguration(project, this)
    }

    override fun getId(): String {
        return SpaceApplicationRunConfigurationType.id
    }
}