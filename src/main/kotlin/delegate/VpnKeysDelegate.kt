package org.example.delegate

import java.io.File

class VpnKeysDelegate {

    fun generateKeys() {
        val command = "scripts/generate_keys.sh"
        val process = Runtime.getRuntime().exec(command)

        while (process.isAlive) {
            println("Generate keys")
            Thread.sleep(100)
        }
        println("Keys generated")
    }

    fun getPublicKey(): String {
        val prefix = "Public key: "
        return File("server_config/vless_keys").readLines()
            .first { it.contains(prefix) }
            .replace(prefix, "")
            .trim()
    }

    fun getPrivateKey(): String {
        val prefix = "Private key: "
        return File("server_config/vless_keys").readLines()
            .first { it.contains(prefix) }
            .replace(prefix, "")
            .trim()
    }

    fun getUuid(): String {
        return File("server_config/vless_uuid").readText().trim()
    }
}