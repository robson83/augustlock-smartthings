/**
 *  AugustLock Controller for SmartThings (beta)
 *
 *  Copyright 2016 Robson Dantas (biu.dantas@gmail.com) - https://github.com/robson83
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

import groovy.json.JsonSlurper

metadata {
	// Automatically generated. Make future change here.
	definition (name: "AugustLock Controller", namespace: "robson83", author: "Robson Dantas") {
    	command "lock"
        command "unlock"
        command "refresh"
        capability "Lock"
	}

	// simulator metadata
	simulator {
	}
    
     preferences {
	    input("ServerIp", "string", title:"Server IP Address", description: "Please enter server ip address", required: true, displayDuringSetup: true)
    	input("ServerPort", "number", title:"Server Port", description: "Please enter your server Port", defaultValue: 80 , required: true, displayDuringSetup: true)
	}

	// UI tile definitions
	tiles {
		standardTile("door", "_lockr", width: 3, height: 2, canChangeIcon: true) {
			state "locked", label: "Locked", action:"unlock", icon:"st.locks.lock.locked", backgroundColor:"#FF0000"
            state "waiting", label: "Waiting", action: "waitdevice", icon:"st.locks.lock.locked", backgroundColor:"#c0c0c0"
            state "unlocked", label: "Unlocked", action:"lock", icon:"st.locks.lock.unlocked", backgroundColor:"#79b821"
		}
        
         standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 1, height: 1) {
        	state "default", action:"refresh", icon:"st.secondary.refresh"
    	}

		main "door"
        details "door", "refresh"
	}
}

private push(command) {

	def cmd = myCommand(command)
    log.debug cmd
}

def waitdevice(){}

def lock() {
    log.debug "Unlock the door"
    sendEvent(name: "_lockr", value: "waiting")
	push('lock')
}

def unlock() {
    log.debug "Lock the door"
    sendEvent(name: "_lockr", value: "waiting")
	push('unlock')
}

def refresh() {
	log.debug "Refreshing"
    sendEvent(name: "_lockr", value: "waiting")
    push('status')
}

private String makeNetworkId(ipaddr, port) { 
     String hexIp = ipaddr.tokenize('.').collect { 
     String.format('%02X', it.toInteger()) 
     }.join() 
     String hexPort = String.format('%04X', port) 
     log.debug "${hexIp}:${hexPort}" 
     return "${hexIp}:${hexPort}" 
}

def myCommand(command) {

	def hostHex = makeNetworkId(ServerIp, ServerPort)
	device.deviceNetworkId = hostHex
    
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/api/${command}",
        headers: [
            HOST: hostHex
        ]
    )
    
    sendHubCommand(result)

	return result
}

private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    return hex

}

def parse(String description) {
	
	log.debug description
	def bodyString = new String(description.split(',')[6].split(":")[1].decodeBase64())
    log.debug bodyString
	def slurper = new JsonSlurper() 
    def obj = slurper.parseText(bodyString)
    sendEvent(name: "_lockr", value: obj.ret)
    log.debug bodyString
}

private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
    return hexport
}
