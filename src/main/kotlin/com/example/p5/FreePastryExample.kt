package com.example.p5

import rice.environment.Environment
import rice.p2p.commonapi.*
import rice.pastry.socket.SocketPastryNodeFactory
import rice.pastry.standard.RandomNodeIdFactory
import java.net.InetAddress
import java.net.InetSocketAddress

fun main(args : Array<String>) {
    var BIND_PORT = args[0].toInt()
    val BOOT_PORT = 9001
    val HOST_IP = "192.168.5.102"
    val address = InetAddress.getByName(HOST_IP)
    val bootAddress = InetSocketAddress(address, BOOT_PORT)
    println("Address: ${bootAddress}")

    val env = Environment()
    env.parameters.setString("nat_search_policy", "never")
    pastryExample(BIND_PORT, bootAddress, env)
}

fun pastryExample(port: Int, address: InetSocketAddress, env: Environment) {
    val nidFactory = RandomNodeIdFactory(env)
    val pastryNode = SocketPastryNodeFactory(nidFactory, port, env).newNode()
    val pastryApplication = FreePastryApplication(pastryNode)
    pastryNode.boot(address)
    println("Node ${pastryNode.id} created")
    println("Node ${pastryNode.id.toStringFull()} created")
    env.timeSource.sleep(10000)

//    val randomId = nidFactory.generateNodeId()
//    pastryApplication.routeMessage(randomId)
    pastryNode.leafSet.uniqueSet.forEach {
        pastryApplication.routeMessageDirect(it)
        env.timeSource.sleep(1000)
    }
}

class FreePastryApplication: Application {
    val endpoint: Endpoint
    val message: String
    val instance = "instance ID"

    constructor(node: Node) {
        endpoint = node.buildEndpoint(this, instance)
        message = "Hello there! from Instance : ${instance} Sent at : [${getCurrentTime()}]"
        endpoint.register()
    }

    override fun update(handle: NodeHandle?, joined: Boolean) {

    }

    override fun forward(message: RouteMessage?): Boolean {
        return true
    }

    override fun deliver(id: Id, message: Message) {
        println("Message Received\n\tCurrent Node : " + endpoint.id + "\n\tMessage : " + message +
                "\n\tTime : " + getCurrentTime())
    }

    fun routeMessageDirect(nodeHandle: NodeHandle) {
        println("Message Sent Direct\n\tCurrent Node : " + endpoint.id + " Destination : " +
                nodeHandle + "\n\tTime : " + getCurrentTime())
        val msg = PastryMessage(endpoint.id, nodeHandle.id, "DIRECT- $message")
        endpoint.route(null, msg, nodeHandle)
    }

    fun routeMessage(id: Id) {
        println("Message Sent\n\tCurrent Node : " + endpoint.id + "\n\tDestination : " + id +
                "\n\tTime : " + getCurrentTime())
        val msg = PastryMessage(endpoint.id, id, message)
        endpoint.route(id, msg, null)
    }

    private fun getCurrentTime(): Long {
        return endpoint.environment.timeSource.currentTimeMillis()
    }

    override fun toString(): String {
        return "FreePastryApplication ${endpoint.id}"
    }
}

class PastryMessage(val from: Id, val to: Id, val message: String): Message {
    override fun getPriority(): Int {
        return Message.LOW_PRIORITY
    }

    override fun toString(): String {
        return "PastryMessage(from=$from, to=$to, message=[$message])"
    }
}
