package org.example

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient

class HoodVpnBot : LongPollingSingleThreadUpdateConsumer {

    private val client: TelegramClient = OkHttpTelegramClient(BotParams.BOT_TOKEN)

    override fun consume(update: Update) {
        val message = SendMessage(update.message.chatId.toString(), "Кажется, я не знаю эту команду. Введите /start, чтобы попасть в главное меню.")
        client.execute(message)
    }
}