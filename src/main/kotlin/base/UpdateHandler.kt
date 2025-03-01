package org.example.base

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateHandler {

    fun canHandle(update: Update): Boolean
    fun handle(update: Update)
}