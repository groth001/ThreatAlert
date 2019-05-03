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

## Installation and Setup

Setup a new or existing Raspberry Pi 3 B+ by the following the instruction guide that comes with the kit. Install the Raspbian OS and be sure to adjust the keyboard settings from the default UK settings to match your region and style of keyboard. The Raspberry Pi should be connected to a router via ethernet cable or Wi-Fi. Enable SSH on the router and log into it via the command line from any machine. Add the following iptables rules, inserting the IP address of the Raspberry Pi where specified.
```
iptables -I PREROUTING -t mangle -j ROUTE --gw [IP address of the Raspberry Pi] --tee
iptables -I POSTROUTING -t mangle -j ROUTE --gw [IP address of the Raspberry Pi] --tee
``` 

Back on the Raspberry Pi, ensure that Python 3.x and pip3 are installed.
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
 
Go to [Google Firebase](https://firebase.google.com/) in a browser, create an account, and sign in. Click on "Go to console" in the upper right corner. Click on the "Add project" square and give the project a name (ThreatAlert or any other name). Set the region to your location, check the box to accept the terms, and click the "Create Project" button. Go to Database in the left window pane menu under Develop and click "Create database" in the orange header to make a new Cloud Firestore database. In the pop-up window, leave the security rules as "Start in locked mode" and click the "Enable" button.

Go to "Authentication" in the left side pane under Develop and then either click "Set up sign-in method" under the Users tab or click the Sign-in method tab. Select "Email/Password" and enable it with the upper slider in the pop-up window. Leave the passwordless sign-in option disabled and click Save.

In the left pane, click the Gear icon next to Project Overview to bring up Project Settings. Go to the Service accounts tab and click the "Generate new private key" button. Put the downloaded certificate in a safe place on the Raspberry Pi. Open the python program 'firebase-alert.py' in a text editor and find the line marked with ## at the end of it. Replace the string in the function call with the path to the certificate.



## Getting Started
