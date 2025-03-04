package org.example.handler

import org.example.base.UpdateHandler
import org.example.base.parseCommand
import org.example.delegate.CreateClientDelegate
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient

class CreateClientHandler (
    private val client: TelegramClient
) : UpdateHandler {

    private val createClientDelegate = CreateClientDelegate()

    private val installAndroidInstructions = """
        Для подключения VPN:
    
        1. Скачайте приложение «Hiddify» через <a href='https://play.google.com/store/apps/details?id=app.hiddify.com'>Google Play</a> или <a href='https://github.com/hiddify/hiddify-next/releases/download/v1.5.2/Hiddify-Android-universal.apk'>apk-файл</a>.
        2. Скопируйте ключ из следующего сообщения.
        3. Зайдите в приложение Hiddify, нажмите "Новый профиль" → "Добавить из буфера обмена". Готово!
    """.trimIndent()

    private val installIosInstructions = """
        Для подключения VPN:
    
        1. Скачайте приложение <a href='https://apps.apple.com/us/app/streisand/id6450534064'>«Streisend»</a>.
        2. Скопируйте ключ из следующего сообщения.
        3. Зайдите в приложение Streisand, нажмите на плюс в правом углу экрана → Добавить из буфера. Готово!    
    """.trimIndent()

    private fun keyMessage(key: String): String {
        return """
            Ваш ключ:

            `$key`

            Нажмите один раз прямо на него, чтобы скопировать в буфер обмена.
        """.trimIndent()
    }

    override fun canHandle(update: Update): Boolean {
        return parseCommand<Command>(update.callbackQuery.data) != null
    }

    override fun handle(update: Update) {
        when (parseCommand<Command>(update.callbackQuery.data)) {
            Command.CREATE_ANDROID_CLIENT -> handleInstallAndroidCommand(update)
            Command.CREATE_IOS_CLIENT -> handleInstallIosCommand(update)
            null -> Unit
        }
    }

    private fun handleInstallAndroidCommand(update: Update) {
        val connectLink = createClientDelegate.createClient(update.message.from.userName)
//        val connectLink = "vless://58df263e-d00b-4d41-9ac5-043d895d2720@194.226.169.193:443?type=tcp&security=reality&pbk=NwIqni9kqF9IhpSXXPqUQbPg-1MfNJJ3KY8dWc8V-lM&fp=chrome&sni=yahoo.com&sid=cf3ede39d7d7&flow=xtls-rprx-vision#cookie027"

        val instructionsMessage = SendMessage(
            update.callbackQuery.message.chatId.toString(),
            installAndroidInstructions,
        ).apply {
            disableWebPagePreview()
            parseMode = ParseMode.HTML
        }
        val keyMessage = SendMessage(
            update.callbackQuery.message.chatId.toString(),
            keyMessage(connectLink)
        ).apply {
            parseMode = ParseMode.MARKDOWN
            disableWebPagePreview()
        }
        client.execute(instructionsMessage)
        client.execute(keyMessage)
    }

    private fun handleInstallIosCommand(update: Update) {
        val connectLink = createClientDelegate.createClient(update.message.from.userName)
//        val connectLink = "vless://58df263e-d00b-4d41-9ac5-043d895d2720@194.226.169.193:443?type=tcp&security=reality&pbk=NwIqni9kqF9IhpSXXPqUQbPg-1MfNJJ3KY8dWc8V-lM&fp=chrome&sni=yahoo.com&sid=cf3ede39d7d7&flow=xtls-rprx-vision#cookie027"

        val instructionsMessage = SendMessage(
            update.callbackQuery.message.chatId.toString(),
            installIosInstructions
        ).apply {
            parseMode = ParseMode.HTML
            disableWebPagePreview()
        }
        val keyMessage = SendMessage(
            update.callbackQuery.message.chatId.toString(),
            keyMessage(connectLink)
        ).apply {
            parseMode = ParseMode.MARKDOWN
            disableWebPagePreview()
        }
        client.execute(instructionsMessage)
        client.execute(keyMessage)
    }

    enum class Command {
        CREATE_ANDROID_CLIENT,
        CREATE_IOS_CLIENT,
    }
}