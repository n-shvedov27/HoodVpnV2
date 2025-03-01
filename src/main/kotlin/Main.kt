package org.example

import kotlinx.serialization.json.Json
import org.example.model.Config
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import java.io.File

fun main() {
    initServer()
}

private fun generateKeys() {
    val command = "scripts/generate_keys.sh"
    val process = Runtime.getRuntime().exec(command)

    while (process.isAlive) {
        println("Generate keys")
        Thread.sleep(100)
    }
    println("Keys generated")
}

private fun restartVpn() {
    val command = "scripts/restart_vpn.sh"
    val process = Runtime.getRuntime().exec(command)

    while (process.isAlive) {
        println("Wait restart")
        Thread.sleep(100)
    }
    println("Restarted")
}

private fun getPublicKey(): String {
    val prefix = "Public key: "
    return File("/server_config/vless_uuid").readLines()
        .first { it.contains(prefix) }
        .replace(prefix, "")
}

private fun getPrivateKey(): String {
    val prefix = "Private key: "
    return File("/server_config/vless_uuid").readLines()
        .first { it.contains(prefix) }
        .replace(prefix, "")
}

private fun getUuid(): String {
    return File("/server_config/vless_uuid").readText()
}

private fun initConfig() {
    val json = Json { encodeDefaults = true }

    val config = Config(
        inbounds = listOf(
            Config.Inbounds(
                settings = Config.Inbounds.Settings(
                    clients = listOf(
                        Config.Inbounds.Settings.Client(
                            id = getUuid()
                        )
                    )
                ),
                streamSettings = Config.Inbounds.StreamSettings(
                    realitySettings = Config.Inbounds.StreamSettings.RealitySettings(
                        privateKey = getPrivateKey()
                    )
                )
            )
        )
    )

    val configString = json.encodeToString(config)
    val configFile = File("/usr/local/etc/xray/config.json")
    configFile.writeText(configString)
}

private fun initServer() {
    generateKeys()
    initConfig()
    restartVpn()
}

private fun startBot() {
    val botsApplication = TelegramBotsLongPollingApplication()
    botsApplication.registerBot(BotParams.BOT_TOKEN, HoodVpnBot())
}