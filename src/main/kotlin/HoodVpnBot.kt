package org.example

import org.example.handler.CreateClientHandler
import org.example.handler.InitVpnHandler
import org.example.handler.StartHandler
import org.example.handler.UnknownCommandHandler
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient

class HoodVpnBot : LongPollingSingleThreadUpdateConsumer {

    private val client: TelegramClient = OkHttpTelegramClient(BotParams.BOT_TOKEN)
    private val unknownCommandHandler = UnknownCommandHandler(client)
    private val handlers = listOf(
        StartHandler(client),
        CreateClientHandler(client),
        InitVpnHandler(client)
    )

    override fun consume(update: Update) {
        val handler = handlers.firstOrNull {
            runCatching { it.canHandle(update) }.getOrDefault(false)
        }
        println("handler: $handler")
        when (handler) {
            null -> unknownCommandHandler.handle(update)
            else -> handler.handle(update)
        }
    }
}