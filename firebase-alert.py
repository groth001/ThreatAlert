#1/usr/bin/python3
#===============================================================
#
# firebase-alert.py
# Author Gary Roth
# 
# Backend program for the ThreatAlert system that accepts 
# alert data from Node-Red and enters it into the Cloud
# Firestone database
#===============================================================

import socket
import datetime
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

def main():
    """Main function that listens for alert data and sends it to the cloud"""

    # Initialize connection to firebase project with service account private key
    cred = credentials.Certificate('REPLACE THIS WITH THE PATH TO YOUR PRIVATE KEY') ##
    default_app = firebase_admin.initialize_app(cred)
    db = firestore.client()

    # Setup UDP socket for incoming alert data from Node-Red
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.bind(('127.0.0.1', 3781))

    count = 0
    curDay = -1
    while True:
        data, client_address = sock.recvfrom(1024)
        #Only process data from localhost port 3870
        if client_address[0] == '127.0.0.1' and client_address[1] == 3870:
            alert = data.decode()
            date = str(datetime.date.today()).split('-')
            # Reset alert count if it's a new day
            if int(date[2]) != curDay:
                count = 0
                curDay = int(date[2])
            count += 1
            # Set the location in the database to put the alert
            doc_ref = db.collection('Year').document(date[0]).collection('Month').document(getMonth(date[1])).collection('Day').document(date[2]).collection('Alerts').document('Alert_' + str(count))
            # Send the alert data to the database location
            try:
                doc_ref.update({'alert': alert,})
            except:
                doc_ref.set({'alert': alert,})

def getMonth(num):
    """Converts a number string to its corresponding month"""

    months = {
                '01': 'January',
                '02': 'February',
                '03': 'March',
                '04': 'April',
                '05': 'May',
                '06': 'June',
                '07': 'July',
                '08': 'August',
                '09': 'September',
                '10': 'October',
                '11': 'November',
                '12': 'December'
                }
    return months[num]

# Call main function to start program
if __name__== '__main__':
    main()

