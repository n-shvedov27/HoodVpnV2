package org.example.base

inline fun <reified T: Enum<T>> parseCommand(command: String): T? {
    val commandFormatted = command.replace("/", "")
    return enumValues<T>().firstOrNull {
        it.name.equals(commandFormatted, ignoreCase = true)
    }
}