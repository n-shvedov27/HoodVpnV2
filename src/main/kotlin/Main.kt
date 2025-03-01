package org.example

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication

fun main() {
    val botsApplication = TelegramBotsLongPollingApplication()
    botsApplication.registerBot(BotParams.BOT_TOKEN, HoodVpnBot())
}