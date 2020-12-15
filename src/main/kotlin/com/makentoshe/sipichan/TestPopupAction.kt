package com.makentoshe.sipichan

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages


class TestPopupDialogAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        // Using the event, create and show a dialog
        val currentProject = e.project
        val message = StringBuilder("${e.presentation.text} Selected!")
        val title = e.presentation.description

        // If an element is selected in the editor, add info about it.
        e.getData(CommonDataKeys.NAVIGATABLE)?.let { navigatable ->
            message.append(String.format("\nSelected Element: %s", navigatable.toString()))
        }
        Messages.showMessageDialog(currentProject, message.toString(), title, Messages.getInformationIcon())
    }

    override fun update(e: AnActionEvent) {
        // Set the availability based on whether a project is open
        // Action allowed after project indexing
        e.presentation.isEnabledAndVisible = e.project != null
    }
}