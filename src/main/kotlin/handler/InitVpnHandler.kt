package org.example.handler

import org.example.base.UpdateHandler
import org.example.delegate.InitVpnDelegate
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient

class InitVpnHandler(
    private val client: TelegramClient
) : UpdateHandler {

    companion object {
        private const val ADMIN_USERNAME = "cookie027"
        private const val INIT_VPN_COMMAND = "init_vpn"
    }

    private val initVpnDelegate = InitVpnDelegate()

    override fun canHandle(update: Update): Boolean {
        val isAdmin = update.message.from.userName == ADMIN_USERNAME
        val isInitCommand = update.message.text == INIT_VPN_COMMAND
        return isAdmin && isInitCommand
    }

    override fun handle(update: Update) {
        initVpnDelegate.initServer()
        val message = SendMessage(update.message.chatId.toString(), "init succes")
        client.execute(message)
    }
}