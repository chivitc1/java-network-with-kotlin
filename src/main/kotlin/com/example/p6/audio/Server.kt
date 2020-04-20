package com.example.p6.audio

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.TargetDataLine

const val PORT = 8000
const val STREAM_PORT = 9786
const val HOST = "127.0.0.1"
fun main() {
    println("Audio UDP Server Started");
    val audioUDPServer = AudioUDPServer()
    audioUDPServer.setupAudio()
    audioUDPServer.broadcastAudio()

    println("Audio UDP Server Terminated");
}

class AudioUDPServer {
    var targetDataLine: TargetDataLine? = null  //mic

    fun broadcastAudio() {
        val socket = DatagramSocket(PORT)
        val address = InetAddress.getByName(HOST)
        val buffer = ByteArray(10000)

        println("Streaming at port: ${STREAM_PORT}")
        while (true) {
            val count = targetDataLine!!.read(buffer, 0, buffer.size)
            if (count > 0) {
                val packet = DatagramPacket(buffer, buffer.size, address, STREAM_PORT)
                socket.send(packet)
            }
        }

    }

    fun setupAudio() {
        val audioFormat: AudioFormat = getAudioFormat()
        targetDataLine = AudioSystem.getTargetDataLine(audioFormat)
        targetDataLine?.open(audioFormat)
        targetDataLine?.start()
    }

    fun getAudioFormat(): AudioFormat {
        val sampleRate = 16000F
        val sampleSizeBits = 16
        val channels = 1    //mono
        val signed = true
        val bigEndian = false

        return AudioFormat(sampleRate, sampleSizeBits, channels, signed, bigEndian)
    }
}
