package com.makentoshe.sipichan.plugin.configuration

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.makentoshe.sipichan.plugin.IconProvider
import javax.swing.Icon

class DemoRunConfigurationType : ConfigurationType {

    override fun getDisplayName(): String {
        return "Sipichan Demo Configuration"
    }

    override fun getConfigurationTypeDescription(): String {
        return "Sipochan demo configuration description"
    }

    override fun getIcon(): Icon {
        return IconProvider.logo
    }

    override fun getId(): String {
        return "SipichanDemoConfiguration"
    }

    override fun getConfigurationFactories(): Array<ConfigurationFactory> {
        return arrayOf(DemoRunConfigurationFactory(this))
    }
}
