from functools import reduce
from dateutil import parser
import threading
import socket
import datetime
import time

# Data structure to store client address and clock data
client_data = {}
client_data_lock = threading.Lock()  # Lock for thread safety

def startReceivingClockTime(connector, address):
    while True:
        try:
            clock_time_string = connector.recv(1024).decode().strip()

            if not clock_time_string:
                continue

            # Parse received clock time
            clock_time = parser.isoparse(clock_time_string)

            # Calculate time difference
            clock_time_diff = datetime.datetime.now() - clock_time

            # Store data with thread lock
            with client_data_lock:
                client_data[address] = {
                    "clock_time": clock_time,
                    "time difference": clock_time_diff,
                    "connector": connector,
                }

            print(f"[SERVER] Updated client data for {address}")

            time.sleep(5)

        except Exception as e:
            print(f"[SERVER] Error receiving clock from {address}: {e}")
            break  # Exit loop if there's an error

def startConnecting(master_server):
    while True:
        master_slave_connector, address = master_server.accept()
        print(f"[SERVER] Connected to client {address}")

        # Start receiving clock times from this client
        threading.Thread(
            target=startReceivingClockTime,
            args=(master_slave_connector, address),
            daemon=True
        ).start()

def getAverageClockDiff():
    with client_data_lock:
        if not client_data:
            return datetime.timedelta(0)

        time_diff_list = [client['time difference'] for client in client_data.values()]
        sum_of_clock_diff = sum(time_diff_list, datetime.timedelta(0))
        average_clock_diff = sum_of_clock_diff / len(client_data)
        return average_clock_diff

def synchronizeAllClocks():
    while True:
        print("\n[SYNCHRONIZER] New synchronization cycle")
        with client_data_lock:
            num_clients = len(client_data)

        print(f"[SYNCHRONIZER] Clients to synchronize: {num_clients}")

        if num_clients > 0:
            try:
                avg_diff = getAverageClockDiff()
                print(f"[SYNCHRONIZER] Average clock difference: {avg_diff}")

                with client_data_lock:
                    for client_address, client in client_data.items():
                        sync_time = (datetime.datetime.now() - avg_diff).isoformat()
                        client['connector'].sendall(sync_time.encode())
                        print(f"[SYNCHRONIZER] Sent synchronized time to {client_address}")

            except Exception as e:
                print(f"[SYNCHRONIZER] Error during synchronization: {e}")

        else:
            print("[SYNCHRONIZER] No clients connected.")

        time.sleep(5)

def initiateClockServer(port=8083):
    master_server = socket.socket()
    master_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    master_server.bind(("", port))
    master_server.listen(10)
    print(f"[SERVER] Clock server started on port {port}")

    threading.Thread(
        target=startConnecting,
        args=(master_server,),
        daemon=True
    ).start()

    threading.Thread(
        target=synchronizeAllClocks,
        daemon=True
    ).start()

    while True:
        time.sleep(1)

if __name__ == "__main__":
    initiateClockServer(port=8083)
