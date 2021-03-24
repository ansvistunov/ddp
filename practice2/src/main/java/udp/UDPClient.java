package udp;

import java.net.*;
import java.io.*;

public class UDPClient {
    public static void main(String args[]) {
        // args give message contents and destination hostname
        try {
            DatagramSocket aSocket = new DatagramSocket();      // create socket
            byte[] message = args[0].getBytes();
            InetAddress aHost = InetAddress.getByName(args[1]); // DNS lookup
            int serverPort = 8080;
            DatagramPacket request =
                    new DatagramPacket(message, args[0].length(), aHost, serverPort);
            System.out.println("new packet to "+aHost);
            aSocket.send(request);              //send message
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);              //wait for reply
            System.out.println("Reply: " + new String(reply.getData()));
            aSocket.close();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage()); // socket creation failed
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage()); // can be caused by send
        }
    }
}
