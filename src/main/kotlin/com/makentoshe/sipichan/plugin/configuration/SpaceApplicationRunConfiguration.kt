package com.makentoshe.sipichan.plugin.configuration

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.makentoshe.sipichan.plugin.StringsBundle
import com.makentoshe.sipichan.plugin.configuration.model.TunnelingTool
import org.jdom.Element
import java.io.File

class SpaceApplicationRunConfiguration(
    project: Project, factory: ConfigurationFactory
) : RunConfigurationBase<RunProfileState>(
    project, factory, StringsBundle.string("space.configuration.name")
) {

    companion object {
        val tunnelingToolKey = Key.create<TunnelingTool>("tunneling-tool")
        val tunnelingToolPathKey = Key.create<String>("tunneling-tool-path")
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return SpaceApplicationRunConfigurationSettingsEditor()
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)

        val tunnelingToolElement = Element(tunnelingToolKey.toString())
        tunnelingToolElement.text = getUserData(tunnelingToolKey)?.title
        element.addContent(tunnelingToolElement)

        val tunnelingToolPathElement = Element(tunnelingToolPathKey.toString())
        tunnelingToolPathElement.text = getUserData(tunnelingToolPathKey)
        element.addContent(tunnelingToolPathElement)
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)

        val tunnelingToolElement = element.getChild(tunnelingToolKey.toString())
        putUserData(tunnelingToolKey, TunnelingTool.getToolByTitle(tunnelingToolElement.text))

        val tunnelingToolPath = element.getChild(tunnelingToolPathKey.toString())
        putUserData(tunnelingToolPathKey, tunnelingToolPath?.text)
    }

    // TODO run ApplicationKt.module in parallel with local tunnel service
    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        return when(getUserData(tunnelingToolKey)) {
            TunnelingTool.Localtunnel -> getLocaltunnelState(executor, environment)
            else -> null
        }

    }

    // TODO implement starting local tunnel service in background
    // Example: npx localtunnel --port 8080 --subdomain makentoshe
    private fun getLocaltunnelState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        // todo handle null
        val tunnelingToolPath = getUserData(tunnelingToolPathKey) ?: return null
        val tunnelingToolFile = File(tunnelingToolPath)
        if (tunnelingToolFile.isDirectory && tunnelingToolFile.list()?.contains("npx") == true) {
            println("Execute localtunnel $tunnelingToolFile")
        }
        if (tunnelingToolFile.isFile && tunnelingToolFile.nameWithoutExtension == "npx") {
            println("Execute localtunnel ${tunnelingToolFile.parentFile}")
        }

        return null
    }
}