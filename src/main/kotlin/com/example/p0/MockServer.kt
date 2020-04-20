package com.example.p0

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

const val SOAP_ENDPOINT = "/wu/ws"
fun main() {
    val PORT = 10103
    val server = HttpServer.create(InetSocketAddress(PORT), 0)
    println("Server started at port: $PORT")
    server.createContext(SOAP_ENDPOINT, SoapHandler())
    server.start()
}
