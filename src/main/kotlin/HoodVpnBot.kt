package org.example

import org.example.delegate.CreateClientDelegate
import org.example.delegate.InitVpnDelegate
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient

class HoodVpnBot : LongPollingSingleThreadUpdateConsumer {

    private val createClientDelegate = CreateClientDelegate()
    private val initVpnDelegate = InitVpnDelegate()
    private val client: TelegramClient = OkHttpTelegramClient(BotParams.BOT_TOKEN)

    override fun consume(update: Update) {

        when (update.message.text) {
            "/create_client" -> {
                val link = createClientDelegate.createClient(update.message.from.userName)
                val message = SendMessage(update.message.chatId.toString(), "`$link`")
                client.execute(message)
            }
            "/init_vpn" -> {
                initVpnDelegate.initServer()
                val message = SendMessage(update.message.chatId.toString(), "init succes")
                client.execute(message)
            }
            else -> {
                val message = SendMessage(update.message.chatId.toString(), "Кажется, я не знаю эту команду. Введите /start, чтобы попасть в главное меню.")
                client.execute(message)
            }
        }
    }
}