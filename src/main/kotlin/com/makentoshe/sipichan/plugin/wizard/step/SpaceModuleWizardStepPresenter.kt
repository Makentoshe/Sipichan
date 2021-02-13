package com.makentoshe.sipichan.plugin.wizard.step

import java.net.URL

interface SpaceModuleWizardStepPresenter {

    fun getCurrentSpaceInstanceString(): String

    fun getCurrentSpaceInstanceUrl(): URL?

    fun showNetworkCheckProgressBar()

    fun hideNetworkCheckProgressBar()

    fun showDescription(string: String)

    fun hideDescription()

    fun showContent()

    fun hideContent()
}