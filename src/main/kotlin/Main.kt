package org.example

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication

fun main() {
    initServer()
}

private fun initServer() {
    val command = "scripts/init_server.sh"
    val process = Runtime.getRuntime().exec(command)

    while (process.isAlive) {
        println("wait")
        Thread.sleep(100)
    }
    println("done")
}

private fun startBot() {
    val botsApplication = TelegramBotsLongPollingApplication()
    botsApplication.registerBot(BotParams.BOT_TOKEN, HoodVpnBot())
}