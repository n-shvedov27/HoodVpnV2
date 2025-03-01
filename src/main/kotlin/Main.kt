package org.example

import kotlinx.serialization.json.Json
import org.example.delegate.RestartVpnDelegate
import org.example.delegate.VpnConfigDelegate
import org.example.delegate.VpnKeysDelegate
import org.example.model.Config
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import java.io.File

private val vpnKeysDelegate = VpnKeysDelegate()
private val vpnConfigDelegate = VpnConfigDelegate()
private val restartVpnDelegate = RestartVpnDelegate()

fun main() {
    println(createClient("cookie027"))
    restartVpnDelegate.restartVpn()
}

private fun writeClientIdToFile(clientName: String, clientId: String) {
    val clientFile = File("server_config/clients/$clientName")
    clientFile.createNewFile()
    clientFile.writeText(clientId)
}

private fun writeClientIdToConfig(clientId: String) {
    val json = Json { encodeDefaults = true }
    val configFile = File("/usr/local/etc/xray/config.json")
    val config = json.decodeFromString<Config>(configFile.readText())
    config.inbounds.first().streamSettings.realitySettings.shortIds.add(clientId)
    val configString = json.encodeToString(config)
    configFile.writeText(configString)
}

private fun generateLink(clientId: String, clientName: String) : String {
    val uuid = vpnKeysDelegate.getUuid()
    val publicKey = vpnKeysDelegate.getPublicKey()
    val serverName = vpnConfigDelegate.getServerNames().first()
    return "vless://$uuid@194.226.169.193:443?type=tcp&security=reality&pbk=$publicKey&fp=chrome&sni=$serverName&sid=$clientId&flow=xtls-rprx-vision#$clientName"
}

fun createClient(clientName: String) : String {
    val clientId = getRandomString(6)
    writeClientIdToFile(clientName = clientName, clientId = clientId)
    writeClientIdToConfig(clientId = clientId)
    return generateLink(clientId = clientId, clientName = clientName)
}

fun getRandomString(length: Int) : String {
    val allowedChars = ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

private fun startBot() {
    val botsApplication = TelegramBotsLongPollingApplication()
    botsApplication.registerBot(BotParams.BOT_TOKEN, HoodVpnBot())
}