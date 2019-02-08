//	Function: M365 BLE message builder
//	Author:   Salvador Martin
//	Date:    03/02/2019
//
//	This library is free software; you can redistribute it and/or
//	modify it under the terms of the GNU Lesser General Public
//	License as published by the Free Software Foundation; either
//	version 2.1 of the License, or (at your option) any later version.
//
//	This library is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//	Lesser General Public License for more details.
//
//	I am not responsible of any damage caused by the misuse of this library.
//	Use at your own risk.
//
//	If you modify or use this, please don't delete my name and give me credits.

package Ninebot;

class NbMessage {
    private val message = MutableList(0) { 0 }

    private var direction: Int = 0
    private var rw: Int = 0
    private var position: Int = 0
    private val payload = MutableList(0) { 0 }
    private var checksum: Int = 0

    fun setDirection(newDirection: NbCommands): NbMessage {
        direction = newDirection.command
        checksum += direction
        return this
    }

    fun setReadOrWrite(readOrWrite: NbCommands): NbMessage { // read or write
        rw = readOrWrite.command
        checksum += rw
        return this
    }

    fun setPosition(pos: Int): NbMessage {
        position = pos
        checksum += position
        return this
    }

    fun setPayload(bytesToSend: ByteArray): NbMessage {
        checksum += bytesToSend.size + 2
        bytesToSend.forEach {
            payload.add(it.toInt())
            checksum += it
        }
        return this
    }

    fun setPayload(bytesToSend: MutableList<Int>): NbMessage {
        payload.addAll(bytesToSend)
        checksum += payload.size + 2
        payload.forEach { checksum += it }
        return this
    }

    fun setPayload(singleByteToSend: Int): NbMessage {
        payload.add(singleByteToSend)
        checksum += 3
        checksum += singleByteToSend
        return this
    }

    fun build(): String {
        setupHeaders()
        setupBody()
        calculateChecksum()
        return buildMessage()
    }

    private fun setupHeaders() {
        message.add(0x55)
        message.add(0xAA)
    }

    private fun setupBody() {
        message.add(payload.size + 2)
        message.add(direction)
        message.add(rw)
        message.add(position)
        payload.forEach { message.add(it) }
    }

    private fun calculateChecksum() {
        checksum = checksum xor 0xffff
        message.add(checksum and 0xff)
        message.add(checksum shr 8)
    }

    private fun buildMessage(): String {
        val finalMessage = StringBuilder()
        message.map {
            if (it in 0..15) {
                "0${it.toString(16)}"
            } else {
                it.toString(16)
            }
        }.forEach {
            finalMessage.append(it)
        }
        return finalMessage.toString()
    }
}