package org.example.delegate

import kotlinx.serialization.json.Json
import org.example.model.Config
import java.io.File

class VpnConfigDelegate {

    private val json = Json { encodeDefaults = true }

    fun getServerNames(): List<String> {
        val configFile = File("/usr/local/etc/xray/config.json")
        val config = json.decodeFromString<Config>(configFile.readText())
        return config.inbounds.first().streamSettings.realitySettings.serverNames
    }
}