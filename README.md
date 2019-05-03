# ThreatAlert

ThreatAlert is a native Android app that integrates with Google Firebase's Cloud Firestore database to notify the user of Snort IDS alerts triggered on the user's monitored network.

## Hardware Requirements

* [Raspberry Pi 3 B+ Kit](https://www.canakit.com/raspberry-pi/pi-3-model-b-plus-kits)
* Android SmartPhone running Version 8.0 or higher with USB cable
* Desktop or Laptop

## Software Requirements

* [Git](https://git-scm.com/)
* [Android Studio](https://developer.android.com/studio)
* [Snort 2.9](https://www.snort.org/)
* [Node-Red](https://nodered.org/)
* [Python 3](https://www.python.org/downloads/)

## Installation

Setup a new Raspberry Pi 3 B+ by the following the instruction guide that comes with the kit or repurpose an existing one. Install the Raspbian OS and be sure to adjust the keyboard settings from the default UK settings to match your region and style of keyboard. Ensure that Python 3.x and pip3 is installed.
```
which python3
which pip3
sudo apt-get install python3
sudo apt-get install pip3
```
Install the following Google Firebase Python module.
```
pip3 install --upgrade firebase-admin
```
Install the most recent version of Snort 2.9 on the Raspberry Pi and configure it using this tutorial guide on [UpCloud](https://upcloud.com/community/tutorials/installing-snort-on-debian/).
Additionally, install the IoT wiring framework [Node-Red](https://nodered.org/docs/hardware/raspberrypi) using the Raspberry Pi specific guide.

Download and install [Git](https://git-scm.com/) on another machine. Retrieve the app source code from this repository and go into the following directory.
```
git clone https://github.com/groth001/ThreatAlert
cd ./ThreatAlert/RaspPiFiles
```
Transfer the two files 'firebase-alert.py' and 'flows_raspberrypi.json' to the Raspberry Pi. The Python program file may go and be run from anywhere (the home directory works fine). The json file containing the Node-Red flow must be placed in the '.node-red' directory overwriting the existing file from the installation.
 
Go to [Google Firebase](https://firebase.google.com/) in a browser, create an account, and sign in. Click on "Go to console" in the upper right corner. Click on the "Add project" square and give the project a name (ThreatAlert or any other name). Set the region to your location, check the box to accept the terms, and click the "Create Project" button.


## Getting Started
