from timeit import default_timer as timer
import socket
import datetime
import time
from dateutil import parser
from functools import reduce
import threading

# client thread function used to send time at client side
def startSendingClockTime(slave_client):
    while True:
        # send clock time to the client
        clock_time = datetime.datetime.now()
        slave_client.send(str(clock_time).encode())

# client thread function used to receive synchronized time from the server
def startReceivingTime(slave_client):
    while True:
        # receive data from the server
        synchronized_time = parser.parse(slave_client.recv(1024).decode())
        print("Synchronized time at the client is : " + str(synchronized_time))
    
# function used to synchronize client process time
def initiateSlaveClient(port=8083):
    slave_client = socket.socket()
    # connect to the clock server on local computer
    slave_client.connect(('127.0.0.1', port))

    print("Starting to Send time from the server...")
    send_time_thread = threading.Thread(
        target=startSendingClockTime,
        args=(slave_client,)
    )
    send_time_thread.start()

    print("Starting to Receive time to the server...")
    receive_time_thread = threading.Thread(
        target=startReceivingTime,
        args=(slave_client,)
    )
    receive_time_thread.start()

# Driver function
if __name__ == "__main__":
    # initiate the client process
    initiateSlaveClient(port=8083)