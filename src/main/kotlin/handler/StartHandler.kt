package org.example.handler

import org.example.base.UpdateHandler
import org.example.delegate.CreateClientDelegate
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.generics.TelegramClient

class StartHandler(
    private val client: TelegramClient
) : UpdateHandler {

    private val createClientDelegate = CreateClientDelegate()

    private val welcomeText = """
        Добро пожаловать в HoodVpn! 👋

        Мы предлагаем стабильный VPN-сервис:

        — Cкорость до 1 Гб/сек.
        — Лимит трафика до 500ГБ.
        — Фильтрация рекламы на YouTube
        — Дополнительные локации

        Меню находится в клавиатуре. Вы можете выбрать интересующий раздел или сразу перейти к установке VPN.

        Выберите ваше устройство:
    """.trimIndent()

    private val selectDeviceText = "Выберите ваше устройство:"

    private val androidButton = InlineKeyboardButton("Android").apply {
        callbackData = CreateClientHandler.Command.CREATE_ANDROID_CLIENT.name
    }
    private val iosButton = InlineKeyboardButton("IOS").apply {
        callbackData = CreateClientHandler.Command.CREATE_IOS_CLIENT.name
    }
    private val selectDevicesKeyboard = InlineKeyboardMarkup(listOf<InlineKeyboardRow>()).apply {
        keyboard = listOf(
            InlineKeyboardRow().apply {
                add(androidButton)
                add(iosButton)
            }
        )
    }

    private val menuKeyboardRow = mutableListOf(
        KeyboardRow().apply {
            add("Профиль")
            add("Установить VPN")
        }
    )

    private val menuKeyboard = ReplyKeyboardMarkup(menuKeyboardRow).apply {
        this.resizeKeyboard = true
    }

    override fun canHandle(update: Update): Boolean {
        return update.message?.text == "/start"
    }

    override fun handle(update: Update) {
        createClientDelegate.createClient(update.message.from.userName)
        val welcomeTextMessage = SendMessage(update.message.chatId.toString(), welcomeText).apply {
            replyMarkup = menuKeyboard
        }
        client.execute(welcomeTextMessage)

        val selectDeviceMessage = SendMessage(update.message.chatId.toString(), selectDeviceText).apply {
            replyMarkup = selectDevicesKeyboard
        }
        client.execute(selectDeviceMessage)
    }
}