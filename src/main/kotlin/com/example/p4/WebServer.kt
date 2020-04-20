package com.example.p4

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.ServerSocket
import java.net.Socket
import java.util.*

const val PORT = 3000
const val NEW_LINE = "\r\n"
fun main(args: Array<String>) {
    println("Webserver started on port ${PORT}")
    ServerSocket(PORT).use {
        while (true) {
            println("waiting for client request...")
            val socketConnection = it.accept()
            println("Connection made")
            Thread(ClientHandler(socketConnection)).start()
        }
    }
}

class ClientHandler(val socket: Socket): Runnable {
    override fun run() {
        println("Handler started for ${socket}")
        handleRequest(socket)
        println("Handler terminated for ${socket}")
    }

    private fun handleRequest(socket: Socket) {
        BufferedReader(InputStreamReader(socket.getInputStream())).use {
            val headerLine = it.readLine()
            val tokenizer = StringTokenizer(headerLine)
            val httpMethod = tokenizer.nextToken()
            when (httpMethod) {
                "GET" -> {
                    val responseBuffer = StringBuilder()
                    responseBuffer.append("<html><h1>WebServer Home Page…. </h1><br>")
                    .append("<b>Welcome to my web server!</b><BR>")
                    .append("</html>")
                    sendResponse(socket, 200, responseBuffer.toString())
                }

                else -> {
                    println("The HTTP method is not allowed")
                    sendResponse(socket, 405, "Method not allowed")
                }
            }
        }
    }

    private fun sendResponse(socket: Socket, httpCode: Int, body: String) {
        var statusLine: String
        val serverHeader = "Server: Webserver${NEW_LINE}"
        val contentTypeHeader = "Content-Type: text/html${NEW_LINE}"
        DataOutputStream(socket.getOutputStream()).use {
            when (httpCode) {
                200 -> {
                    statusLine = "HTTP/1.0 200 OK${NEW_LINE}"
                    val contentLengthHeader = "Content-Length: ${body.length}${NEW_LINE}"
                    it.apply {
                        writeBytes(statusLine)
                        writeBytes(serverHeader)
                        writeBytes(contentTypeHeader)
                        writeBytes(contentLengthHeader)
                        writeBytes(NEW_LINE)
                        writeBytes(body)
                    }

                }

                405 -> {
                    statusLine = "HTTP/1.0 405 Method Not Allowed${NEW_LINE}"
                    it.writeBytes(statusLine)
                    it.writeBytes(NEW_LINE)
                }

                else -> {
                    statusLine = "HTTP/1.0 404 Not Found${NEW_LINE}"
                    it.writeBytes(statusLine)
                    it.writeBytes(NEW_LINE)
                }
            }
        }
    }

}