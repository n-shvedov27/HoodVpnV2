package org.example.delegate

import kotlinx.serialization.json.Json
import org.example.model.Config
import java.io.File

class InitVpnDelegate {

    private val vpnKeysDelegate = VpnKeysDelegate()
    private val restartVpnDelegate = RestartVpnDelegate()

    fun initServer() {
        vpnKeysDelegate.generateKeys()
        initConfig()
        restartVpnDelegate.restartVpn()
    }

    private fun initConfig() {
        val json = Json { encodeDefaults = true }

        val config = Config(
            inbounds = listOf(
                Config.Inbounds(
                    settings = Config.Inbounds.Settings(
                        clients = listOf(
                            Config.Inbounds.Settings.Client(
                                id = vpnKeysDelegate.getUuid()
                            )
                        )
                    ),
                    streamSettings = Config.Inbounds.StreamSettings(
                        realitySettings = Config.Inbounds.StreamSettings.RealitySettings(
                            privateKey = vpnKeysDelegate.getPrivateKey()
                        )
                    )
                )
            )
        )

        val configString = json.encodeToString(config)
        val configFile = File("/usr/local/etc/xray/config.json").apply { createNewFile() }
        configFile.writeText(configString)
    }
}