package com.makentoshe.sipichan.plugin.configuration

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.makentoshe.sipichan.plugin.StringsBundle

class SpaceApplicationRunConfiguration(
    project: Project, factory: ConfigurationFactory
) : RunConfigurationBase<RunProfileState>(
    project, factory, StringsBundle.string("space.configuration.name")
) {

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return SpaceApplicationRunConfigurationSettingsEditor()
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        // TODO implement starting local tunnel service in background
        // Example: npx localtunnel --port 8080 --subdomain makentoshe

        // TODO run ApplicationKt.module in parallel with local tunnel service
        return null
    }

}