package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val log: Log,
    val inbounds: List<Inbounds>,
    val outbounds: List<Outbounds>,
) {

    @Serializable
    data class Log(
        val loglevel: String = "warning"
    )

    @Serializable
    data class Inbounds(
        val port: Int = 443,
        val protocol: String = "vless",
        val settings: Settings = Settings(),
        val streamSettings: StreamSettings = StreamSettings(),
        val sniffing: Sniffing = Sniffing(),
    ) {
        @Serializable
        data class Settings(
            val clients: List<Client> = listOf(Client()),
            val decryption: String = "none",
        ) {
            @Serializable
            data class Client(
                val id: String = "be757097-a612-4299-a723-85bccd9bb8a3",
                val flow: String = "xtls-rprx-vision",
            )
        }

        @Serializable
        data class StreamSettings(
            val network: String = "tcp",
            val security: String = "reality",
            val realitySettings: RealitySettings = RealitySettings(),
        ) {
            @Serializable
            data class RealitySettings(
                val dest: String = "yahoo.com:443",
                val serverNames: List<String> = listOf(
                    "yahoo.com",
                    "www.yahoo.com"
                ),
                val privateKey: String = "YFTb_C9PvLteXDCRlQWYSVzdN2auXPZWvclCabUG2kM",
                val shortIds: List<String> = listOf(
                    "4a486b04bbdf"
                )
            )
        }

        @Serializable
        data class Sniffing(
            val enabled: Boolean = true,
            val destOverride: List<String> = listOf("http", "tls"),
        )
    }

    @Serializable
    data class Outbounds(
        val protocol: String = "freedom",
        val tag: String = "direct",
    )
}

