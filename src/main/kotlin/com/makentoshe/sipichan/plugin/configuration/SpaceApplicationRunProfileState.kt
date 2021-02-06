package com.makentoshe.sipichan.plugin.configuration

import com.intellij.execution.DefaultExecutionResult
import com.intellij.execution.ExecutionResult
import com.intellij.execution.Executor
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ProgramRunner
import com.intellij.terminal.TerminalExecutionConsole
import com.pty4j.PtyProcessBuilder
import java.io.File

// Example: npx localtunnel --port 8080 --subdomain makentoshe
class SpaceApplicationRunProfileState(
    private val executionEnvironment: ExecutionEnvironment,
    private val workDirectory: File
) : RunProfileState {

    private val localtunnelCommands = listOf("npx.cmd", "localtunnel", "--port", "8080", "--subdomain", "makentoshe")

    // All works well but the execution finishes before all data will be printed, so
    // TODO understand how to keep process until all data will be printed
    // This will happen when ktor server run configuration will be finished
    override fun execute(executor: Executor?, runner: ProgramRunner<*>): ExecutionResult? {
        val runConfiguration = executionEnvironment.runProfile
        if (runConfiguration !is SpaceApplicationRunConfiguration) return null

        val ptyProcess = PtyProcessBuilder(localtunnelCommands.toTypedArray())
        ptyProcess.setDirectory(workDirectory.absolutePath)
        val process = ptyProcess.start()

//        thread {
//            val scanner = Scanner(process.inputStream)
//            while (scanner.hasNext()) {
//                println(scanner.nextLine())
//            }
//
//            while (true) {
//                Thread.sleep(1000)
//            }
//        }

        val localtunnel = "Starting localtunnel service..."
        val handler = OSProcessHandler(process, localtunnel)
        val console = TerminalExecutionConsole(executionEnvironment.project, handler)

        return DefaultExecutionResult(console, handler)
    }
}
