package com.makentoshe.sipichan.plugin.wizard.strategy

import com.makentoshe.sipichan.plugin.wizard.BuildSystem

// TODO join properties with SpaceWizard
data class SpaceModuleBuilderProperties(
    val group: String,
    val artifact: String,
    val version: String
) {

    constructor(buildSystem: BuildSystem) : this(buildSystem.group, buildSystem.artifact, buildSystem.version)

    fun attributes() = mapOf(
        "GRADLE_GROUP_ID" to group,
        "GRADLE_ARTIFACT_ID" to artifact,
        "GRADLE_VERSION" to version
    )
}
