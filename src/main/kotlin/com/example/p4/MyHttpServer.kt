package com.example.p4

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.InetSocketAddress

fun main() {
    val PORT = 80
    val server = HttpServer.create(InetSocketAddress(PORT), 0)
    println("Server started at port: $PORT")
    server.createContext("/index", IndexHandler())
    server.createContext("/detail", DetailHandler())
    server.start()
}

class DetailHandler : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        println("Request headers")
        val headers = exchange.requestHeaders
        for (key in headers.keys) {
            println("${key} = ${headers.get(key)}")
        }

        when (exchange.requestMethod.toUpperCase()) {
            "GET" -> {
                println("Request Body")
                val inputStream = exchange.requestBody
                if (inputStream == null) {
                    println("Request body is empty")
                }
                inputStream?.use {
                    println(BufferedReader(InputStreamReader(it)).readText())
                }

                val responseHeaders = exchange.responseHeaders
                val message = getResponse()
                responseHeaders.apply {
                    add("Content-Type", "text/html")
                    add("Server", "MyHttpServer/1.0")
                    add("Set-cookie", "userId=monster user")
                }
                exchange.sendResponseHeaders(200, message.toByteArray().size.toLong())
                exchange.responseBody.use {
                    it.write(message.toByteArray())
                }
            }
        }
    }

}
class IndexHandler : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        println("Remote address: ${exchange.remoteAddress}")
        val response: String = getResponse()
        exchange.sendResponseHeaders(200, response.length.toLong())
        val out = exchange.responseBody
        out.write(response.toByteArray())
        out.close()
    }
}

fun getResponse(): String {
    val sb = StringBuilder()
    sb.append("<html><h1>Hello</h1><br>")
            .append("<b>Welcome to HTTP server</b></html>")
    return sb.toString()
}