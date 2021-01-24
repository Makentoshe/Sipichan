package com.makentoshe.sipichan.plugin.configuration

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class DemoRunConfiguration(
    project: Project, factory: ConfigurationFactory
): RunConfigurationBase<RunProfileState>(project, factory, "DemoRunConfigurationName") {

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return DemoRunConfigurationSettingsEditor()
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        // TODO implement starting local tunnel service
        // Example: npx localtunnel --port 8080 --subdomain makentoshe

        // TODO after starting: run ApplicationKt.module
        return null
    }

}