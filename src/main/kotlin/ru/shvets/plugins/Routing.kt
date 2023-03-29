package ru.shvets.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.config.yaml.*
import ru.shvets.route.authenticationRoute
import ru.shvets.route.default
import ru.shvets.route.notesRoute

fun Application.configureRouting(config: YamlConfig) {
    routing {
        default()
    }
    notesRoute()
    authenticationRoute(config)
}

