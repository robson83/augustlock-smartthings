# Augustlock-Smartthings

## Why ?
It's been a while waiting for SmartThings and AugustLock integrate their products, but nothing happened at all. I'm starting to heavily use SmartThings and it annoys me a little bit having to use several different apps to control things.

## How?
This project is based on [AugustCtl](https://github.com/sretlawd/augustctl) library, which allows you to control your August through your computer. Just forked it, added few things and tested against a RaspberryPi and Mac - then created a device handler to communicate my Hub and the API.

### Installation procedures

Clone two different projects:
* [augustlock-smartthings](https://github.com/robson83/augustlock-smartthings)
* [augustlock-api](https://github.com/robson83/augustctl)

Then, follow the steps below.

1. SmartThings side
   1. Log onto your [SmartThings](https://graph.api.smartthings.com/)
   2. Click on My Devices Handlers, and then Create New Device Handler on top right
   3. Copy the code you just cloned from the repo called "AugustLock.groovy" 
   4. Click Save, and then Publish - for me
   5. Go to the top menu, click on My Devices and then New Device on top right
   6. Fill in name, Device Network ID (some random numbers and letters (A-F)), Type select AugustController (should be by the end of the options) and then click create
   7. After creating, click EDIT below preferences, and type your server IP and port - this is important to make everything work

Note:I'm assuming you know about how to install and operate the basics of a RaspberryPi. Also note a Bluetooth device is needed, and the one I used is based on [CSR 8510 Chipset](http://www.amazon.com/gp/product/B00IMALQ94). 

If you have a Mac (macbook, iMac) you can also use it to test the project. Just skip directly to the node installation.

2. RaspberryPI side

   1. Install Raspbian, or any other image you might be familiar with
   2. Install Node. I suggest following the nvm package, which allows you to switch between node versions easily - Follow (NVM)[https://github.com/creationix/nvm] here. After getting, I suggest you to have v0.10.29 - this can be done issuing nvm install v0.10.29 and then nvm use v0.10.29
   3. Go to the augustlock-api project and create a file called "config.json". That file should contain your keys that will be used to communicate with your AugustLock. The steps necessary to grab it are described here [AugustLock OfflineKeys](https://github.com/mtvg/August) . I used the iPhone method, taking a backup and extracting the file.
   4. To have your server listening correctly, you should modify your config.json, adding an 'address' key to the json file. Example: {"address": "192.168.0.9", "offlineKey": "01234567890abcdef", "offlineKeyOffset": 1 } . Suggest you to have your local LAN address with 3 digit ip address - for any reason, as reported on SmartThings forum, ips starting with 10 (2 digits), doesn't communicate well (maybe a bug on ST?)
   5. Start you server using node server.js
   6. Open your browser at http://youripaddress:3000/api/status and if everything is configured accordingly, you should be able to see a json response with the result

Commands available:
/api/status
/api/lock
/api/unlock

Pending to be done:
* Add a timer and ping the hub back, in case the door is opened manually (then refresh wouldn't be necessary)
* Improve the api to cache status, in order to prevent it to be called before every lock/unlock request
* Improve error handling and the way promises are implemented to prevent http requests being returned without proper answer from the lock
* Security layer on top of the api, to allow just the hub to call the endpoints. Also do something on the raspberry pi side, maybe filtering with iptables, as ST doesn't allow LAN connections to be under HTTPS

### Demo (video)

[![Demo](http://img.youtube.com/vi/ALVF3cudANw/0.jpg)](http://www.youtube.com/watch?v=ALVF3cudANw)

### Screenshots

![Unlocked](https://github.com/robson83/augustlock-smartthings/blob/7f755fd099ac530076051c4b265f7e4d99b5d6be/thumb_IMG_8507_1024.jpg "Unlocked")
![Unlocked full](https://github.com/robson83/augustlock-smartthings/blob/7f755fd099ac530076051c4b265f7e4d99b5d6be/thumb_IMG_8508_1024.jpg "Unlocked full")
![Waiting full](https://github.com/robson83/augustlock-smartthings/blob/7f755fd099ac530076051c4b265f7e4d99b5d6be/thumb_IMG_8509_1024.jpg "Waiting full")
![Locked full](https://github.com/robson83/augustlock-smartthings/blob/7f755fd099ac530076051c4b265f7e4d99b5d6be/thumb_IMG_8510_1024.jpg "Locked full")
