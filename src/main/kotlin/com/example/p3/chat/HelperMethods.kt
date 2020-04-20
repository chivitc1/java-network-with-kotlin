package com.example.p3.chat

import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

const val BUFFER_SIZE = 64
const val END_CHAR = 0x00.toChar()
class HelperMethods {
    companion object {
        fun sendFixedLengthMessage(socketChannel: SocketChannel, message: String) {
            val buffer = ByteBuffer.allocate(BUFFER_SIZE)
            buffer.put(message.toByteArray())
            buffer.flip()
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer)
            }
            println("Sent: ${message}")
        }

        fun receiveFixedLengthMessage(socketChannel: SocketChannel) : String {
            var message: String = ""
            val buffer = ByteBuffer.allocate(BUFFER_SIZE)
            socketChannel.read(buffer)
            buffer.flip()
            while (buffer.hasRemaining()) {
                message += buffer.get().toChar()
            }
            return message
        }

        fun sendMessage(socketChannel: SocketChannel, message: String) {
            val buffer = ByteBuffer.allocate(message.length + 1)
            buffer.put(message.toByteArray())
            buffer.put(END_CHAR.toByte())
            buffer.flip()
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer)
            }
            println("Sent: $message")
        }

        fun receiveMessage(socketChannel: SocketChannel): String {
            val buffer = ByteBuffer.allocate(16)
            var message = ""
            while (socketChannel.read(buffer) > 0) {
                var byteRead = END_CHAR
                buffer.flip()
                while (buffer.hasRemaining()) {
                    byteRead = buffer.get().toChar()
                    if (byteRead == END_CHAR) break
                    message += byteRead
                }
                if (byteRead == END_CHAR) break
                buffer.clear()
            }

            return message
        }

        fun getString(buffer: ByteBuffer) : String {
            var message  = ""
            while (buffer.hasRemaining()) {
                message += buffer.get().toChar()
            }
            return message
        }
    }
}