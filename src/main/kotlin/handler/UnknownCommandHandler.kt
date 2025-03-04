package org.example.handler

import org.example.base.UpdateHandler
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient

class UnknownCommandHandler(
    private val client: TelegramClient
) : UpdateHandler {

    companion object {
        private const val UNKNOWN_COMMAND_MESSAGE = """
            "Кажется, я не знаю эту команду. Введите /start, чтобы попасть в главное меню."
        """

    }

    override fun canHandle(update: Update) = true

    override fun handle(update: Update) {
        val message = SendMessage(update.message.chatId.toString(), UNKNOWN_COMMAND_MESSAGE)
        client.execute(message)
    }
}