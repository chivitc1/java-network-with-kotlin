package com.example.p2.networkinterface

import java.lang.String.format
import java.lang.StringBuilder
import java.net.InetAddress
import java.net.NetworkInterface

fun main(args: Array<String>) {
    val interfaceEnum = NetworkInterface.getNetworkInterfaces()
    print("Name       Display name\n---------------\n")

    for (ele in interfaceEnum) {
        println("${format("%-10s %-30s", ele.name, ele.displayName)}")
        for (addr in ele.inetAddresses) {
            println("---    ${addr}")
        }
    }
    println("-----------------------")
    val address = InetAddress.getLocalHost()
    val network = NetworkInterface.getByInetAddress(address)
    println("Interface: ${network.name}")
    println("IP address: ${address.hostAddress}")
    println("MAC address: ${getMac(network)}")
}

fun getMac(network: NetworkInterface?): String {
    val sb = StringBuilder()
    val macBuffer = network?.hardwareAddress
    if (macBuffer != null) {
        for (i in 0..macBuffer.size-1) {
            if (i == macBuffer.size - 1) {
                sb.append(String.format("%02X%s", macBuffer[i], ""))
            } else {
                sb.append(String.format("%02X%s", macBuffer[i], "-"))
            }
        }
    } else {
        return "---"
    }

    return sb.toString()
}
