package org.example.delegate

import kotlinx.serialization.json.Json
import org.example.model.Config
import java.io.File

class CreateClientDelegate {

    private val vpnKeysDelegate = VpnKeysDelegate()
    private val vpnConfigDelegate = VpnConfigDelegate()
    private val restartVpnDelegate = RestartVpnDelegate()

    fun createClient(clientName: String) : String {
        return when (val storedClientId = storedClientId(clientName)) {
            null -> {
                val clientId = getRandomString(12)
                writeClientIdToFile(clientName = clientName, clientId = clientId)
                writeClientIdToConfig(clientId = clientId)
                val link = generateLink(clientId = clientId, clientName = clientName)
                restartVpnDelegate.restartVpn()
                link
            }
            else -> generateLink(clientId = storedClientId, clientName = clientName)
        }
    }

    private fun storedClientId(clientName: String): String? {
        val clientFile = File("clients/$clientName")
        return if (clientFile.exists()) clientFile.readText().trim() else null
    }

    private fun writeClientIdToFile(clientName: String, clientId: String) {
        val clientFile = File("clients/$clientName")
        clientFile.parentFile.mkdirs()
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



    private fun getRandomString(length: Int) : String {
        val allowedChars = ('a'..'f') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}