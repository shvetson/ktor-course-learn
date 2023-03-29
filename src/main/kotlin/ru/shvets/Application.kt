package ru.shvets

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.config.yaml.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import net.mamoe.yamlkt.toYamlElementOrNull
import org.ktorm.dsl.*
import ru.shvets.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    val yamlConfig = YamlConfig (path = "application.yaml")

    configureSerialization()

    if (yamlConfig != null) {
        configureSecurity(yamlConfig)
        configureRouting(yamlConfig)
    }

//    contactUsModule()
}

//fun main() {
//
//    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
//        configureRouting()
//    }.start(wait = true)
//}

