package com.makentoshe.sipichan.plugin.wizard.strategy

interface SpaceModuleBuilderProperties {
    val group: String
    val artifact: String
    val version: String

    fun attributes(): Map<String, String>
}

data class GradleSpaceModuleBuilderProperties(
    override val group: String,
    override val artifact: String,
    override val version: String
) : SpaceModuleBuilderProperties {

    override fun attributes() = mapOf(
        "GRADLE_GROUP_ID" to group,
        "GRADLE_ARTIFACT_ID" to artifact,
        "GRADLE_VERSION" to version
    )
}