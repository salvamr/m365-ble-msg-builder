## Usage
* Turning off the scooter: 55 AA 03 20 03 79 01 5F FF
```
val turnOffScooter = NbMessage()
		.setDirection(NbCommands.MASTER_TO_M365)
		.setReadOrWrite(NbCommands.WRITE)
		.setPosition(0x79)
		.setPayload(0x01)
		.build()
````
* Changing password: 55 AA 08 20 03 79 XX XX XX XX XX XX 23 FE
    * Password must be length 6, only numbers and ascii charset
```
val changePassword = NbMessage()
		.setDirection(NbCommands.MASTER_TO_M365)
		.setReadOrWrite(NbCommands.WRITE)
		.setPosition(0x79)
		.setPayload("932046".toByteArray(charset = Charsets.US_ASCII))
		.build()
```
* Getting the controller version: 55 AA 03 20 01 1A 02 BF FF
```
val ctrlVersion = NbMessage()
		.setDirection(NbCommands.MASTER_TO_M365)
		.setReadOrWrite(NbCommands.READ)
		.setPosition(0x1A)
		.setPayload(0x02)
		.build()
```
## About the protocol
Here Camilo explains everything you need to understand the protocol

https://github.com/CamiAlfa/M365-BLE-PROTOCOL/blob/master/protocolo

## Credits
Thanks to Camilo Ruiz (@CamiAlfa at github.com) for his work on 
the M365 BLE protocol that inspired me to make this library

https://github.com/CamiAlfa/M365-BLE-PROTOCOL 
