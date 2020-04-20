package com.example.p7.channelselector

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.time.LocalDateTime

const val PORT = 5000
const val HOST = "127.0.0.1"
fun main() {
    println("Time Server Started")

    val serverSocketChannel = ServerSocketChannel.open()
    serverSocketChannel.socket().bind(InetSocketAddress(PORT))
    val selector = Selector.open()
    Thread(SelectorHandler(selector)).start()

    while (true) {
        val socketChannel = serverSocketChannel.accept()
        println("Socket channel accepted - $socketChannel")

        if (socketChannel != null) {
            socketChannel.configureBlocking(false)
            selector.wakeup()
            socketChannel.register(selector, SelectionKey.OP_WRITE, null)
        }
    }
}

class SelectorHandler(val selector: Selector): Runnable {
    override fun run() {
        val TIMEOUT = 500L
        while (true) {
            println("About to select...")
            val readyChannels = selector.select(TIMEOUT)
            if (readyChannels == 0) {
                println("No tasks available")
            } else {
                val keys = selector.selectedKeys()
                val keyIterator = keys.iterator()
                while (keyIterator.hasNext()) {
                    val selectionKey = keyIterator.next()
                    if (selectionKey.isWritable) {
                        val message = "Date: ${LocalDateTime.now()}"
                        val buffer = ByteBuffer.allocate(64)
                        buffer.put(message.toByteArray())
                        buffer.flip()
                        var socketChannel: SocketChannel? = null
                        while (buffer.hasRemaining()) {
                            socketChannel = selectionKey.channel() as SocketChannel
                            socketChannel.write(buffer)
                        }
                        println("Sent : $message to : ${socketChannel!!.remoteAddress}")
                    }
                    Thread.sleep(1000)
                    keyIterator.remove()
                }
            }

        }
    }

}

