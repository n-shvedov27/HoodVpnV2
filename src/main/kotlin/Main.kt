package org.example

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication

fun main() {
    initServer()
}

private fun initServer() {

}

private fun startBot() {
    val botsApplication = TelegramBotsLongPollingApplication()
    botsApplication.registerBot(BotParams.BOT_TOKEN, HoodVpnBot())
}