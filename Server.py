import socket, threading
import time

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_ip = '0.0.0.0'
server_port = 5413
server.bind((server_ip, server_port))
server.listen(5)

while True:
    client_socket, client_address = server.accept()
    print 'Connection from: ', client_address
    while True:
        data = client_socket.recv(1024)
	print 'Received: ', data
	time.sleep(1)
        
