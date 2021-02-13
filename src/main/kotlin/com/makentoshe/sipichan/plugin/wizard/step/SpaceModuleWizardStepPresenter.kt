package com.makentoshe.sipichan.plugin.wizard.step

interface SpaceModuleWizardStepPresenter {

    fun getCurrentSpaceInstanceUrl(): String

    fun showNetworkCheckProgressBar()

    fun hideNetworkCheckProgressBar()

    fun showDescription(string: String)

    fun hideDescription()
}