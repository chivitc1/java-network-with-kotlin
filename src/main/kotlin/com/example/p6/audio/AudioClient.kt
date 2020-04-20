package com.example.p6.audio

import sun.security.krb5.Confounder.bytes
import java.io.ByteArrayInputStream
import java.net.DatagramPacket
import java.net.DatagramSocket
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine


fun main() {
    println("Audio UDP Client Started");
    AudioClient()
    println("Audio UDP Client Terminated");
}
class AudioClient {
    private var audioInputstream: AudioInputStream? = null
    private var sourceDataLine: SourceDataLine? = null  //speaker

    constructor() {
        initiateAudio()
    }


    fun getAudioFormat(): AudioFormat {
        val sampleRate = 16000F
        val sampleSizeBits = 16
        val channels = 1    //mono
        val signed = true
        val bigEndian = false

        return AudioFormat(sampleRate, sampleSizeBits, channels, signed, bigEndian)
    }

    private fun initiateAudio() {
        val socket = DatagramSocket(STREAM_PORT)
        val buffer = ByteArray(10000)
        while (true) {
            val packet = DatagramPacket(buffer, buffer.size)
            socket.receive(packet)
            val audioData = packet.data
            val inputStream = ByteArrayInputStream(audioData)
            val audioFormat = getAudioFormat()
            audioInputstream = AudioInputStream(inputStream, audioFormat, audioData.size.toLong() / audioFormat.frameSize)
            sourceDataLine = AudioSystem.getSourceDataLine(audioFormat)
            sourceDataLine!!.open(audioFormat)
            sourceDataLine!!.start()

            playAudio()
        }
    }

    private fun playAudio() {
        val buffer = ByteArray(10000)
        var count = 0
        while (audioInputstream!!.read(buffer, 0, buffer.size).also({ count = it }) != -1) if (count > 0) {
            sourceDataLine!!.write(buffer, 0, count)
        }
    }
}