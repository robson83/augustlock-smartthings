# Augustlock-Smartthings

## Why ?
It's been a while waiting for SmartThings and AugustLock integrate their products, but nothing happened at all. I'm starting to heavily use SmartThings and it annoys me a little bit having to use several different apps to control things.

## How?
This project is based on [AugustCtl](https://github.com/sretlawd/augustctl) library, which allows you to control your August through your computer. Just forked it, added few things and tested against a RaspberryPi and Mac - then created a device handler to communicate my Hub and the API.

### Installation procedures

Clone two different projects:
augustlock-smartthings
augustlock-api

Then, follow the steps below.

1. SmartThings side
   1. Log onto your [SmartThings](https://graph.api.smartthings.com/)
   2. Click on My Devices Handlers, and then Create New Device Handler on top right
   3. Copy the code you just cloned from the repo called "AugustLock.groovy" 
   4. Click Save, and then Publish - for me
   5. Go to the top menu, click on My Devices and then New Device on top right
   6. Fill in name, Device Network ID (some random numbers and letters (A-F)), Type select AugustController (should be by the end of the options) and then click create
   7. After creating, click EDIT below preferences, and type your server IP and port - this is important to make everything work

2. RaspberryPI side
   1. Work in progress
