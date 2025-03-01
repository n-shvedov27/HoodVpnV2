package org.example.delegate

class RestartVpnDelegate {

    fun restartVpn() {
        val command = "scripts/restart_vpn.sh"
        val process = Runtime.getRuntime().exec(command)

        while (process.isAlive) {
            println("Wait restart")
            Thread.sleep(100)
        }
        println("Restarted")
    }
}