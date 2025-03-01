package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val log: Log = Log(),
    val inbounds: List<Inbounds> = listOf(Inbounds()),
    val outbounds: List<Outbounds> = listOf(Outbounds()),
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
                /**
                 * здесь вставить uuid, сгенерированный на шаге №2
                 */
                val id: String = "",
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
                /**
                 * Вставить приватный ключ (Private key), созданный на шаге №2
                 */
                val privateKey: String = "",
                /**
                 * Список уникальных коротких идентификаторов, доступных клиентам, чтобы их различать
                 * Длина: от 2 до 16 символов. Используемые символы: 0-f.
                 * Для удобства, значения можно сгенерировать командой `i`
                 */
                val shortIds: MutableList<String> = mutableListOf("30gl0kwlh0w8")
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

