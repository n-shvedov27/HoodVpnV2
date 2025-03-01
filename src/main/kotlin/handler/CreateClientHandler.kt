package org.example.handler

import org.example.base.UpdateHandler
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient

class CreateClientHandler (
    private val client: TelegramClient
) : UpdateHandler {
    override fun canHandle(update: Update): Boolean {
        return update.message?.text == "/create_client"
    }

    override fun handle(update: Update) {
        TODO("Not yet implemented")
    }
}